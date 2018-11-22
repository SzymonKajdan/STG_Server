package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.PaymentHisotry;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.TimeTable;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.PaymentHistoryRepository;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.TimeTableReposiotry;
import com.shareThegame.STG.Repository.UserRepository;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.Valid;
import java.text.ParseException;

import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public
class TimeTableController {

    @Autowired
    TimeTableReposiotry timeTableReposiotry;
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;
    @PostMapping ( value = "/ReserveHall", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String reserve( @Valid TimeTable timeTable,String end, String start  ) throws ParseException {
        List<TimeTable> timeTableList=timeTableReposiotry.findAllBySportobjectid ( timeTable.getSportobjectid () );
        boolean isFree=true;
        DateTime postStartRent;
        DateTime postEndRent;

        int year;
        int month;
        int day;
        int hour;
        int minutes;
        int sec;
        //string z data z posta

        year=Integer.parseInt (  start.substring ( 0,start.length ()-17 ));
        month=Integer.parseInt (  start.substring ( 5,start.length ()-14 ));
        day=Integer.parseInt (  start.substring ( 8,start.length ()-11 ));
        hour=Integer.parseInt (  start.substring ( 11,start.length ()-8 ));
        minutes=Integer.parseInt (  start.substring ( 14,start.length ()-5 ));
        sec=Integer.parseInt (  start.substring ( 18,start.length ()-2 ));

        postStartRent=new DateTime ( year,month,day,hour,minutes,sec   );
        Date postStartRentDate= postStartRent.toDate ();





        //string z data z posta

        year=Integer.parseInt (  end.substring ( 0,end.length ()-17 ));
        month=Integer.parseInt (  end.substring ( 5,end.length ()-14 ));
        day=Integer.parseInt (  end.substring ( 8,end.length ()-11 ));
        hour=Integer.parseInt (  end.substring ( 11,end.length ()-8 ));
        minutes=Integer.parseInt (  end.substring ( 14,end.length ()-5 ));
        sec=Integer.parseInt (  end.substring ( 18,end.length ()-2 ));


        postEndRent=new DateTime ( year,month,day,hour,minutes,sec   );
        for( TimeTable item:timeTableList){

           // System.out.println (timeTableList.size () );
            //z bazy  start rent
            year = item.getStartrent ( ).getYear ( ) + 1900;
            month = item.getStartrent ( ).getMonth ( ) + 1;
            day = item.getStartrent ( ).getDate ( );
            hour = item.getStartrent ( ).getHours ( );
            minutes = item.getStartrent ( ).getMinutes ( );
            sec = item.getStartrent ( ).getSeconds ( );

           DateTime h2startrenttime=new DateTime ( year,month,day,hour,minutes,sec );


           //z bazy  end rent
           year=item.getEndrend ().getYear ()+1900;
           month=item.getEndrend ().getMonth ()+1;
           day=item.getEndrend ().getDate  ();
           hour=item.getEndrend ().getHours ();
           minutes=item.getEndrend ().getMinutes ();
           sec=item.getEndrend ().getSeconds ();
         //  item.getEndrend ().getTime ();
           DateTime h2endrenttime=new DateTime ( year,month,day,hour,minutes,sec );




          //  System.out.println ("baza"+item.getStartrent ().getTime ()+" post"+postStartRentDate.getTime ());
//||(timerequest.isAfter ( itemStart )&&timerequest.isAfter ( itemEnd ))
            if(item.getStartrent ().getTime ()==postStartRentDate.getTime ()||(postStartRentDate.getTime ()>=item.getStartrent ().getTime ()&&postStartRentDate.getTime ()<item.getEndrend ().getTime ())){

                isFree=false;
                break;
            }

        }
        JSONObject jsonObject = new JSONObject ( );
        if(isFree==false){
            jsonObject.put ( "status","hala zjeta w tym terminie" );
        }
        else{
            String username;
            try {
                org.springframework.security.core.userdetails.User currentUser =
                        ( org.springframework.security.core.userdetails.User ) SecurityContextHolder.getContext ( ).getAuthentication ( ).getPrincipal ( );
                username = currentUser.getUsername ( );
            } catch ( ClassCastException e ) {
                username = "anonymousUser";
                return "{}";
            }
            isFree=true;
            User user= userRepository.findByEmail ( username );
            SportObject sportObject=sportObjectReposiotry.getOne ( timeTable.getSportobjectid () );
           // System.out.println (sportObject.getAdress () );
            timeTable.setRenterid ( user.getId () );
            timeTable.setStartrent ( postStartRent.toDate () );
            timeTable.setEndrend ( postEndRent.toDate () );
            timeTableReposiotry.save ( timeTable );
            double  time=postEndRent.toDate ().getTime ()-postStartRent.toDate ().getTime ();
            time/=3600000;
            timeTable.setPrice (  (time*sportObject.getRentprice ()) );
            PaymentHisotry paymentHisotry=new PaymentHisotry ();
            paymentHisotry.setStartrent ( postStartRent.toDate () );
            paymentHisotry.setExprrent ( postEndRent.toDate () );
            paymentHisotry.setStautsofpayment ( false);
            paymentHisotry.setSportobjectid ( sportObject.getId () );
            paymentHisotry.setUserid ( user.getId () );
            paymentHisotry.setSportObject ( sportObject );
            paymentHisotry.setCost ( time*sportObject.getRentprice () );
            paymentHisotry.setUser ( user );
            paymentHistoryRepository.save ( paymentHisotry );
            user.getPaymentHisotries ().add ( paymentHisotry );
            sportObject.getPaymentHisotries ().add ( paymentHisotry );
            userRepository.save ( user );
            sportObjectReposiotry.save ( sportObject );


                  //  System.out.println (time );


            sportObject.getTimeTable ().add ( timeTable );
            sportObjectReposiotry.save ( sportObject );

            jsonObject.put ( "status","hala wolona w tym temrinie dokonano rezerwacji" );
        }
    return jsonObject.toString ();

    }
    @PostMapping ( value = "/ChangeReservation", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody String changeReservation(Long sportobjectid,String end, String start, String newstart,String newend) {
        boolean isFree = true;
        DateTime oldPostStartRent;
        DateTime oldPostEndRent;
        DateTime newPostStartRent;
        DateTime newPostEndRent;

        int year;
        int month;
        int day;
        int hour;
        int minutes;
        int sec;
        //string z data z posta

        year = Integer.parseInt ( start.substring ( 0 , start.length ( ) - 17 ) );
        month = Integer.parseInt ( start.substring ( 5 , start.length ( ) - 14 ) );
        day = Integer.parseInt ( start.substring ( 8 , start.length ( ) - 11 ) );
        hour = Integer.parseInt ( start.substring ( 11 , start.length ( ) - 8 ) );
        minutes = Integer.parseInt ( start.substring ( 14 , start.length ( ) - 5 ) );
        sec = Integer.parseInt ( start.substring ( 18 , start.length ( ) - 2 ) );

        oldPostStartRent = new DateTime ( year , month , day , hour , minutes , sec );
        Date oldPostStartRentDate = oldPostStartRent.toDate ( );


        year = Integer.parseInt ( end.substring ( 0 , end.length ( ) - 17 ) );
        month = Integer.parseInt ( end.substring ( 5 , end.length ( ) - 14 ) );
        day = Integer.parseInt ( end.substring ( 8 , end.length ( ) - 11 ) );
        hour = Integer.parseInt ( end.substring ( 11 , end.length ( ) - 8 ) );
        minutes = Integer.parseInt ( end.substring ( 14 , end.length ( ) - 5 ) );
        sec = Integer.parseInt ( end.substring ( 18 , end.length ( ) - 2 ) );

        oldPostEndRent = new DateTime ( year , month , day , hour , minutes , sec );
        Date oldPostEndRentDate = oldPostEndRent.toDate ( );

        year = Integer.parseInt ( newstart.substring ( 0 , newstart.length ( ) - 17 ) );
        month = Integer.parseInt ( newstart.substring ( 5 , newstart.length ( ) - 14 ) );
        day = Integer.parseInt ( newstart.substring ( 8 , newstart.length ( ) - 11 ) );
        hour = Integer.parseInt ( newstart.substring ( 11 , newstart.length ( ) - 8 ) );
        minutes = Integer.parseInt ( newstart.substring ( 14 , newstart.length ( ) - 5 ) );
        sec = Integer.parseInt ( newstart.substring ( 18 , newstart.length ( ) - 2 ) );

        newPostStartRent = new DateTime ( year , month , day , hour , minutes , sec );
        Date newPostStartRentDate = newPostStartRent.toDate ( );

        year = Integer.parseInt ( newend.substring ( 0 , newend.length ( ) - 17 ) );
        month = Integer.parseInt ( newend.substring ( 5 , newend.length ( ) - 14 ) );
        day = Integer.parseInt ( newend.substring ( 8 , newend.length ( ) - 11 ) );
        hour = Integer.parseInt ( newend.substring ( 11 , newend.length ( ) - 8 ) );
        minutes = Integer.parseInt ( newend.substring ( 14 , newend.length ( ) - 5 ) );
        sec = Integer.parseInt ( newend.substring ( 18 , newend.length ( ) - 2 ) );

        newPostEndRent = new DateTime ( year , month , day , hour , minutes , sec );
        Date newPostEndRentDate = newPostEndRent.toDate ( );

        List <TimeTable> timeTableList = timeTableReposiotry.findAllBySportobjectid ( sportobjectid );

        for (TimeTable item : timeTableList) {
            if ( item.getStartrent ( ).getTime ( ) == newPostEndRentDate.getTime ( ) || ( newPostEndRentDate.getTime ( ) >= item.getStartrent ( ).getTime ( ) && newPostStartRentDate.getTime ( ) < item.getEndrend ( ).getTime ( ) ) ) {
                isFree=false;
                break;
            }

        } JSONObject jsonObject = new JSONObject ( );
        if(isFree==false){
            jsonObject.put ( "status","hala zjeta w tym terminie" );
        }else{
            String username;
            try {
                org.springframework.security.core.userdetails.User currentUser =
                        ( org.springframework.security.core.userdetails.User ) SecurityContextHolder.getContext ( ).getAuthentication ( ).getPrincipal ( );
                username = currentUser.getUsername ( );
            } catch ( ClassCastException e ) {
                username = "anonymousUser";
                return "{}";
            }
            TimeTable timeTableToChange=timeTableReposiotry.findByStartrentAndEndrendAndSportobjectid ( oldPostStartRentDate,oldPostEndRentDate,sportobjectid );
            if(timeTableToChange!=null){

//iniit
                User user= userRepository.findByEmail ( username );
                SportObject sportObject=sportObjectReposiotry.getOne ( timeTableToChange.getSportobjectid () );
                PaymentHisotry paymentHisotry=paymentHistoryRepository.findBySportobjectidAndAndStartrentAndExprrent ( sportobjectid,oldPostStartRentDate,oldPostEndRentDate );
               // System.out.println (oldPostStartRentDate);


                //delete old reserv
                sportObject.getTimeTable ().remove ( timeTableToChange );
                sportObject.getPaymentHisotries ().remove ( paymentHisotry );
                user.getPaymentHisotries ().remove ( paymentHisotry );


                userRepository.save ( user );
                sportObjectReposiotry.save ( sportObject );

                paymentHistoryRepository.delete ( paymentHisotry );
                timeTableReposiotry.delete ( timeTableToChange );

                TimeTable timeTable1=new TimeTable ();
                timeTable1.setStartrent ( newPostStartRentDate );
                timeTable1.setEndrend ( newPostEndRentDate );
                double  time=newPostEndRent.toDate ().getTime ()-newPostStartRent.toDate ().getTime ();
                time/=3600000;
                timeTable1.setPrice ( time*sportObject.getRentprice () );
                timeTable1.setStartrent ( newPostStartRentDate );
                timeTable1.setEndrend ( newPostEndRentDate );
                PaymentHisotry paymentHisotry1=new PaymentHisotry ();
                paymentHisotry1.setStartrent ( newPostStartRentDate );
                paymentHisotry1.setExprrent ( newPostEndRentDate );
                paymentHisotry1.setStautsofpayment ( false);
                paymentHisotry1.setSportobjectid ( sportObject.getId () );
                paymentHisotry1.setUserid ( user.getId () );
                paymentHisotry1.setSportObject ( sportObject );
                paymentHisotry1.setCost ( time*sportObject.getRentprice () );
                paymentHisotry1.setUser ( user );

                paymentHistoryRepository.save ( paymentHisotry1 );



                System.out.println (paymentHisotry.getId () );
                user.getPaymentHisotries ().add ( paymentHisotry1 );
                sportObject.getPaymentHisotries ().add ( paymentHisotry1 );

                userRepository.save ( user );
                timeTableReposiotry.save ( timeTable1 );
              sportObjectReposiotry.save ( sportObject );


                //  System.out.println (time );


               sportObject.getTimeTable ().add ( timeTable1 );
                System.out.println ("id "+timeTable1.getId () );
               //sportObjectReposiotry.save ( sportObject );


                jsonObject.put ( "xd","Xd" );

            }
            else{
                jsonObject.put ( "status","blad" );

            }

        }
        return jsonObject.toString ();
    }
}
