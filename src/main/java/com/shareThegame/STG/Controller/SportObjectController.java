package com.shareThegame.STG.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareThegame.STG.Model.*;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.TimeTableReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import com.shareThegame.STG.Repository.VisvilityObjectRepository;
import com.sun.javafx.collections.MappingChange;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;

@Controller
public
class SportObjectController {
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TimeTableReposiotry timeTableReposiotry;
    @Autowired
    VisvilityObjectRepository visvilityObjectRepository;


    @PostMapping ( value = "/getObjecByCity", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String getObjecByCity(String city) throws JsonProcessingException, JSONException {
        JSONObject jsonObject=new JSONObject (  );
        Map<String,Object> lista_hal = new HashMap<>();
        List<SportObject>ObjectinCity= sportObjectReposiotry.findAllByCity ( city );
        for(SportObject spiterstor:ObjectinCity){
            System.out.println (ObjectinCity.size () );


//            lista_hal.put ("miasto",spiterstor.getCity () );
//            lista_hal.put ( "adress",spiterstor.getAdress () );
//            lista_hal.put ( "timetable",spiterstor.getTimeTable () );
//            lista_hal.put ( "obectphoto",spiterstor.getObjectPhotos () );
           // lista_hal.put()







        }
        jsonObject.put ( "Terminarz",ObjectinCity );

        return jsonObject.toString ();

    }



    @PostMapping(value = "/addObject" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String addObjec( @Valid SportObject sportObject ){
        String username;
        try {
            org.springframework.security.core.userdetails.User currentUser =
                    ( org.springframework.security.core.userdetails.User ) SecurityContextHolder.getContext ( ).getAuthentication ( ).getPrincipal ( );
            username = currentUser.getUsername ( );
        } catch ( ClassCastException e ) {
            username = "anonymousUser";
            return "{}";
        }
        User  user= userRepository.findByEmail ( username );
        sportObject.setActive ( 0 );
        VisibilityObject visibilityObject=new VisibilityObject ();
       // visibilityObject.setSportobjectid ( (long)0 );
        visvilityObjectRepository.save ( visibilityObject );
       sportObject.setVisibilityObject ( visibilityObject);
        sportObject.setObjectPhotos ( new ArrayList <> (  ) );
        sportObject.setTimeTable ( new ArrayList <> (  ) );
        sportObject.setPaymentHisotries ( new ArrayList <> (  ) );
        sportObject.setObjectStars ( new ArrayList <> (  ) );
        sportObject.setOwnid ( user.getId () );
        sportObject.setActive ( 0 );
        JSONObject jsonObject = new JSONObject ( );

        if(user!=null) {


            sportObject.setOwnid ( user.getId ( ) );
            sportObjectReposiotry.save ( sportObject );


            jsonObject.put ( "satus" , "dodano" );
        }
        else{

            jsonObject.put ( "satus" , "nie dodano" );
        }
        return  jsonObject.toString ();


    }
}
