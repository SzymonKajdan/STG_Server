package com.shareThegame.STG.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public
class TimeTable implements  Comparable<TimeTable> {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private long id;
    private String paymenttype;
    private double price;
    private Date startrent;
    private Date endrend;
    private Long sportobjectid;
    private Long renterid;




    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
    }

    public
    String getPaymenttype ( ) {
        return paymenttype;
    }

    public
    void setPaymenttype ( String paymenttype ) {
        this.paymenttype = paymenttype;
    }

    public
    double getPrice ( ) {
        return price;
    }

    public
    void setPrice ( double price ) {
        this.price = price;
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
    Long getRenterid ( ) {
        return renterid;
    }

    public
    void setRenterid ( Long renterid ) {
        this.renterid = renterid;
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
    Date getEndrend ( ) {
        return endrend;
    }

    public
    void setEndrend ( Date endrend ) {
        this.endrend = endrend;
    }


    @Override
    public
    int compareTo ( TimeTable o ) {
        if (getStartrent () == null || o.getStartrent () == null) {
            return 0;
        }
        return getStartrent ().compareTo(o.getStartrent ());
    }
}
