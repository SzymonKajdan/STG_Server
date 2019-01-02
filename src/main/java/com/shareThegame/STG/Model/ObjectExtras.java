package com.shareThegame.STG.Model;

import javax.persistence.*;

@Entity
public class ObjectExtras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private long sportobjectid;

    private boolean parking;

    private  boolean bathroom;

    private  boolean artificiallighting;

    private  boolean lockerroom;

    private  boolean equipment;


    public Long getId ( ) {
        return id;
    }

    public void setId ( Long id ) {
        this.id = id;
    }



    public long getSportobjectid() {
        return sportobjectid;
    }

    public void setSportobjectid(long sportobjectid) {
        this.sportobjectid = sportobjectid;
    }


    public
    boolean isParking ( ) {
        return parking;
    }

    public
    void setParking ( boolean parking ) {
        this.parking = parking;
    }


    public
    boolean isArtificiallighting ( ) {
        return artificiallighting;
    }

    public
    void setArtificiallighting ( boolean artificiallighting ) {
        this.artificiallighting = artificiallighting;
    }

    public
    boolean isBathroom ( ) {
        return bathroom;
    }

    public
    void setBathroom ( boolean bathroom ) {
        this.bathroom = bathroom;
    }

    public
    boolean isLockerroom ( ) {
        return lockerroom;
    }

    public
    void setLockerroom ( boolean lockerroom ) {
        this.lockerroom = lockerroom;
    }

    public
    boolean isEquipment ( ) {
        return equipment;
    }

    public
    void setEquipment ( boolean equipment ) {
        this.equipment = equipment;
    }
}
