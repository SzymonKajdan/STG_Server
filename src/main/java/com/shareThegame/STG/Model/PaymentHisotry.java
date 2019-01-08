package com.shareThegame.STG.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.json.JSONPropertyIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.*;
import javax.persistence.Id;
import java.util.Date;

@Entity
public

class PaymentHisotry implements  Comparable<PaymentHisotry>{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double cost;
    private Date startrent;
    private Date  exprrent;
    private boolean stautsofpayment;
    private  Long   userid;
    private  Long sportobjectid;
    @ManyToOne
    private SportObject sportObject;
    @ManyToOne
    private  User user;
    @JSONPropertyIgnore
    public
    User getUser ( ) {
        return user;
    }

    public
    void setUser ( User user ) {
        this.user = user;
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

    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
    }

    public
    double getCost ( ) {
        return cost;
    }

    public
    void setCost ( double cost ) {
        this.cost = cost;
    }

    public
    Date getStartrent ( ) {
        return startrent;
    }

    public
    void setStartrent ( Date startrent ) {
        this.startrent = startrent;
    }

    public
    Date getExprrent ( ) {
        return exprrent;
    }

    public
    void setExprrent ( Date exprrent ) {
        this.exprrent = exprrent;
    }

    public
    boolean isStautsofpayment ( ) {
        return stautsofpayment;
    }

    public
    void setStautsofpayment ( boolean stautsofpayment ) {
        this.stautsofpayment = stautsofpayment;
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






    @Override
    public
    int compareTo ( PaymentHisotry o ) {
         if (getStartrent () == null || o.getStartrent () == null) {
            return 0;
        }
        return getStartrent ().compareTo(o.getStartrent ());
    }
}
