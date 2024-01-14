package com.jeevan.trainticketreservation.loginregisterservice.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReservedClassType {

    @Id
    @GeneratedValue
    int CId;

    String classtype;

    @Column(unique = true)
    public Long pnr;

    public Long numberOfSeats;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    public ReservedTrainDetails reservedTrainDetails;

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public Long getPnr() {
        return pnr;
    }

    public ReservedClassType setPnr(Long pnr) {
        this.pnr = pnr;
        return this;
    }

    public ReservedClassType setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public int getCId() {
        return CId;
    }

    public ReservedClassType setCId(int c_Id) {
        this.CId = c_Id;
        return this;
    }

    public String getClasstype() {
        return classtype;
    }

    public ReservedClassType setClasstype(String classtype) {
        this.classtype = classtype;
        return this;
    }

    public ReservedTrainDetails getReservedTrainDetails() {
        return reservedTrainDetails;
    }

    public ReservedClassType setReservedTrainDetails(ReservedTrainDetails reservedTrainDetails) {
        this.reservedTrainDetails = reservedTrainDetails;
        return this;
    }

}
