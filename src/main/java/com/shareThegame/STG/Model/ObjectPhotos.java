package com.shareThegame.STG.Model;

import javax.persistence.*;
import java.util.Base64;

@Entity
public
class ObjectPhotos {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY )
    private Long id;
    private Long sportobjectid;
    @Lob
    //Base64.getEncoder().encodeToString(image)
   // private String photo;
    private byte[] photo;

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
    String getPhoto ( ) {
        return Base64.getEncoder().encodeToString(photo);
    }

    public
    void setPhoto ( String photoToConvert ) {
        this.photo =Base64.getDecoder ( ).decode (photoToConvert  );
    }
    public
    void setPhoto ( byte[]photo ) {
        this.photo =photo;
    }
//    public
//    SportObject getSportObject ( ) {
//        return sportObject;
//    }
//
//    public
//    void setSportObject ( SportObject sportObject ) {
//        this.sportObject = sportObject;
//    }
//
//    @ManyToOne
//    private SportObject sportObject;
}
