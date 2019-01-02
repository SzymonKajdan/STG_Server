package com.shareThegame.STG.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONPropertyIgnore;

import javax.persistence.*;

@Entity
public
class FavouriteObjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id ;
    private  Long userid;
    private  Long sportobjectid;


    @ManyToOne
    User user;
    
    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
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
    Long getSportobjectid ( ) {
        return sportobjectid;
    }

    public
    void setSportobjectid ( Long sportobjectid ) {
        this.sportobjectid = sportobjectid;
    }

    @JSONPropertyIgnore
    public
    User getUser ( ) {
        return user;
    }

    public
    void setUser ( User user ) {
        this.user = user;
    }
}
