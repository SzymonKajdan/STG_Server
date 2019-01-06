package com.shareThegame.STG.Model;

import com.shareThegame.STG.Repository.TimeTableReposiotry;
import org.hibernate.annotations.LazyToOne;
import org.joda.time.DateTime;
import org.json.JSONPropertyIgnore;

import javax.persistence.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public
class SportObject {

    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;

    private String city;


    private String street;

    private String localno;

    private String zipcode;

    private String ppmail;

    private int active;

    private double rentprice;

    private Long ownid;

    private String name;

    private boolean futsal;

    private boolean volleyball;

    private boolean soccer;

    private boolean tennis;

    private boolean squash;

    private boolean handball;

    private boolean basketball;

    private boolean badminton;

    private String zipcodecity;

    private String siteaddress;

    private String email;

    private String phoneno;

    private String open;

    private String close;

    private double latitude;

    private double longitude;


    @OneToMany
    private List <TimeTable> timeTable;

    @OneToOne
    private VisibilityObject visibilityObject;

    @OneToOne
    private ObjectExtras objectExtras;

    @OneToMany
    private List <ObjectPhotos> objectPhotos;

    @OneToMany
    private List <PaymentHisotry> paymentHisotries;

    @OneToMany
    private List <ObjectStars> objectStars;

    @OneToOne
    private OpenHours openHours;

    @JSONPropertyIgnore
    public
    User getUser ( ) {
        return user;
    }

    public
    void setUser ( User user ) {
        this.user = user;
    }

    @ManyToOne
    private User user;


    public
    Long getId ( ) {
        return id;
    }

    public
    void setId ( Long id ) {
        this.id = id;
    }

    public
    String getCity ( ) {
        return city;
    }

    public
    void setCity ( String city ) {
        this.city = city;
    }


    public
    String getStreet ( ) {
        return street;
    }

    public
    void setStreet ( String street ) {
        this.street = street;
    }

    public
    String getLocalno ( ) {
        return localno;
    }

    public
    void setLocalno ( String localno ) {
        this.localno = localno;
    }

    public
    String getZipcode ( ) {
        return zipcode;
    }

    public
    void setZipcode ( String zipcode ) {
        this.zipcode = zipcode;
    }

    public
    String getPpmail ( ) {
        return ppmail;
    }

    public
    void setPpmail ( String ppmail ) {
        this.ppmail = ppmail;
    }

    public
    int getActive ( ) {
        return active;
    }

    public
    void setActive ( int active ) {
        this.active = active;
    }

    public
    double getRentprice ( ) {
        return rentprice;
    }

    public
    void setRentprice ( double rentprice ) {
        this.rentprice = rentprice;
    }

    public
    Long getOwnid ( ) {
        return ownid;
    }

    public
    void setOwnid ( Long ownid ) {
        this.ownid = ownid;
    }

    public
    List <TimeTable> getTimeTable ( ) {
        return timeTable;
    }

    public
    void setTimeTable ( List <TimeTable> timeTable ) {
        this.timeTable = timeTable;
    }

    public
    List <ObjectPhotos> getObjectPhotos ( ) {
        return objectPhotos;
    }

    public
    void setObjectPhotos ( List <ObjectPhotos> objectPhotos ) {
        this.objectPhotos = objectPhotos;
    }

    public
    VisibilityObject getVisibilityObject ( ) {
        return visibilityObject;
    }

    public
    void setVisibilityObject ( VisibilityObject visibilityObject ) {
        this.visibilityObject = visibilityObject;
    }

    public
    ObjectExtras getObjectExtras ( ) {
        return objectExtras;
    }

    public
    void setObjectExtras ( ObjectExtras objectExtras ) {
        this.objectExtras = objectExtras;
    }

    public
    List <PaymentHisotry> getPaymentHisotries ( ) {
        return paymentHisotries;
    }

    public
    void setPaymentHisotries ( List <PaymentHisotry> paymentHisotries ) {
        this.paymentHisotries = paymentHisotries;
    }

    public
    List <ObjectStars> getObjectStars ( ) {
        return objectStars;
    }

    public
    void setObjectStars ( List <ObjectStars> objectStars ) {
        this.objectStars = objectStars;
    }

    public
    String getPhoneno ( ) {
        return phoneno;
    }

    public
    void setPhoneno ( String phoneno ) {
        this.phoneno = phoneno;
    }

    public
    boolean isFutsal ( ) {
        return futsal;
    }

    public
    void setFutsal ( boolean futsal ) {
        this.futsal = futsal;
    }


