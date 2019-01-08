package com.shareThegame.STG.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shareThegame.STG.Model.*;
import com.shareThegame.STG.Repository.*;
import com.shareThegame.STG.Service.UserService;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeTableReposiotry tt;
    @Autowired
    private
    SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    private
    FavouriteObjectsRepository favouriteObjectsRepository;
    @Autowired
    private  TimeTableReposiotry timeTableReposiotry;
    @Autowired
    private
    VisvilityObjectRepository visvilityObjectRepository;
    @Autowired
    private
    ObjectExstrasRepository objectExstrasRepository;
    @Autowired
    private
    ObjectPhotosRepository objectPhotosRepository;
    @Autowired
    private
    PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    private
    ObjectStarsRepository objectStarsRepository;
    @Autowired
    private
    OpenHoursRepository openHoursRepository;

    @PostMapping ( value = "/register", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String register ( @Valid User user ) throws JSONException {


        JSONObject jsonObject = new JSONObject ( );
        if ( user != null && user.getUsername ( ) != null && user.getPassword ( ) != null ) {
            if ( user.getUsername ( ).length ( ) >= 5 && user.getPassword ( ).length ( ) >= 5 ) {


                User userExists = userRepository.findByUsernameOrEmail ( user.getUsername ( ),user.getEmail () );

                if ( userExists != null ) {
                    jsonObject.put ( "message" , "EXIST" );
                    return jsonObject.toString ( );
                }
                user.setSportObjects ( new ArrayList <> (  ) );
                user.setPaymentHisotries ( new ArrayList <> (  ) );
                user.setFavouriteObjects ( new ArrayList <> (  ) );

                user.setActive ( 1 );

                userService.saveUser ( user );

                jsonObject.put ( "message" , "SUCCESS" );
                return jsonObject.toString ( );
            }

            jsonObject.put ( "message" , "INCORRECT" );
            return jsonObject.toString ( );
        }

        jsonObject.put ( "message" , "SERVER_ERROR" );

        return jsonObject.toString ( );
    }

    @PostMapping ( value = "/login", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String logIn ( @Valid User user ) throws JSONException {
        JSONObject jsonObject = new JSONObject ( );
        String username = userAuth ( );

        System.out.println ( "xd" );
        if ( user != null && user.getUsername ( ) != null && user.getPassword ( ) != null ) {
            User userExist = userRepository.findByEmail ( username );
            System.out.println ( username );
            if ( userExist != null ) {
                if ( userService.comaprePassword ( userExist.getPassword ( ) , user.getPassword ( ) ) == true ) {
                    jsonObject.put ( "message" , "SUCCESS" );
                    jsonObject.put ( "userId",userExist.getId () );
                } else {
                    jsonObject.put ( "message" , "WRONG_PASSWORD_OR_USERNAME" );
                }
            } else {
                jsonObject.put ( "message" , "WRONG_PASSWORD_OR_USERNAME" );
            }


        } else {
            jsonObject.put ( "message" , "SERVER_ERROR" );
        }
        return jsonObject.toString ( );

    }

    @PostMapping ( value = "/resetPassword", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String ResetPassword( String newPassword,String oldPassword ) throws JSONException {
        JSONObject jsonObject = new JSONObject ( );
        String username=userAuth ();
        User user=userRepository.findByEmail ( username );

           // User userExist = userRepository.findByEmail ( username).getPassword ();
            if(userService.comaprePassword (user.getPassword (),oldPassword)){
                User userExist=userRepository.findByEmail ( username );
                userService.changePassword ( userExist,newPassword );

                jsonObject.put ( "message","haslo mzienione  " ) ;
            }
            else{

                jsonObject.put ( "message","stare haslo jest bełdne  " ) ;
            }




        return jsonObject.toString ( );
    }




    @PostMapping ( value = "/getUser", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    @ResponseBody public
    String giveBackUser(  ) throws JSONException, JsonProcessingException {
        System.out.println (" zwracam usera" );
        String usermail=userAuth ();
        User user=userRepository.findByEmail ( usermail );
        Map<String,Object> userMap = new HashMap<> ();
        JSONObject jsonObject=new JSONObject (  );
        if ( user != null ) {
            if(user.getUsername ()!=null) {
                jsonObject.put ( "username" , user.getUsername ( ) );
            }
            else {
                jsonObject.put ( "username" , "" );
            }
            if(user.getEmail ()!=null) {
                jsonObject.put ( "email" , user.getEmail ( ) );
            }
            else{
                jsonObject.put ( "email","" );
            }
            if ( user.getPhoneno ()!=null ) {
                jsonObject.put ( "phoneno" , user.getPhoneno ( ) );
            }
            else{
                jsonObject.put ( "phoneno" , "" );
            }
            if(user.getFirstname ()!=null) {
                jsonObject.put ( "firstname" , user.getFirstname ( ) );
            }
            else{
                jsonObject.put ( "firstname","" );
            }
            if( user.getLastname ()!=null){
                jsonObject.put ( "lastname",user.getLastname () );
            }
            else{
                jsonObject.put ( "lastname","" );
            }

            if(user.getPaypalemail ()!=null) {
                jsonObject.put ( "paypalemail" , user.getPaypalemail ( ) );
            }else{
                jsonObject.put ( "paypalemail" , "" );

            }
            if(user.getDateofbirth ()!=null){
                jsonObject.put ( "dateofbirth",user.getDateofbirth ());

            }else{
                jsonObject.put ( "dateofbirth","");
            }


        }
        System.out.println (" zwracam usera"+jsonObject.toString () );
        return jsonObject.toString ();




    }
    @PostMapping ( value = "/updateProfile", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String updateProfile(@Valid User user){
        String useremail=userAuth ();
        User userToUpdate=userRepository.findByEmail ( useremail );
        if(userToUpdate!=null) {
            System.out.println (user.getDateofbirth () );
            userToUpdate.setDateofbirth ( user.getDateofbirth ());
            userToUpdate.setEmail ( user.getEmail ( ) );
            userToUpdate.setPaypalemail ( user.getPaypalemail ( ) );
            userToUpdate.setFirstname ( user.getFirstname ( ) );
            userToUpdate.setLastname ( user.getLastname ( ) );
            userToUpdate.setPhoneno ( user.getPhoneno ( ) );
            userToUpdate.setUsername ( user.getUsername () );
//        toUpdate.setPhoto (  Base64.decodeBase64(user.getPhoto ()) );
            userRepository.save ( userToUpdate );

        return new JSONObject ( ).put ( "message","UPDATE_SUCCESS" ).toString ();
        }
        else {
            return new JSONObject ( ).put ( "message","UPDATE_ERROR" ).toString ();
        }
    }


    @PostMapping(value = "/deleteUser",produces = "application/json",consumes =  "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String deleteUser(@Valid User user){
        String useremail=userAuth ();
        User userToDelete=userRepository.findByEmail ( useremail );

        if(deleteRelationship (userToDelete )==true){


            return  new JSONObject (  ).put ( "message","DELETE_SUCCESS" ).toString ();
        }
        else{
            return  new JSONObject (  ).put ( "message","DELETE_ERROR" ).toString ();
        }

    }


    private String userAuth(){

        String username;
        try {
            org.springframework.security.core.userdetails.User currentUser =
                    ( org.springframework.security.core.userdetails.User ) SecurityContextHolder.getContext ( ).getAuthentication ( ).getPrincipal ( );
            username = currentUser.getUsername ( );
            return  username;
        } catch ( ClassCastException e ) {
            username = "anonymousUser";
            return "{}";
        }
    }
    private
    DateTime parseDateOfBirth ( String date ) {
        int year;
        int month;
        int day;
        int hour;
        int minutes;
        int sec;
        //string z data z posta


        year = Integer.parseInt ( date.substring ( 0 , date.length ( ) - 17 ) );
        month = Integer.parseInt ( date.substring ( 5 , date.length ( ) - 14 ) );
        day = Integer.parseInt ( date.substring ( 8 , date.length ( ) - 11 ) );
        hour = Integer.parseInt ( date.substring ( 11 , date.length ( ) - 8 ) );
        minutes = Integer.parseInt ( date.substring ( 14 , date.length ( ) - 5 ) );
        sec = Integer.parseInt ( date.substring ( 18 , date.length ( ) - 2 ) );

        return new DateTime ( year , month , day , hour , minutes , sec );
    }
    private
    boolean deleteRelationship(User userToDelete) {
        if ( userToDelete != null ) {
            List <SportObject> sportObjectsToDelete = sportObjectReposiotry.findByUser ( userToDelete );


            List <FavouriteObjects> favouriteObjectsListToDelete = userToDelete.getFavouriteObjects ( );

            if(sportObjectsToDelete.size ()!=0) {
                //System.out.println (sportObjectsToDelete.size () );
                for (SportObject item : sportObjectsToDelete) {


                    List <TimeTable> timeTableListToDelete = timeTableReposiotry.findAllBySportobjectid ( item.getId ( ) );
                    if(timeTableListToDelete.size ()!=0) {
                        item.getTimeTable ( ).removeAll ( timeTableListToDelete );
                        timeTableReposiotry.deleteAll ( timeTableListToDelete );
                    }
                    VisibilityObject visibilityObjectToDelete = visvilityObjectRepository.findBySportobjectid ( item.getId ( ) );
                   if(visibilityObjectToDelete!=null) {
                       item.setVisibilityObject ( null );
                       visvilityObjectRepository.delete ( visibilityObjectToDelete );
                   }

                   OpenHours openHours=openHoursRepository.findBySportobjectid ( item.getId () );
                   if(openHours!=null){
                       item.setOpenHours (null);
                       openHoursRepository.delete ( openHours );
                   }

                    ObjectExtras objectExtras = objectExstrasRepository.findBySportobjectid ( item.getId ( ) );
                   if(objectExtras!=null) {
                       item.setObjectExtras ( null );
                       objectExstrasRepository.delete ( objectExtras );
                   }
                    List <ObjectPhotos> objectPhotosListToDelete = objectPhotosRepository.findAllBySportobjectid ( item.getId ( ) );
                   if(objectPhotosListToDelete.size ()!=0) {
                       item.getObjectPhotos ( ).removeAll ( objectPhotosListToDelete );
                       objectPhotosRepository.deleteAll ( objectPhotosListToDelete );
                   }
                    List <PaymentHisotry> paymentHisotryListToDelete = paymentHistoryRepository.findAllBySportObject ( item );
                    List <PaymentHisotry> paymentHisotryList = paymentHistoryRepository.findAllByUser ( userToDelete );
                    if(paymentHisotryListToDelete.size ()!=0) {

                        item.getPaymentHisotries ( ).removeAll ( paymentHisotryListToDelete );

                    }
                    if(paymentHisotryList.size ()!=0){
                        item.getPaymentHisotries ( ).removeAll ( paymentHisotryList );

                            for(PaymentHisotry p:paymentHisotryList){

                                p.setUser ( null );
                            }
                    }


                    List <ObjectStars> objectStarsListToDelete = objectStarsRepository.findAllBySportobjectid ( item.getId ( ) );
                    if(objectStarsListToDelete.size ()!=0) {
                        item.getObjectStars ( ).removeAll ( objectStarsListToDelete );
                        objectStarsRepository.deleteAll ( objectStarsListToDelete );
                    }
                    item.setUser ( null );
                }
                userToDelete.getSportObjects ( ).removeAll ( sportObjectsToDelete );
            }
            else{


            }

            if(favouriteObjectsListToDelete.size ()!=0){
                userToDelete.getFavouriteObjects ( ).removeAll ( favouriteObjectsListToDelete );
                for(FavouriteObjects p:favouriteObjectsListToDelete){

                    p.setUser ( null );
                }

                favouriteObjectsRepository.deleteAll ( favouriteObjectsRepository.findByUserid ( userToDelete.getId ( ) ) );
            }

            List <PaymentHisotry> paymentHisotryList = paymentHistoryRepository.findAllByUser ( userToDelete );
            if(paymentHisotryList.size ()!=0){
                for(PaymentHisotry p:paymentHisotryList){

                    p.setUser ( null );
                }

                paymentHisotryList.removeAll ( paymentHistoryRepository.findAllByUser ( userToDelete ) );
                paymentHistoryRepository.deleteAll ( paymentHisotryList );

            }


            // userToDelete.getPaymentHisotries ().removeAll (  )
           // userToDelete.setPaymentHisotries (null);


            userRepository.delete ( userToDelete );

                sportObjectReposiotry.deleteAll ( sportObjectReposiotry.findAllByOwnid ( userToDelete.getId ( ) ) );
                for (SportObject item : sportObjectsToDelete)
                    paymentHistoryRepository.deleteAll ( paymentHistoryRepository.findAllBySportobjectid ( item.getId ( ) ) );
                //paymentHistoryRepository.deleteAll (paymentHistoryRepository.fin);
                return true;


        } else {
            return false;
        }
    }
}





