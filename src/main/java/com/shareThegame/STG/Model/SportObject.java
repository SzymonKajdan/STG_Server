package com.shareThegame.STG.Model;

import com.shareThegame.STG.Repository.TimeTableReposiotry;
import org.hibernate.annotations.LazyToOne;
import org.json.JSONPropertyIgnore;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class SportObject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private  String adress;

    private String street;

    private String localno;

    private String zipcode;

    private String ppmail;

    private int active;

    private int rentprice;

    private  Long ownid;

    private  String name;

    private boolean fustal;

    private  boolean volleball;

    private boolean  soccer;

    private  boolean tennis;

    private  boolean squash ;

    private  boolean handball;

    private  boolean basketball;

    private  boolean badminton;


    private  String phoneno;

    private  String open;

    private  String close;


    @OneToMany
    private List<TimeTable> timeTable;

    @OneToOne
    private VisibilityObject visibilityObject;

    @OneToOne
    private ObjectExtras objectExtras;

    @OneToMany
    private List<ObjectPhotos> objectPhotos;

    @OneToMany
    private  List<PaymentHisotry> paymentHisotries;

    @OneToMany
    private  List<ObjectStars> objectStars;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocalno() {
        return localno;
    }

    public void setLocalno(String localno) {
        this.localno = localno;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPpmail() {
        return ppmail;
    }

    public void setPpmail(String ppmail) {
        this.ppmail = ppmail;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getRentprice() {
        return rentprice;
    }

    public void setRentprice(int rentprice) {
        this.rentprice = rentprice;
    }

    public Long getOwnid() {
        return ownid;
    }

    public void setOwnid(Long ownid) {
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

    public List<ObjectPhotos> getObjectPhotos() {
        return objectPhotos;
    }

    public void setObjectPhotos(List<ObjectPhotos> objectPhotos) {
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

    public ObjectExtras getObjectExtras() {
        return objectExtras;
    }

    public void setObjectExtras(ObjectExtras objectExtras) {
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
    boolean isFustal ( ) {
        return fustal;
    }

    public
    void setFustal ( boolean fustal ) {
        this.fustal = fustal;
    }

    public
    boolean isVolleball ( ) {
        return volleball;
    }

    public
    void setVolleball ( boolean volleball ) {
        this.volleball = volleball;
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
}