    public
    boolean isVolleyball ( ) {
        return volleyball;
    }

    public
    void setVolleyball ( boolean volleyball ) {
        this.volleyball = volleyball;
    }

    public
    boolean isSoccer ( ) {
        return soccer;
    }

    public
    void setSoccer ( boolean soccer ) {
        this.soccer = soccer;
    }

    public
    boolean isTennis ( ) {
        return tennis;
    }

    public
    void setTennis ( boolean tennis ) {
        this.tennis = tennis;
    }

    public
    boolean isSquash ( ) {
        return squash;
    }

    public
    void setSquash ( boolean squash ) {
        this.squash = squash;
    }

    public
    boolean isHandball ( ) {
        return handball;
    }

    public
    void setHandball ( boolean handball ) {
        this.handball = handball;
    }

    public
    boolean isBasketball ( ) {
        return basketball;
    }

    public
    void setBasketball ( boolean basketball ) {
        this.basketball = basketball;
    }

    public
    boolean isBadminton ( ) {
        return badminton;
    }

    public
    void setBadminton ( boolean badminton ) {
        this.badminton = badminton;
    }


    public
    String getName ( ) {
        return name;
    }

    public
    void setName ( String name ) {
        this.name = name;
    }

    public
    String getOpen ( ) {
        return open;
    }

    public
    void setOpen ( String open ) {
        this.open = open;
    }

    public
    String getClose ( ) {
        return close;
    }

    public
    void setClose ( String close ) {
        this.close = close;
    }

    public
    String getEmail ( ) {
        return email;
    }

    public
    void setEmail ( String email ) {
        this.email = email;
    }

    public
    double getLatitude ( ) {
        return latitude;
    }

    public
    void setLatitude ( double latitude ) {
        this.latitude = latitude;
    }

    public
    double getLongitude ( ) {
        return longitude;
    }

    public
    void setLongitude ( double longitude ) {
        this.longitude = longitude;
    }

    public
    String getZipcodecity ( ) {
        return zipcodecity;
    }

    public
    void setZipcodecity ( String zipcodecity ) {
        this.zipcodecity = zipcodecity;
    }

    public
    String getSiteaddress ( ) {
        return siteaddress;
    }

    public
    void setSiteaddress ( String siteaddress ) {
        this.siteaddress = siteaddress;
    }

    public
    OpenHours getOpenHours ( ) {
        return openHours;
    }

    public
    void setOpenHours ( OpenHours openHours ) {
        this.openHours = openHours;
    }

    public
    int calc ( ) {
        double startHour = hourToDouble ( open );
        double endHour = hourToDouble ( close );
        int hoursCount = ( int ) ( ( endHour - startHour ) * 2 );
        return hoursCount;

    }

    public
    double hourToDouble ( String hour ) {
        String hours, minutes;
        hours = hour.substring ( 0 , 2 );
        minutes = hour.substring ( 3 , 5 );

        double dHour;
        double dMinutes;

        dHour = Double.parseDouble ( hours );
        if ( minutes.equals ( "00" ) ) {
            dMinutes = 0.0;
        }
        else {
            dMinutes = 0.5;
        }

        return dHour + dMinutes;
    }

    public
    Date getDate ( String day ) {
        int hour = Integer.parseInt ( day.substring ( 0 , 2 ) );
        String min = day.substring ( 3 , 5 );
        int minutes;
        if ( min.equals ( "00" ) ) {
            minutes = 0;
        }
        else {
            minutes = 30;
        }
        DateTime dt = new DateTime()
                .withHourOfDay(hour)
                .withMinuteOfHour(minutes)
                .withSecondOfMinute(0).withMillisOfSecond ( 0 );
        //   System.out.println (hour );
        return dt.toDate ();

    }
    public
    Date getDate ( String day,int month ,int dayofMonth,int year ) {
        int hour = Integer.parseInt ( day.substring ( 0 , 2 ) );
        String min = day.substring ( 3 , 5 );
        int minutes;
        if ( min.equals ( "00" ) ) {
            minutes = 0;
        }
        else {
            minutes = 30;
        }
        DateTime dt = new DateTime().withMonthOfYear ( month ).withYear (  year ).withDayOfMonth ( dayofMonth )
                .withHourOfDay(hour)
                .withMinuteOfHour(minutes)
                .withSecondOfMinute(0).withMillisOfSecond ( 0 );
        //   System.out.println (hour );
        return dt.toDate ();

    }
}
