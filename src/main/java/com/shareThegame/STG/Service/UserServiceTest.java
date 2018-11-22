package com.shareThegame.STG.Service;


import com.shareThegame.STG.Model.*;
import com.shareThegame.STG.Repository.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
public
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    private TimeTableReposiotry timeTableReposiotry;
    @Autowired
    private VisvilityObjectRepository visvilityObjectRepository;
    @Autowired
    private ObejctExstrasRepository obejctExstrasRepository;
    @Autowired
    private ObjectPhotosRepository objectPhotosRepository;
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private ObjectPhotoService objectPhotoService;
    @Autowired
    private ObjectStarsRepository objectStarsRepository;

    @PostConstruct
    public
    void setUp ( ) {

        //tworze pierwszy obiekt sportowy


        SportObject sportObject = new SportObject ( );


        sportObject.setActive ( 1 );
        sportObject.setAdress ( "Ignacego Skorupki 21, 90-001 Łódź" );
        sportObject.setCity ( "Lodz" );
        sportObject.setLocalno ( "21" );
        sportObject.setOwnid ( ( ( long ) 1 ) );
        sportObject.setPpmail ( "sportObject@stg.com" );
        sportObject.setPhoneno ( "42 636 77 88" );
        sportObject.setRentprice ( 100 );
        sportObject.setZipcode ( "90-001" );
        sportObject.setOwnid ( ( long ) 1 );
        sportObject.setActive ( 1 );
        sportObject.setBadminton ( true );
        sportObject.setVolleball ( true );
        sportObject.setHandball ( false );
        sportObject.setSoccer ( false );
        sportObject.setFustal ( true );
        sportObject.setBasketball ( true );
        sportObject.setSquash ( false );
        sportObject.setTimeTable ( new ArrayList <> ( ) );
        sportObject.setObjectPhotos ( new ArrayList <> ( ) );
        sportObject.setPaymentHisotries ( new ArrayList <> ( ) );
        sportObject.setObjectStars ( new ArrayList <> ( ) );
        sportObject.setName ( "Mosir Skorupki " );

//ocena hali
        ObjectStars objectStars = new ObjectStars ( );
        objectStars.setUserid ( ( long ) 1 );
        objectStars.setValue ( 3 );
        objectStars.setSportobjectid ( ( long ) 1 );
        //objectStars.setSportObject ( sportObject );
        objectStarsRepository.save ( objectStars );


//terminarz dla hali nr 1
        TimeTable timeTable = new TimeTable ( );
        timeTable.setPrice ( ( long ) 100 );
        timeTable.setPaymenttype ( "pp" );
        timeTable.setRenterid ( ( long ) 1 );
        Date start = new DateTime ( 2018 , 11 , 20, 18 , 00  ).toDate ( );
        Date end = new DateTime ( 2018 , 11 , 20 , 19 , 00 ).toDate ( );

        timeTable.setStartrent ( start );
        timeTable.setEndrend ( end );
        timeTable.setSportobjectid ( ( long ) 1 );
        if ( timeTableReposiotry.findBySportobjectid ( ( long ) 1 ) == null ) {
            timeTableReposiotry.save ( timeTable );
        }
//Widocznosc peirwszej hali w mapach

        VisibilityObject visibilityObject = new VisibilityObject ( );
        visibilityObject.setId ( ( long ) 1 );
        visibilityObject.setSportobjectid ( ( long ) 1 );
        start = new DateTime ( 2018 , 9 , 10 , 18 , 0 , 0 ).toDate ( );
        visibilityObject.setStartvsibility ( start );
        end = new DateTime ( 2019 , 9 , 11 , 15 , 00 , 00 ).toDate ( );
        visibilityObject.setExprvisbility ( end );
        if ( visvilityObjectRepository.findBySportobjectid ( ( long ) 1 ) == null ) {
            visvilityObjectRepository.save ( visibilityObject );
        }



//Co hala oferuje
        ObjectExtras objectExtras = new ObjectExtras ( );
        objectExtras.setParking ( true );
        objectExtras.setArtificiallighting ( true );
        objectExtras.setToilets ( true );
        objectExtras.setSportobjectid ( ( long ) 1 );
        if ( obejctExstrasRepository.findBySportobjectid ( ( long ) 1 ) == null ) {
            obejctExstrasRepository.save ( objectExtras );


        }

//Zdjecia hali
        ObjectPhotos objectPhoto = new ObjectPhotos ( );
        objectPhoto.setSportobjectid ( ( long ) 1 );

        byte[] fileContent = null;
       try {
            ClassPathResource imgFile = new ClassPathResource ( "img/2.jpg" );
            fileContent = StreamUtils.copyToByteArray ( imgFile.getInputStream ( ) );
        } catch ( IOException e ) {
            e.printStackTrace ( );
        }

        objectPhoto.setPhoto ( fileContent);
        if ( objectPhotosRepository.findBySportobjectid ( ( long ) 1 ) == null ) {
            objectPhotosRepository.save ( objectPhoto );
        }
//hisotria paltnosci za hale
        PaymentHisotry paymentHisotry = new PaymentHisotry ( );
        paymentHisotry.setCost ( 100 );
        paymentHisotry.setUserid ( ( long ) 1 );
        paymentHisotry.setSportobjectid ( ( long ) 1 );
        paymentHisotry.setStautsofpayment ( true );
        paymentHisotry.setExprrent ( new DateTime ( 2018 , 11 , 20 , 19 , 00 ).toDate ( ) );
        paymentHisotry.setStartrent ( new DateTime ( 2018 , 11 , 20 , 18 , 00 ).toDate ( ) );

        if ( paymentHistoryRepository.findByUserid ( ( long ) 1 ) == null ) {
            paymentHistoryRepository.save ( paymentHisotry );
        }


        if ( sportObjectReposiotry.findAllById ( ( long ) 1 ).size ( ) == 0 ) {
            sportObject.getTimeTable ( ).add ( timeTable );
            sportObject.setObjectExtras ( objectExtras );
            sportObject.setVisibilityObject ( ( visibilityObject ) );
            sportObject.getObjectPhotos ( ).add ( objectPhoto );
            sportObject.getPaymentHisotries ( ).add ( paymentHisotry );
            sportObject.getObjectStars ( ).add ( objectStars );

            System.out.println ( "pierwsza hala dodana" );
            sportObjectReposiotry.save ( sportObject );

            //zpaisuje info o hali
            objectStars.setSportObject ( sportObject );
            objectStarsRepository.save ( objectStars );

        } else {
            System.out.println ( "hala juz isntieje" );
        }


        Role role = new Role ( );
        role.setRole ( "CLIENT" );

        if ( roleRepository.findByRole ( "CLIENT" ) == null ) {
            roleRepository.save ( role );
            System.out.println ( "stworzylem role " );
        }

        User test = new User ( );
        test.setPassword ( "admin" );
        test.setUsername ( "admin" );
        test.setEmail ( "22321@dsaas.co" );
        test.setActive ( 1 );
        test.setPhoneno ( "790 540 834" );

        test.setSportObjects ( new ArrayList <> ( ) );
        test.getSportObjects ( ).add ( sportObject );


        if ( userRepository.findByUsername ( "admin" ) == null ) {

            System.out.println ( "inializuje uytkonikow" );
            test.setPaymentHisotries ( new ArrayList <> ( ) );
            test.getPaymentHisotries ( ).add ( paymentHisotry );
            userService.saveUser ( test );

        }

    }


}



