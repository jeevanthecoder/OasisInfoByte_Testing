package com.jeevan.trainticketreservation.loginregisterservice.controller;

import com.jeevan.trainticketreservation.loginregisterservice.Dao.*;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.*;
import com.jeevan.trainticketreservation.loginregisterservice.service.ReservationService;
import com.jeevan.trainticketreservation.loginregisterservice.service.UserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TrainDetailsRepository trainDetailsRepository;

    @Autowired
    private ReservedClassTypeRepository reservedClassTypeRepository;

    @Autowired
    private ReservedTrainDetailsRepository reservedTrainDetailsRepository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;
//    @GetMapping("/users")
//    public List<Users> getUser(){
//        System.out.println("Getting users.");
//        return userService.getUsers();
//    }

    @PostMapping(value = "/reserve-ticket/{trainNumber}")
    public ResponseEntity<Reservation> reserveTheTicket(Principal principal,@PathVariable("trainNumber")String trainNumber, @RequestBody ReservedClassType reservedClassType){
        ReservedClassType reservedClassType1 = reservedClassType;
        TrainDetails trainDetails =this.trainDetailsRepository.findTrainDetailsByTrainNumber(trainNumber);
        boolean flag=false;
        if(trainNumber==null){
            System.out.println("TrainNumber not found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(principal.getName()==null){

            System.out.println("No userName");
            return ResponseEntity.status(HttpStatus.valueOf("No userName found")).build();
        }
        Users users = this.userRepository.findUsersByUserName(principal.getName());
        if(users==null){
            System.out.println("User is not in database");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if(reservedClassType1==null){
            System.out.println("No input");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

            if(trainDetails ==null){
                System.out.println("No trains found for this number");
                return ResponseEntity.status(HttpStatus.valueOf("No trains found for this train number")).build();
            }

                Reservation reservation = new Reservation();
                Long numberofseats = reservedClassType1.getNumberOfSeats();
                Set<ClassType> classType = trainDetails.getClassType();

                for (ClassType classType1 : classType) {
                    System.out.println("Classtype : "+classType1.getClasstype().toString()+" "+"NumberOfSeats : "+classType1.getNumberOfSeats().longValue());
                    if (classType1.getClasstype().equals(reservedClassType.getClasstype()) && numberofseats <= classType1.getNumberOfSeats().longValue()) {
                        flag = true;
                        break;
                    }
                }
                if (flag == true) {
                    ReservedTrainDetails reservedTrainDetails1 = new ReservedTrainDetails();
                    reservedTrainDetails1.setTrainName(trainDetails.getTrainName());
                    reservedTrainDetails1.setTrainNumber(trainNumber);
                    reservedTrainDetails1.setSource(trainDetails.getSource());
                    reservedTrainDetails1.setDest(trainDetails.getDest());
                    reservedTrainDetails1.setDateOfJourney(trainDetails.getDateOfJourney());

                    if(reservation.getReservedTrainDetails()==null){
                        Set<ReservedTrainDetails> reservedTrainDetailsSet = new HashSet<>();
                        reservedTrainDetailsSet.add(reservedTrainDetails1);
                        reservation.setReservedTrainDetails(reservedTrainDetailsSet);
                    }else
                    reservation.getReservedTrainDetails().add(reservedTrainDetails1);
                    Long pnr = reservationService.PNRGeneration();
                    reservation.setPnrNumber(pnr);
                    reservedClassType1.setPnr(pnr);
                    reservedTrainDetails1.setPnrNumber(pnr);
                    reservation.setUsers(users);
                    users.getReservation().add(reservation);
//                    this.reservedClassTypeRepository.save(reservedClassType1);

                    if(reservedTrainDetails1.getReservedClassType()==null){
                        Set<ReservedClassType> reservedClassTypesSet = new HashSet<>();
                        reservedClassTypesSet.add(reservedClassType1);
                        reservedTrainDetails1.setReservedClassType(reservedClassTypesSet);
                    }else
                    reservedTrainDetails1.getReservedClassType().add(reservedClassType1);
                    reservedClassType1.setReservedTrainDetails(reservedTrainDetails1);
//                    this.reservedTrainDetailsRepository.save(reservedTrainDetails1);
                    reservedTrainDetails1.setReservation(reservation);
                    return ResponseEntity.ok(this.reservationRepository.save(reservation));

                }else{
                    System.out.println("flag value is false.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }


    }

    @GetMapping("/delete-reservation/{PNR}")
public ResponseEntity<Void> deleteReservationbypnr(@PathVariable("PNR")Long PNR,Principal principal){

        if(PNR==null){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else{
            String message=this.reservationService.DeleteReservationByPNR(PNR, principal.getName());
            System.out.println(message);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
}

    @GetMapping("/current-user")
    public String getLoggedInUser(Principal principal){

        return principal.getName();
    }


}
