package com.shareThegame.STG.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shareThegame.STG.Model.ObjectPhotos;
import com.shareThegame.STG.Model.ObjectStars;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.ObjectPhotosRepository;
import com.shareThegame.STG.Repository.ObjectStarsRepository;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public
class ObjectStarsController {
    @Autowired
    ObjectStarsRepository objectStarsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;

    @PostMapping ( value = "/addMark", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String addMark(Long sportobjectid ,long mark) throws JsonProcessingException, JSONException {
        User user=userRepository.findByEmail ( userAuth () );
        ObjectStars objectStars=objectStarsRepository.findBySportobjectidAndUserid ( sportobjectid,user.getId () );
        if(objectStars==null){
            if(sportObjectReposiotry.getOne ( sportobjectid )!=null) {
                objectStars.setSportObject ( sportObjectReposiotry.getOne ( sportobjectid ) );
                objectStars.setSportobjectid ( sportobjectid );
                objectStars.setValue ( mark );
                objectStars.setUserid ( user.getId () );
                objectStarsRepository.save ( objectStars );
                return new JSONObject ().put (  "message","Succes"  ).toString ();
            }else{
                return new JSONObject (  ).put ( "message","Error_Sport_Object_Not_Exist" ).toString ();
            }

        }
        else{
            if(sportObjectReposiotry.getOne ( sportobjectid )!=null) {
                objectStars.setValue ( mark );
                objectStarsRepository.save ( objectStars );
                return new JSONObject ().put (  "message","Succes"  ).toString ();
            }
            else {
                return new JSONObject (  ).put ( "message","Error_Sport_Object_Not_Exist" ).toString ();
            }

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
}
