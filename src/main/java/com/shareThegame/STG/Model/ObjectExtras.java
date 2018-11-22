package com.shareThegame.STG.Model;

import javax.persistence.*;

@Entity
public class ObjectExtras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private long sportobjectid;

    private boolean parking;

    private  boolean toilets;

    private  boolean artificiallighting;

    private  boolean balls;


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
    boolean isToilets ( ) {
        return toilets;
    }

    public
    void setToilets ( boolean toilets ) {
        this.toilets = toilets;
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
    boolean isBalls ( ) {
        return balls;
    }

    public
    void setBalls ( boolean balls ) {
        this.balls = balls;
    }
}
