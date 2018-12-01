package com.shareThegame.STG.Model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private long id;

    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    private String phoneno;

    private String firstname;

    private  String lastname;

    private  String paypalemail;

    private Date dateofbirth;

    private int active;

    private byte[] photo;

    @ManyToMany
    @JoinTable ( name = "user_role", joinColumns = @JoinColumn ( name = "user_id" ), inverseJoinColumns = @JoinColumn ( name = "role_id" ) )
    private List<Role> roles;

    @OneToMany
    private  List<SportObject> sportObjects;

    @NotFound (action = NotFoundAction.IGNORE)
    @OneToMany
    private  List<PaymentHisotry>paymentHisotries;


    @OneToMany
    List<FavouriteObjects> favouriteObjects;


    public long getId( ) {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getEmail( ) {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getUsername( ) {
        return username;
    }

    public void setUsername( String username ) {
        this.username = username;
    }

    public String getPassword( ) {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getPhoneno( ) {
        return phoneno;
    }

    public void setPhoneno( String phoneno ) {
        this.phoneno = phoneno;
    }

    public List <Role> getRoles( ) {
        return roles;
    }

    public void setRoles( List <Role> roles ) {
        this.roles = roles;
    }

    public int getActive( ) {
        return active;
    }

    public void setActive( int active ) {
        this.active = active;
    }

    public
    void setId ( long id ) {
        this.id = id;
    }

    public
    List <SportObject> getSportObjects ( ) {
        return sportObjects;
    }

    public
    void setSportObjects ( List <SportObject> sportObjects ) {
        this.sportObjects = sportObjects;
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
    String getPhoto ( ) {
        return Base64.getEncoder().encodeToString(photo);
    }

    public
    void setPhoto ( byte[] photo ) {
        this.photo = photo;
    }

    public
    List <FavouriteObjects> getFavouriteObjects ( ) {
        return favouriteObjects;
    }

    public
    void setFavouriteObjects ( List <FavouriteObjects> favouriteObjects ) {
        this.favouriteObjects = favouriteObjects;
    }

    public
    String getFirstname ( ) {
        return firstname;
    }

    public
    void setFirstname ( String firstname ) {
        this.firstname = firstname;
    }

    public
    String getLastname ( ) {
        return lastname;
    }

    public
    void setLastname ( String lastname ) {
        this.lastname = lastname;
    }

    public
    Date getDateofbirth ( ) {
        return dateofbirth;
    }

    public
    void setDateofbirth ( Date dateofbirth ) {
        this.dateofbirth = dateofbirth;
    }

    public
    String getPaypalemail ( ) {
        return paypalemail;
    }

    public
    void setPaypalemail ( String paypalemail ) {
        this.paypalemail = paypalemail;
    }
}
