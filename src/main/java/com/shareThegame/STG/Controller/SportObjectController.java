package com.shareThegame.STG.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.shareThegame.STG.Model.*;
import com.shareThegame.STG.Repository.*;
import com.shareThegame.STG.Wrapper.PhotoWrapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;
    @Autowired
    ObjectPhotosRepository objectPhotosRepository;
    @Autowired
    ObjectStarsRepository objectStarsRepository;
    @Autowired
    ObjectExstrasRepository objectExstrasRepository;
    @Autowired
    OpenHoursRepository openHoursRepository;

    @PostMapping ( value = "/addNewObject", produces = "application/json",consumes="application/json" )
    public @ResponseBody
    String addNewObject ( @RequestBody String  object){
        String useremail=userAuth ();
        User user=userRepository.findByEmail ( useremail );

        JSONObject json =new JSONObject ( object );
        JSONObject sportobject=json.getJSONObject ( "sportObject" );
        JSONObject objectextras=json.getJSONObject ( "objectExstras" );
        JSONObject openhours=json.getJSONObject ( "openHours" );
        JSONArray photos=json.getJSONArray ( "photos" );
        JSONObject jsonObject=new JSONObject (  );

        ObjectExtras objectExtras=parseObjectExtras(objectextras);
        OpenHours openHours=parseOpenHours(openhours);
        if(user!=null) {


            List <ObjectPhotos> objectPhotosList = parseObjectPhotos ( photos );

            objectExstrasRepository.save ( objectExtras );
            openHoursRepository.save ( openHours );
            objectPhotosRepository.saveAll ( objectPhotosList );


            SportObject sportObject = parseSportObject ( sportobject );
            sportObject.setOwnid ( user.getId ( ) );
            sportObject.setObjectExtras ( objectExtras );
            sportObject.setOpenHours ( openHours );
            sportObject.setObjectPhotos ( objectPhotosList );
            sportObjectReposiotry.save ( sportObject );

            objectExtras.setSportobjectid ( sportObject.getId ( ) );
            openHours.setSportobjectid ( sportObject.getId ( ) );

            for (ObjectPhotos it : objectPhotosList) {
                it.setSportobjectid ( sportObject.getId ( ) );
            }

            objectExstrasRepository.save ( objectExtras );
            openHoursRepository.save ( openHours );
            objectPhotosRepository.saveAll ( objectPhotosList );


            jsonObject.put ( "message" , "SUCCESS" );
            jsonObject.put ( "id",sportObject.getId () );
        }
        else{

            jsonObject.put ( "message" , "FAILED " );
        }
        return jsonObject.toString ();
    }


    @PostMapping(value = "/getAllObjects", produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String getAllObjects(){
        JSONArray jsonObject=new JSONArray (  );
        List<SportObject> allSportObjectList=sportObjectReposiotry.findAll ();
        System.out.println ("Request o  wszytskie obiekty");
        for(SportObject item:allSportObjectList){
            Map<String,Object> sportObjectMap = new HashMap<>();
            List<ObjectStars>objectStarsList= objectStarsRepository.findAllBySportobjectid ( item.getId () );
            sportObjectMap.put ( "globalID",item.getId () );
            sportObjectMap.put ( "city",item.getCity () );
            sportObjectMap.put ( "street",item.getStreet () );
            sportObjectMap.put ( "localno" ,item.getLocalno ());
            sportObjectMap.put ( "ppmail",item.getPpmail () );
            sportObjectMap.put ( "active",item.getActive () );
            sportObjectMap.put ( "rentprice",item.getRentprice () );
            sportObjectMap.put ( "ownid",item.getOwnid () );
            sportObjectMap.put ( "name",item.getName () );
            sportObjectMap.put ( "futsal",item.isFutsal () );
            sportObjectMap.put ( "volleyball",item.isVolleyball () );
            sportObjectMap.put ( "soccer",item.isSoccer () );
            sportObjectMap.put ( "tennis",item.isTennis () );
            sportObjectMap.put ( "squash",item.isSquash () );
            sportObjectMap.put ( "handball",item.isHandball () );
            sportObjectMap.put ( "basketball",item.isBasketball () );
            sportObjectMap.put ( "badminton",item.isBadminton () );
            sportObjectMap.put ( "zipcode",item.getZipcode () );
            sportObjectMap.put ( "zipcodecity",item.getZipcodecity () );
            if(item.getSiteaddress ()!=null) {
                sportObjectMap.put ( "siteaddress" , item.getSiteaddress ( ) );
            }
            else{
                sportObjectMap.put ( "siteaddress" , " " );
            }
            if(item.getEmail ()!=null) {
                sportObjectMap.put ( "email" , item.getEmail ( ) );
            }else{
                sportObjectMap.put ( "email" , " " );
            }
            if(item.getPhoneno ()!=null) {
                sportObjectMap.put ( "phoneno" , item.getPhoneno ( ) );
            }
            else{
                sportObjectMap.put ( "phoneno" , " " );
            }
            sportObjectMap.put ( "latitude",item.getLatitude () );
            sportObjectMap.put ( "longitude",item.getLongitude () );
            sportObjectMap.put ( "close",item.getClose () );
            sportObjectMap.put ( "open",item.getOpen () );
            sportObjectMap.put ( "float",returnStarsValue ( objectStarsList ) );
            sportObjectMap.put ( "quantityRate",objectStarsList.size () );
            sportObjectMap.put ( "photos",item.getObjectPhotos () );
            sportObjectMap.put ( "objectExtras",item.getObjectExtras ());
            sportObjectMap.put ( "openHours",item.getOpenHours () );
            JSONArray tmp=new JSONArray ( );
            tmp.put (sportObjectMap );
            jsonObject.put (  sportObjectMap);


        }
        System.out.println (jsonObject.toString () );
    return jsonObject.toString ();
    }
    int boolToInt(boolean b) {
        return Boolean.compare(b, false);
    }
    @PostMapping(value = "/addObject" ,produces = "application/json")
    public @ResponseBody
    String addObject( @Valid SportObject sportObject,@Valid ObjectExtras objectExtras,@Valid OpenHours openHours ){
        String useremial;

        useremial=userAuth ();
        User  user= userRepository.findByEmail ( useremial );
        sportObject.setActive ( 0 );
        JSONObject jsonObject = new JSONObject ( );

        if(user!=null) {





            objectExstrasRepository.save ( objectExtras );
            openHoursRepository.save ( openHours );

            sportObject.setObjectPhotos ( new ArrayList <> (  ) );
            sportObject.setTimeTable ( new ArrayList <> (  ) );
            sportObject.setPaymentHisotries ( new ArrayList <> (  ) );
            sportObject.setObjectStars ( new ArrayList <> (  ) );

            sportObject.setOwnid ( user.getId () );
            sportObject.setActive ( 1 );
            sportObject.setUser ( user );

            sportObject.setObjectExtras ( objectExtras );
            sportObject.setOpenHours (  openHours );
            sportObject.setOwnid ( user.getId ( ) );




            user.getSportObjects ().add ( sportObject );




            sportObjectReposiotry.save ( sportObject );



            objectExtras.setSportobjectid ( sportObject.getId () );
            objectExstrasRepository.save ( objectExtras );
            openHours.setSportobjectid ( sportObject.getId () );
            openHoursRepository.save ( openHours );

            //System.out.println (sportObject.getId () );
            System.out.println (sportObject.getObjectExtras ().getSportobjectid () );
            userRepository.save ( user );



            jsonObject.put ( "message" , "SUCCESS" );
            jsonObject.put ( "id",sportObject.getId () );
        }
        else{

            jsonObject.put ( "message" , "FAILED " );
        }
        return  jsonObject.toString ();


    }
    @PostMapping(value = "/deleteObject" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public @ResponseBody
    String deleteObject( @Valid SportObject sportObject ){
        String useremial=userAuth ();
        User user=userRepository.findByEmail ( useremial );
        SportObject sportObjectToDelete = sportObjectReposiotry.getOne ( sportObject.getId ( ) );
        if(sportObject.getOwnid ()==user.getId ()&&sportObjectToDelete!=null) {

            List<PaymentHisotry> paymentHisotryList = paymentHistoryRepository.findAllBySportobjectid    ( sportObject.getId ( ) );
            List<ObjectPhotos> objectPhotosToDelete=objectPhotosRepository.findAllBySportobjectid ( sportObject.getId () );
            List<ObjectStars>objectStarsListToDelete=objectStarsRepository.findAllBySportobjectid ( sportObject.getId () );
            ObjectExtras objectExtrasToDelete= objectExstrasRepository.findBySportobjectid ( sportObject.getId () );
            List<TimeTable>timeTableListToDelete=timeTableReposiotry.findAllBySportobjectid ( sportObject.getId () );
            VisibilityObject visibilityObjectToDelete=visvilityObjectRepository.findBySportobjectid ( sportObject.getId () );
            OpenHours openHoursToDelete=openHoursRepository.findBySportobjectid ( sportObject.getId () );

            if(paymentHisotryList.size ()!=0){
            sportObjectToDelete.getPaymentHisotries ().removeAll ( paymentHisotryList );

            for(PaymentHisotry p:paymentHisotryList){
                User tmpuser=userRepository.getOne ( p.getUserid () );
                tmpuser.getPaymentHisotries ().removeAll ( paymentHistoryRepository.findAllBySportobjectid ( sportObject.getId () ) );
            }
           paymentHistoryRepository.deleteAll (paymentHisotryList);

            }
            if(objectPhotosToDelete.size ()!=0){
                sportObjectToDelete.getObjectPhotos ().removeAll ( objectPhotosToDelete );
                objectPhotosRepository.deleteAll (objectPhotosToDelete);
            }
            if(objectStarsListToDelete.size ()!=0){
                sportObjectToDelete.getObjectStars ().removeAll ( objectStarsListToDelete );
                objectStarsRepository.deleteAll (objectStarsListToDelete);
            }
            if(objectExtrasToDelete!=null){
                sportObjectToDelete.setObjectExtras (null);
                objectExstrasRepository.delete ( objectExtrasToDelete );
            }
            if(timeTableListToDelete.size ()!=0){
                sportObjectToDelete.getTimeTable ().removeAll ( timeTableListToDelete );
                timeTableReposiotry.deleteAll (timeTableListToDelete);
            }
            if(visibilityObjectToDelete!=null){
                sportObjectToDelete.setVisibilityObject (null);
                visvilityObjectRepository.delete ( visibilityObjectToDelete );

            }
            if(openHoursToDelete!=null){
                sportObjectToDelete.setOpenHours ( null );
                openHoursRepository.delete ( openHoursToDelete );

            }

            user.getSportObjects ().remove (  sportObjectToDelete );

           // sportObject.setUser (null);
           // userRepository.save ( user );
            sportObjectReposiotry.delete (  sportObjectToDelete );


        return  new JSONObject ( ).put ( "xd","Xd" ).toString ();
        }
        else {
             return new JSONObject (  ).put ( "message","Error" ).toString () ;
            }

    }
    @PostMapping(value = "/updateObject" ,produces = "application/json",consumes = "application/json")
    public @ResponseBody
    String updateObject(@RequestBody String  object){
        String usermail=userAuth ();
        User user=userRepository.findByEmail ( usermail );

        JSONObject json =new JSONObject ( object );
        JSONObject sportobject=json.getJSONObject ( "sportObject" );
        JSONObject objectextras=json.getJSONObject ( "objectExstras" );
        JSONObject openhours=json.getJSONObject ( "openHours" );
        JSONArray photos=json.getJSONArray ( "photos" );

        SportObject sportObject=parseSportObject ( sportobject );
        ObjectExtras objectExtras=parseObjectExtras ( objectextras );
        OpenHours openHours=parseOpenHours ( openhours );
        List <ObjectPhotos> objectPhotosList = parseObjectPhotos ( photos );

        SportObject toUpdate=sportObjectReposiotry.getOne ( sportObject.getId () );
      if(user.getId ()==toUpdate.getOwnid ()&&toUpdate!=null) {
          List<ObjectPhotos>objectPhotosToDelete=objectPhotosRepository.findAllBySportobjectid ( sportObject.getId () );
          if(objectPhotosToDelete.size ()!=0){
              toUpdate.getObjectPhotos ().removeAll ( objectPhotosToDelete );
              objectPhotosRepository.deleteAll (objectPhotosToDelete);
          }

          OpenHours openHoursToUpdate=openHoursRepository.findBySportobjectid ( sportObject.getId () );
          upOpenHoursOfObject ( openHoursToUpdate,openHours );


          openHoursToUpdate.setSportobjectid ( sportObject.getId () );
          openHoursRepository.save ( openHoursToUpdate );


          ObjectExtras objectExtrasToUpdate=objectExstrasRepository.findBySportobjectid ( sportObject.getId () );
          upExtsrasOfSportObject ( objectExtrasToUpdate,objectExtras );
          objectExtrasToUpdate.setSportobjectid ( sportObject.getId () );
          objectExstrasRepository.save ( objectExtrasToUpdate );


          List<ObjectPhotos> photosToUpdate=objectPhotosRepository.findAllBySportobjectid ( sportObject.getId () );
          upObjectPhostos ( photosToUpdate,objectPhotosList );
          System.out.println (objectPhotosList.size () );
          for(ObjectPhotos it:photosToUpdate){
              it.setSportobjectid ( sportObject.getId () );
          }
          objectPhotosRepository.saveAll ( objectPhotosList );

        upSportObject ( toUpdate,sportObject,openHoursToUpdate,objectExtrasToUpdate,photosToUpdate );



          //System.out.println (openHours.getSportobjectid () );

          sportObjectReposiotry.save ( toUpdate );
          return new JSONObject (  ).put ( "stats","updated" ).toString ();
      }else{
          return new JSONObject (  ).put ( "stats","error" ).toString ();
      }

    }

    private  void upObjectPhostos(List<ObjectPhotos> photosListToUpdate,List<ObjectPhotos> objectPhotos){
        photosListToUpdate.clear ();

            photosListToUpdate.addAll ( objectPhotos );

    }

    private
    void upSportObject(SportObject toUpdate, SportObject sportObject,OpenHours openHours,ObjectExtras objectExtras,List<ObjectPhotos> objectPhotos){
        toUpdate.setOpenHours ( openHours );
        toUpdate.setObjectExtras ( objectExtras );
        toUpdate.setEmail ( sportObject.getEmail () );
        toUpdate.setZipcodecity ( sportObject.getZipcodecity () );
        toUpdate.setZipcode ( sportObject.getZipcode () );
        toUpdate.setLongitude ( sportObject.getLongitude () );
        toUpdate.setLatitude ( sportObject.getLatitude () );
        toUpdate.setPhoneno (sportObject.getPhoneno ());
        toUpdate.setStreet ( sportObject.getStreet () );
        toUpdate.setCity ( sportObject.getCity () );
        toUpdate.setLocalno ( sportObject.getLocalno () );
        toUpdate.setPpmail (sportObject.getPpmail ());
        toUpdate.setActive ( 1 );
        toUpdate.setRentprice ( sportObject.getRentprice () );
        toUpdate.setName ( sportObject.getName () );
        toUpdate.setFutsal ( sportObject.isFutsal () );
        toUpdate.setBasketball ( sportObject.isBasketball () );

        toUpdate.setVolleyball ( sportObject.isVolleyball () );
        toUpdate.setSoccer ( sportObject.isSoccer () );
        toUpdate.setTennis ( sportObject.isTennis () );
        toUpdate.setSquash ( sportObject.isSquash () );
        toUpdate.setHandball ( sportObject.isHandball () );
        toUpdate.setBadminton ( sportObject.isBadminton () );
        toUpdate.setSiteaddress ( sportObject.getSiteaddress () );
        toUpdate.setEmail ( sportObject.getEmail () );
        toUpdate.setObjectPhotos ( objectPhotos );

    }

    private void upOpenHoursOfObject(OpenHours openHoursToUpdate,OpenHours openHours){
        openHoursToUpdate.setMondayHours ( openHours.getMondayHours () );
        openHoursToUpdate.setTusedayHours ( openHours.getTusedayHours () );
        openHoursToUpdate.setThrusdayHours ( openHours.getThrusdayHours () );
        openHoursToUpdate.setWensdayHours ( openHours.getWensdayHours () );
        openHoursToUpdate.setFridayHours ( openHours.getFridayHours () );
        openHoursToUpdate.setSaturdayHours ( openHours.getSaturdayHours () );
        openHoursToUpdate.setSundayHours ( openHours.getSundayHours () );

        System.out.println (openHours.isOpenInBankHolidays () );
        openHoursToUpdate.setOpenInBankHolidays (  openHours.isOpenInBankHolidays ());
    }

    private  void upExtsrasOfSportObject(ObjectExtras objectExtrasToUpdate,ObjectExtras objectExtras){
        objectExtrasToUpdate.setEquipment ( objectExtras.isEquipment () );
        objectExtrasToUpdate.setLockerroom ( objectExtras.isLockerroom () );
        objectExtrasToUpdate.setBathroom ( objectExtras.isBathroom () );
        objectExtrasToUpdate.setArtificiallighting ( objectExtras.isArtificiallighting () );
        objectExtrasToUpdate.setParking ( objectExtras.isParking () );
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
    private double returnStarsValue(List<ObjectStars>objectStarsList) {
        double value = 0;
        for (ObjectStars item : objectStarsList) {
            value += item.getValue ( );

        }
        if ( objectStarsList.size ( ) != 0 ) {
            return ( double ) value / ( double ) objectStarsList.size ( );
        }
        else
        {
            return  0;
        }
    }

    private SportObject parseSportObject(JSONObject  sportObject){

        Gson g = new Gson();
        SportObject p = g.fromJson(sportObject.toString (), SportObject.class);
        return  p;
    }

    private ObjectExtras parseObjectExtras(JSONObject objectExstras){
        Gson g=new Gson ();
        ObjectExtras obj=g.fromJson (objectExstras.toString (),ObjectExtras.class  );
        return obj;
    }
    private
    OpenHours parseOpenHours ( JSONObject openhours ) {
        Gson g=new Gson ();
        OpenHours obj=g.fromJson (openhours.toString (),OpenHours.class  );
        return obj;
    }
    private
    List<ObjectPhotos> parseObjectPhotos ( JSONArray photos ) {
        PhotoWrapper photoWrapper=new PhotoWrapper ();
        List <ObjectPhotos> list = new ArrayList <> ( );
        for (int i=0 ;i<photos.length ();i++){

           // System.out.println (photo.get ( i ) );
          ObjectPhotos objectPhotos=new ObjectPhotos ();
          String tmp= ( String ) photos.get ( i );
          objectPhotos.setPhoto ( tmp);
            list.add ( objectPhotos );

        }



        return list;
    }

}
