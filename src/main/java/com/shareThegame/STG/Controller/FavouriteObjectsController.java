package com.shareThegame.STG.Controller;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.shareThegame.STG.Model.FavouriteObjects;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.FavouriteObjectsRepository;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import com.sun.javafx.collections.MappingChange;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
public
class FavouriteObjectsController {
    @Autowired
    FavouriteObjectsRepository favouriteObjectsRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;

    @PostMapping (value = "/addObjectToFav" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String addObjectToFav(Long sportobjectid){
        User user=userRepository.findByEmail ( userAuth () );
        System.out.println ("user id "+user.getId ()+ " sport object id "+sportobjectid );
        if(user!=null){


            if(checkFav ( user.getFavouriteObjects (),sportobjectid)==false){
                FavouriteObjects favouriteObjects=new FavouriteObjects ();
                favouriteObjects.setUser ( user );
                favouriteObjects.setUserid ( user.getId () );
                favouriteObjects.setSportobjectid ( sportobjectid );
                favouriteObjectsRepository.save ( favouriteObjects );
                user.getFavouriteObjects ().add (  favouriteObjects);
                userRepository.save ( user );
                return  new JSONObject (  ).put ( "message","SUCCESS" ).toString ();
            }
            else{
                return  new JSONObject (  ).put ( "message","EXIST" ).toString ();
            }


        }else{

            return new JSONObject (  ).put ( "message","ERROR" ).toString ();

        }

    }
    @PostMapping (value = "/getFavorites" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String getFavorites(){
        User user=userRepository.findByEmail ( userAuth () );
        List<FavouriteObjects> favouriteObjectsList=user.getFavouriteObjects ();
        JSONArray jsonObject= new JSONArray (  );

        for(FavouriteObjects item:favouriteObjectsList){
            Map<String, Long> map = new HashMap<String, Long>();
            map.put ( "id",item.getSportobjectid () );
            jsonObject.put ( map );
        }

        System.out.println ("zwracam liste ublionych obiektow");
        return jsonObject.toString ();
    }
    private Boolean checkFav(List<FavouriteObjects> userfav,Long sportobjectid){

        for (FavouriteObjects item:userfav ) {
            if(item.getSportobjectid ()==sportobjectid){
               return true;

            }
        }
      return false;
    }
    @PostMapping (value = "/deleteFromFavorites" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String deleteFromFavorites(Long sportobjectid){
        User user=userRepository.findByEmail ( userAuth () );
        List<FavouriteObjects> favouriteObjectsList=user.getFavouriteObjects ();
        long id=0;
        FavouriteObjects favouriteObjects=new FavouriteObjects ();
        for(FavouriteObjects item:favouriteObjectsList){
            if(item.getSportobjectid ()==sportobjectid){
                id=item.getId ();
                favouriteObjects=item;
                System.out.println (id );
                break;
            }

        }
        if(id!=0){

            user.getFavouriteObjects ().remove ( favouriteObjects );
            System.out.println (user.getFavouriteObjects ().size () );
            favouriteObjectsRepository.delete ( favouriteObjects );
           // favouriteObjectsRepository.delete ( favouriteObjectsRepository.getOne (id  ) );
            userRepository.save ( user );

            return new JSONObject (  ).put ( "message","SUCCESS" ).toString ();
        }else{
            return new JSONObject (  ).put ( "message","ERROR" ).toString ();
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
