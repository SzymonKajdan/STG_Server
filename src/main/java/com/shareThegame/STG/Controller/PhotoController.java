package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.ObjectPhotos;
import com.shareThegame.STG.Repository.ObjectPhotosRepository;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Controller
public
class PhotoController {
    @Autowired
    ObjectPhotosRepository objectPhotosRepository;

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
}