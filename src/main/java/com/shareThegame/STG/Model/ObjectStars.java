package com.shareThegame.STG.Model;

import org.json.JSONPropertyIgnore;

import javax.persistence.*;

@Entity
public
class ObjectStars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sportobjectid;

    private Long userid;

    private long  value;
    @ManyToOne

    private  SportObject sportObject;

    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
    }

    public
    Long getSportobjectid ( ) {
        return sportobjectid;
    }

    public
    void setSportobjectid ( Long sportobjectid ) {
        this.sportobjectid = sportobjectid;
    }

    public
    Long getUserid ( ) {
        return userid;
    }

    public
    void setUserid ( Long userid ) {
        this.userid = userid;
    }

    public
    long getValue ( ) {
        return value;
    }

    public
    void setValue ( long value ) {
        this.value = value;
    }


    @JSONPropertyIgnore
    public
    SportObject getSportObject ( ) {
        return sportObject;
    }

    public
    void setSportObject ( SportObject sportObject ) {
        this.sportObject = sportObject;
    }
}
