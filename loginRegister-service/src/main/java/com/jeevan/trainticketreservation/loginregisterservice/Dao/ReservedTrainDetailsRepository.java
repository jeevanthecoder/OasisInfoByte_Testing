package com.jeevan.trainticketreservation.loginregisterservice.Dao;

import com.jeevan.trainticketreservation.loginregisterservice.Entity.Reservation;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedTrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedTrainDetailsRepository extends JpaRepository<ReservedTrainDetails,Integer> {

    public ReservedTrainDetails findReservedTrainDetailsByReservation(Reservation reservation);

    void deleteReservedTrainDetailsByPnrNumber(Long pnr);
}
