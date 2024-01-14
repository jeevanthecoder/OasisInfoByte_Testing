package com.jeevan.trainticketreservation.loginregisterservice.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Table
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReservedTrainDetails {

    @Id
    @GeneratedValue
    public int t_Id;

    @Column(unique = true)
    public Long pnrNumber;

    public String trainNumber;

    public String trainName;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    public Set<ReservedClassType> reservedClassType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    public Reservation reservation;

    public String source;
    public String dest;

    public Date dateOfJourney;


    public ReservedTrainDetails(String trainNumber, String trainName, Set<ReservedClassType> reservedClassType, String source, String dest, Date dateOfJourney) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.reservedClassType = reservedClassType;
        this.source = source;
        this.dest = dest;
        this.dateOfJourney = dateOfJourney;
    }
}
