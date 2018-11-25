package com.shareThegame.STG.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.TimeTable;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.TimeTableReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import com.shareThegame.STG.Service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TimeTableReposiotry tt;

    @PostMapping ( value = "/register", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String register ( @Valid User user ) throws JSONException {


        JSONObject jsonObject = new JSONObject ( );
        if ( user != null && user.getUsername ( ) != null && user.getPassword ( ) != null ) {
            if ( user.getUsername ( ).length ( ) > 4 && user.getPassword ( ).length ( ) > 5 ) {


                User userExists = userRepository.findByUsername ( user.getUsername ( ) );

                if ( userExists != null ) {
                    jsonObject.put ( "message" , "taki uzytwnonik juz isntieje " );
                    return jsonObject.toString ( );
                }
                user.setSportObjects ( new ArrayList <> (  ) );
                user.setPaymentHisotries ( new ArrayList <> (  ) );
                user.setActive ( 1 );

                userService.saveUser ( user );

                jsonObject.put ( "message" , "Zarejestrowałeś się pomyślnie" );
                return jsonObject.toString ( );
            }

            jsonObject.put ( "message" , "uzytkownik lub hasło nieprawidłowe" );
            return jsonObject.toString ( );
        }

        jsonObject.put ( "message" , "Wystąpił problem z rejestracją, spróbuj ponownie" );

        return jsonObject.toString ( );
    }

    @PostMapping ( value = "/login", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String logIn ( @Valid User user ) throws JSONException {
        JSONObject jsonObject = new JSONObject ( );
        String username=userAuth ();


        if ( user != null && user.getUsername ( ) != null && user.getPassword ( ) != null ) {
            User userExist = userRepository.findByEmail ( username );
            System.out.println (username );
            if ( userExist != null ) {

                if ( userService.comaprePassword ( userExist.getPassword ( ) , user.getPassword ( ) ) == true )
                    jsonObject.put ( "message" , "zalogowano" );


                else {
                    jsonObject.put ( "message" , "bledny login lub haslo  " );
                }
            } else {
                jsonObject.put ( "message" , "bledny login lub haslo  " );
            }


        } else {
            jsonObject.put ( "message" , "Ups cos poszlo nie tak sproboj ponownie za chwile" );
        }
        return jsonObject.toString ( );

    }
    @PostMapping ( value = "/resetPassword", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String ResetPassword( @Valid User user,String newPassword ) throws JSONException {
        JSONObject jsonObject = new JSONObject ( );
        String username=userAuth ();
        if(userRepository.findByEmail ( username ).getUsername ().equals (   user.getUsername () )){
           // User userExist = userRepository.findByEmail ( username).getPassword ();
            if(userService.comaprePassword ( userRepository.findByEmail ( username).getPassword (),user.getPassword () )){
                User userExist=userRepository.findByEmail ( username );
                userService.changePassword ( userExist,newPassword );

                jsonObject.put ( "message","haslo mzienione  " ) ;
            }
            else{

                jsonObject.put ( "message","stare haslo jest bełdne  " ) ;
            }

        }
        else{
            jsonObject.put ( "message","cos poszlo nie tak " ) ;
        }

        return jsonObject.toString ( );
    }

    @PostMapping ( value = "/getUser", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )@ResponseBody
    public
    String giveBackUser(  ) throws JSONException, JsonProcessingException {

        String username=userAuth ();
        List <User> list = new ArrayList <> ( );

        User usertoGive = userRepository.findByEmail ( username );
        list.add ( usertoGive );
         JSONObject jsonObject = new JSONObject (usertoGive );
       // JSONObject jsonObject = new JSONObject ( );

      //  jsonObject.put ( " uzytkonik " , list );
         return  jsonObject.toString ();
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

}





