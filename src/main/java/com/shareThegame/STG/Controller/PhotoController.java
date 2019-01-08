package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.ObjectPhotos;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.ObjectPhotosRepository;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Controller
public
class PhotoController {
    @Autowired
    ObjectPhotosRepository objectPhotosRepository;
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    UserRepository userRepository;

    @PostMapping ( value = "/getImg", produces = "application/json" )
    public @ResponseBody
    String getImg ( long sportobjectid ) {
        List <ObjectPhotos> objectPhotos = objectPhotosRepository.findAllBySportobjectid ( sportobjectid );
        Map <String, Object> photos = new HashMap <> ( );
        String xd=null;
        JSONObject jsonObject = new JSONObject ( );
        System.out.println (objectPhotos.get ( 0 ).getPhoto ());

        //JSONObject jsonObject = new JSONObject ( );
        jsonObject.put ( "Photos of object " , objectPhotos );
        return jsonObject.toString ();

    }

    @PostMapping(value ="/deletePhoto",produces = "application/json")
    public  @ResponseBody
    String deleteImg(Long sportobjectid,long photoid){
        String useremail=userAuth ();

        User user=userRepository.findByEmail ( useremail );
        SportObject sportObject=sportObjectReposiotry.getOne ( sportobjectid );
        JSONObject json=new JSONObject (  );
        if(sportObject!=null&&sportObject.getOwnid ()==user.getId ()) {
            List<ObjectPhotos> objectPhotosList=objectPhotosRepository.findAllBySportobjectid ( sportobjectid );
            ObjectPhotos toDelete=new ObjectPhotos ();
            for (ObjectPhotos item:objectPhotosList) {
                if(item.getId ()==photoid){
                    toDelete=item;
                    break;
                }
            }
            if(toDelete!=null){
                sportObject.getObjectPhotos ().remove ( toDelete );
                objectPhotosRepository.delete ( toDelete );
                json.put ( "staus","ok" );

            }else
            {
                json.put ( "status","error" );
            }

        }
        return json.toString ();
    }



    private
    String userAuth () {


        String username;
        try {
            org.springframework.security.core.userdetails.User currentUser =
                    ( org.springframework.security.core.userdetails.User ) SecurityContextHolder.getContext ( ).getAuthentication ( ).getPrincipal ( );
            username = currentUser.getUsername ( );
            return username;
        } catch ( ClassCastException e ) {
            username = "anonymousUser";
            return "{}";
        }
    }
}