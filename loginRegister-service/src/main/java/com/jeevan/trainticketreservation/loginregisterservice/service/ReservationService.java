package com.jeevan.trainticketreservation.loginregisterservice.service;

import com.jeevan.trainticketreservation.loginregisterservice.Dao.ReservationRepository;
import com.jeevan.trainticketreservation.loginregisterservice.Dao.ReservedClassTypeRepository;
import com.jeevan.trainticketreservation.loginregisterservice.Dao.ReservedTrainDetailsRepository;
import com.jeevan.trainticketreservation.loginregisterservice.Dao.UserRepository;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.Reservation;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedClassType;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedTrainDetails;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private ReservedClassTypeRepository reservedClassTypeRepository;

    @Autowired
    private ReservedTrainDetailsRepository reservedTrainDetailsRepository;
    public Long PNRGeneration(){
        Long PNR=0l;
            Random random = new Random();
            PNR = random.nextLong(1, 99999999);

        return PNR;
    }

    @Transactional
    public String DeleteReservationByPNR(Long PNR,String userName){

        Users user = this.userRepository.findUsersByUserName(userName);
        Reservation reservation = this.reservationRepository.findReservationByPnrNumber(PNR);
        if(reservation==null){
            return "Reservation details not found";
        }else if(user==null){
            return "User not found";
        }else{

//            int cId=reservedClassType.getCId();
//            this.reservedClassTypeRepository.deleteReservedClassTypeByPnr(PNR);
//            System.out.println("Deleted ReservedClassType");
//this.reservedTrainDetailsRepository.deleteReservedTrainDetailsByPnrNumber(PNR);
//            System.out.println("Deleted ReservedTrainDetails");
            this.reservationRepository.deleteByPnrNumber(PNR);
            System.out.println("Deleted Reservation");
            user.getReservation().remove(reservation);
//            ReservedTrainDetails reservedTrainDetails = this.reservedTrainDetailsRepository.findReservedTrainDetailsByReservation(reservation);
//            ReservedClassType reservedClassType = this.reservedClassTypeRepository.findReservedClassTypeByReservedTrainDetails(reservedTrainDetails);
//            reservedTrainDetails.getReservedClassType().remove(reservedClassType);
//            reservation.getReservedTrainDetails().remove(reservedTrainDetails);
            return "Reservation with PNR : "+PNR+" delete successfully!!!";
        }
    }
}
