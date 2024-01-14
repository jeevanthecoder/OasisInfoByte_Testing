package com.jeevan.trainticketreservation.loginregisterservice.Dao;

import com.jeevan.trainticketreservation.loginregisterservice.Entity.Reservation;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedTrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    public Reservation findReservationByPnrNumber(Long pnrNumber);

    public void deleteByPnrNumber(Long pnr);
}
