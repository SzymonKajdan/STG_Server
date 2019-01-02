package com.shareThegame.STG.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class VisibilityObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startvsibility;
    private  Date exprvisbility;
    private Long    sportobjectid;





    public Long getId ( ) {
        return id;
    }

    public void setId ( Long id ) {
        this.id = id;
    }

    public Date getStartvsibility ( ) {
        return startvsibility;
    }

    public void setStartvsibility ( Date startvsibility ) {
        this.startvsibility = startvsibility;
    }

    public Date getExprvisbility ( ) {
        return exprvisbility;
    }

    public void setExprvisbility ( Date exprvisbility ) {
        this.exprvisbility = exprvisbility;
    }

    public Long getSportobjectid ( ) {
        return sportobjectid;
    }

    public void setSportobjectid ( Long sportobjectid ) {
        this.sportobjectid = sportobjectid;
    }


}
