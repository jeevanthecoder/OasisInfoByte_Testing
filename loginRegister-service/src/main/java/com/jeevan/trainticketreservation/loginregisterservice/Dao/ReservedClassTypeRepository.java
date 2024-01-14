package com.jeevan.trainticketreservation.loginregisterservice.Dao;

import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedClassType;
import com.jeevan.trainticketreservation.loginregisterservice.Entity.ReservedTrainDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservedClassTypeRepository extends JpaRepository<ReservedClassType,Integer> {

    ReservedClassType findReservedClassTypeByReservedTrainDetails(ReservedTrainDetails reservedTrainDetails);

    void deleteReservedClassTypeByPnr(Long pnr);
}
