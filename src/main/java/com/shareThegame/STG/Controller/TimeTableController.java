package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.*;
import com.shareThegame.STG.Repository.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.jws.soap.SOAPBinding;
import javax.validation.Valid;
import java.text.ParseException;

import java.time.format.DateTimeFormatter;
import java.util.*;

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
    @Autowired
    OpenHoursRepository openHoursRepository;




    @PostMapping ( value = "/ReserveHall", produces = "application/json")
    public @ResponseBody
    String reserve ( @RequestBody String jsonData ) throws ParseException {

        JSONObject jsonObject = new JSONObject ( );

        String username = userAuth ( );

        User user = userRepository.findByEmail ( username );
        DateTime postStartRent;
        DateTime postEndRent;

        Long sportobjectid=Long.valueOf (  new JSONObject ( jsonData ).get ( "sportobjectid" ).toString ());
        boolean isFree = true;



        postStartRent = pasreDate ( new JSONObject ( jsonData ).get ( "start" ).toString () );
        Date postStartRentDate = postStartRent.toDate ( );


        postEndRent = pasreDate ( new JSONObject ( jsonData ).get ( "end" ).toString () );
        Date postEndRentDate = postEndRent.toDate ( );


        DateTime dt = new DateTime().withYear ( postEndRent.getYear () ).withDayOfMonth ( postEndRent.getDayOfMonth () ).withMonthOfYear ( postEndRent.getMonthOfYear () )
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59).withMillisOfSecond ( 59 );
        DateTime dt1 = new DateTime().withYear ( postEndRent.getYear () ).withDayOfMonth ( postEndRent.getDayOfMonth () ).withMonthOfYear ( postEndRent.getMonthOfYear () )
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0).withMillisOfSecond ( 0 );
        List <TimeTable> timeTableList = timeTableReposiotry.findAllBySportobjectidAndStartrentAfterAndEndrendBefore (sportobjectid,dt1.toDate (),dt.toDate () );
        TimeTable timeTableCheck=timeTableReposiotry.findByRenteridAndStartrentAndEndrend ( user.getId (),postStartRentDate,postEndRentDate );
        if(timeTableCheck!=null&&timeTableCheck.getRenterid ()==user.getId ()){
            PaymentHisotry paymentHisotry=paymentHistoryRepository.findBySportobjectidAndAndStartrentAndExprrent ( timeTableCheck.getSportobjectid (),postStartRentDate,postEndRentDate );
            jsonObject.put ( "payment_id",paymentHisotry.getId () );
              jsonObject.put ( "status","lets_pay" );
              return  jsonObject.toString ();
        }
        Collections.sort ( timeTableList );
        boolean iscorrect = checkCorrectOfRequest ( sportobjectid , postStartRent , postEndRent );
        if ( iscorrect == true ) {

            if ( timeTableList.size ( ) > 0 ) {

                isFree = chceckAvalabile ( timeTableList , postStartRentDate , postEndRentDate );

            }
            else {
                isFree = true;
            }


//



            if ( isFree == false ) {
                jsonObject.put ( "status" , "SPORTOBJECT_IS_NOT_AVALAVILE" );
            }
            else {


                isFree = true;



                SportObject sportObject = sportObjectReposiotry.getOne ( sportobjectid);
              //  boolean status=statusOfPayment;

                TimeTable timeTable=new TimeTable ();
                timeTable.setSportobjectid ( sportobjectid );


                //set timetable
                timeTable.setRenterid ( user.getId ( ) );
                timeTable.setStartrent ( postStartRent.toDate ( ) );
                timeTable.setEndrend ( postEndRent.toDate ( ) );
                double time = postEndRent.toDate ( ).getTime ( ) - postStartRent.toDate ( ).getTime ( );
                time /= 3600000;
                timeTable.setPrice ( ( time * sportObject.getRentprice ( ) ) );
                timeTableReposiotry.save ( timeTable );

                PaymentHisotry paymentHisotry = new PaymentHisotry ( );
                paymentHisotry.setStartrent ( postStartRent.toDate ( ) );
                paymentHisotry.setExprrent ( postEndRent.toDate ( ) );
                paymentHisotry.setStautsofpayment ( false );
                paymentHisotry.setSportobjectid ( sportObject.getId ( ) );
                paymentHisotry.setUserid ( user.getId ( ) );
                paymentHisotry.setSportObject ( sportObject );
                paymentHisotry.setCost ( time * sportObject.getRentprice ( ) );
                paymentHisotry.setUser ( user );
                paymentHistoryRepository.save ( paymentHisotry );


                user.getPaymentHisotries ( ).add ( paymentHisotry );
                sportObject.getPaymentHisotries ( ).add ( paymentHisotry );
                userRepository.save ( user );
                sportObjectReposiotry.save ( sportObject );


                sportObject.getTimeTable ( ).add ( timeTable );
                sportObjectReposiotry.save ( sportObject );

                jsonObject.put ( "status" , "RESERVATION_CONFRIMED" );
                jsonObject.put ( "payment_id",paymentHisotry.getId () );
                jsonObject.put ( "id",timeTable.getId () ) ;
            }
        }
        else {
            jsonObject.put ( "status","REQUEST_FAILED" );
        }
        return jsonObject.toString ( );

    }

    @GetMapping ( value = "/returnTimetableOfSportObject", produces = "application/json" )
    public @ResponseBody
    String returnTimetableOfSportObject ( @RequestBody String  jsonData ) {



        DateTime start = pasreDate ( new JSONObject (  jsonData ).get ( "start" ).toString () );
        DateTime end = pasreDate ( new JSONObject ( jsonData ).get ( "end" ).toString () );
        Long sportobjectid=Long.valueOf ( new JSONObject ( jsonData ).get ( "sportobjectid" ).toString () );

        System.out.println ( "reguset o termianrz" );
        List <TimeTable> timeTableList = timeTableReposiotry.findAllBySportobjectidAndStartrentAfterAndEndrendBefore ( sportobjectid , start.toDate ( ) , end.toDate ( ) );
        SportObject sp = sportObjectReposiotry.getOne ( sportobjectid );
        OpenHours openHours = openHoursRepository.findBySportobjectid ( sportobjectid );

        List <String> objectOpenAndClose = new ArrayList <> ( );
        //  System.out.println (start.getDayOfWeek () );
        objectOpenAndClose = chcekDay ( start , openHours );
        // System.out.println (objectOpenAndClose.get ( 0 )+ "   "+objectOpenAndClose.get ( 1 ) );


        int size = calc ( objectOpenAndClose.get ( 0 ) , objectOpenAndClose.get ( 1 ) );
        //System.out.println (size );
        //System.out.println (size );
        String tmp = new String ( );
        for (int i = 0; i < size; i++) {
            tmp += "0";
        }
        StringBuilder timetabletoreturn = new StringBuilder ( tmp );
        sp.getOpen ( );
        Date localopen = sp.getDate ( objectOpenAndClose.get ( 0 ) );
        //   System.out.println (localopen );

        for (TimeTable item : timeTableList) {
            int first = getHourCount ( localopen , item.getStartrent ( ) );
            int last = getHourCount ( localopen , item.getEndrend ( ) ) - 1;
            PaymentHisotry paymentHisotry = paymentHistoryRepository.findBySportobjectidAndAndStartrentAndExprrent ( sportobjectid , item.getStartrent ( ) , item.getEndrend ( ) );
            if ( paymentHisotry.isStautsofpayment ( ) == true ) {

                for (int i = first; i <= last; i++) {

                    timetabletoreturn.setCharAt ( i , '1' );
                }
            }
            else {

                for (int i = first; i <= last; i++) {

                    timetabletoreturn.setCharAt ( i , '4' );
                }
            }
        }


        JSONObject jsonObject = new JSONObject ( );
        System.out.println ( jsonObject.toString ( ) );
        jsonObject.put ( "schedule" , timetabletoreturn );
        return jsonObject.toString ( );
    }
    /*
    @PostMapping ( value = "/ChangeReservation", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public @ResponseBody
    String changeReservation ( Long sportobjectid , String end , String start , String newstart , String newend ) {
        JSONObject jsonObject = new JSONObject ( );
        boolean isFree = true;
        DateTime oldPostStartRent;
        DateTime oldPostEndRent;



        oldPostStartRent = pasreDate ( start );
        Date oldPostStartRentDate = oldPostStartRent.toDate ( );

        oldPostEndRent = pasreDate ( end );
        Date oldPostEndRentDate = oldPostEndRent.toDate ( );

        DateTime newPostStartRent = pasreDate ( newstart );
        Date newPostStartRentDate = newPostStartRent.toDate ( );

        DateTime newPostEndRent = pasreDate ( newend );
        Date newPostEndRentDate = newPostEndRent.toDate ( );

        DateTime dt = new DateTime().withYear ( newPostStartRent.getYear () ).withDayOfMonth ( newPostStartRent.getDayOfMonth () ).withMonthOfYear ( newPostStartRent.getMonthOfYear () )
                .withHourOfDay(23)
                .withMinuteOfHour(59)
                .withSecondOfMinute(59).withMillisOfSecond ( 59 );

        DateTime dt1 = new DateTime().withYear ( newPostEndRent.getYear () ).withDayOfMonth ( newPostEndRent.getDayOfMonth () ).withMonthOfYear ( newPostEndRent.getMonthOfYear () )
                .withHourOfDay(0)
                .withMinuteOfHour(0)
                .withSecondOfMinute(0).withMillisOfSecond ( 0 );
        boolean iscorrect = checkCorrectOfRequest ( sportobjectid,newPostStartRent,newPostEndRent );
        if ( iscorrect == true ) {
            List <TimeTable> timeTableList = timeTableReposiotry.findAllBySportobjectidAndStartrentAfterAndEndrendBefore ( sportobjectid , dt.toDate ( ) , dt1.toDate ( ) );
            Collections.sort ( timeTableList );
            if ( timeTableList.size ( ) > 0 ) {
                isFree = chceckAvalabile ( timeTableList , newPostStartRentDate , newPostEndRentDate );

            }
            else {
                isFree = true;
            }



            if ( isFree == false ) {
                jsonObject.put ( "status" , "hala zjeta w tym terminie" );
            }
            else {

                TimeTable timeTableToChange = timeTableReposiotry.findByStartrentAndEndrendAndSportobjectid ( oldPostStartRentDate , oldPostEndRentDate , sportobjectid );
                if ( timeTableToChange != null ) {

                    String username = userAuth ( );
                    User user = userRepository.findByEmail ( username );
                    SportObject sportObject = sportObjectReposiotry.getOne ( timeTableToChange.getSportobjectid ( ) );
                    PaymentHisotry paymentHisotry = paymentHistoryRepository.findBySportobjectidAndAndStartrentAndExprrent ( sportobjectid , oldPostStartRentDate , oldPostEndRentDate );
                    // System.out.println (oldPostStartRentDate);

                    //delete old reserv
                    deleteReserv ( timeTableToChange , user );
                    TimeTable timeTable1 = new TimeTable ( );
                    timeTable1.setStartrent ( newPostStartRentDate );
                    timeTable1.setEndrend ( newPostEndRentDate );
                    timeTable1.setSportobjectid ( sportobjectid );
                    timeTable1.setRenterid ( user.getId ( ) );

                    double time = newPostEndRent.toDate ( ).getTime ( ) - newPostStartRent.toDate ( ).getTime ( );
                    time /= 3600000;
                    timeTable1.setPrice ( time * sportObject.getRentprice ( ) );
                    timeTable1.setStartrent ( newPostStartRentDate );
                    timeTable1.setEndrend ( newPostEndRentDate );
                    PaymentHisotry paymentHisotry1 = new PaymentHisotry ( );
                    paymentHisotry1.setStartrent ( newPostStartRentDate );
                    paymentHisotry1.setExprrent ( newPostEndRentDate );
                    paymentHisotry1.setStautsofpayment ( false );
                    paymentHisotry1.setSportobjectid ( sportObject.getId ( ) );
                    paymentHisotry1.setUserid ( user.getId ( ) );
                    paymentHisotry1.setSportObject ( sportObject );
                    paymentHisotry1.setCost ( time * sportObject.getRentprice ( ) );
                    paymentHisotry1.setUser ( user );
                    paymentHisotry1.setUserid ( user.getId ( ) );
                    paymentHisotry1.setSportobjectid ( sportobjectid );

                    paymentHistoryRepository.save ( paymentHisotry1 );


                    user.getPaymentHisotries ( ).add ( paymentHisotry1 );
                    sportObject.getPaymentHisotries ( ).add ( paymentHisotry1 );

                    userRepository.save ( user );
                    timeTableReposiotry.save ( timeTable1 );
                    sportObjectReposiotry.save ( sportObject );


                    sportObject.getTimeTable ( ).add ( timeTable1 );
                    //System.out.println ( "id " + timeTable1.getId ( ) );
                    sportObjectReposiotry.save ( sportObject );


                    jsonObject.put ( "status" , "udana zmiana rezerwacji " );

                }
                else {
                    jsonObject.put ( "status" , "blad" );

                }

            }
        }else{
            jsonObject.put ( "status","error" );
        }
        return jsonObject.toString ( );
    }
*/
    @PostMapping ( value = "/DeleteReservation", produces = "application/json" )
    public @ResponseBody
    String deleteReservation ( @RequestBody String jsonData ) {
        String useremial = userAuth ( );
        User user = userRepository.findByEmail ( useremial );
        Long sportobjectid=Long.valueOf ( new JSONObject ( jsonData ).get ( "sportobjectid" ).toString () );
        Long id=Long.valueOf ( new JSONObject ( jsonData ).get ( "id" ).toString () );
        TimeTable timeTableToDelete = timeTableReposiotry.findBySportobjectidAndId ( sportobjectid,id );
        if ( timeTableToDelete != null ) {


                if ( user.getId ( ) != timeTableToDelete.getRenterid ( ) ) {

                    return new JSONObject ( ).put ( "message" , "SERVER_ERROR" ).toString ( );
                }
                else {
                    deleteReserv ( timeTableToDelete , user );
                    return new JSONObject ( ).put ( "message" , "Delete_Success" ).toString ( );

                }





        }
        else {
            return new JSONObject ( ).put ( "message" , "Erorr_Check_The_Correctness" ).toString ( );

        }


    }

    private
    void deleteReserv ( TimeTable timeTableToDelete , User user ) {

        SportObject sportObject = sportObjectReposiotry.getOne ( timeTableToDelete.getSportobjectid ( ) );
        PaymentHisotry paymentHisotry = paymentHistoryRepository.findBySportobjectidAndAndStartrentAndExprrent ( timeTableToDelete.getSportobjectid ( ) , timeTableToDelete.getStartrent ( ) , timeTableToDelete.getEndrend ( ) );
        // System.out.println (oldPostStartRentDate);

        //delete old reserv
        sportObject.getTimeTable ( ).remove ( timeTableToDelete );
        sportObject.getPaymentHisotries ( ).remove ( paymentHisotry );
        user.getPaymentHisotries ( ).remove ( paymentHisotry );


        userRepository.save ( user );
        sportObjectReposiotry.save ( sportObject );

        paymentHistoryRepository.delete ( paymentHisotry );
        timeTableReposiotry.delete ( timeTableToDelete );

    }

    private
    DateTime pasreDate ( String date ) {
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
    String userAuth ( ) {

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

    private
    Boolean chceckAvalabile ( List <TimeTable> timeTableList , Date postStartRentDate , Date postEndRentDate ) {
        boolean isFree = true;
        System.out.println ("rozmair "+timeTableList.size ());


            for (int i = 0; i < timeTableList.size ( ) ; i++) {
               System.out.println ( postStartRentDate.toString ()+" "+ postEndRentDate.toString ()+" Baza"+timeTableList.get ( i ).getStartrent ().toString ()+" "+ timeTableList.get ( i ).getEndrend ().toString ());
               if(postStartRentDate.getTime ()<timeTableList.get ( i ).getStartrent ().getTime ()&& postEndRentDate.getTime ()<=timeTableList.get ( i ).getEndrend ().getTime ()&& postEndRentDate.getTime ( )>timeTableList.get ( i ).getStartrent ().getTime ()){
                   System.out.println ("1 " +postStartRentDate.toString ()+" "+ postEndRentDate.toString ()+" Baza"+timeTableList.get ( i ).getStartrent ().toString ()+" "+ timeTableList.get ( i ).getEndrend ().toString ());

                    isFree = false;
                    break;
                }
                if(postStartRentDate.getTime ()>=timeTableList.get ( i ).getStartrent ().getTime ()&& postEndRentDate.getTime ()<=timeTableList.get ( i ).getEndrend ().getTime ()){
                    System.out.println ("2 " +postStartRentDate.toString ()+" "+ postEndRentDate.toString ()+" Baza"+timeTableList.get ( i ).getStartrent ().toString ()+" "+ timeTableList.get ( i ).getEndrend ().toString ());
                    isFree = false;
                    break;
                }
                if(postStartRentDate.getTime ()<=timeTableList.get ( i ).getStartrent ().getTime ()&& postEndRentDate.getTime ()>=timeTableList.get ( i ).getEndrend ().getTime ()){
                    System.out.println ("3 " +postStartRentDate.toString ()+" "+ postEndRentDate.toString ()+" Baza"+timeTableList.get ( i ).getStartrent ().toString ()+" "+ timeTableList.get ( i ).getEndrend ().toString ());
                    isFree = false;
                    break;
                }
            }







        return isFree;
    }

    public
    int getHourCount ( Date start , Date end ) {
        double startHour = hourToDouble ( start );
        double endHour = hourToDouble ( end );
        int hoursCount = ( int ) ( ( endHour - startHour ) * 2 );
        return hoursCount;

    }

    public
    int calc ( String open , String close ) {
        double startHour = hourToDouble ( open );
        double endHour = hourToDouble ( close );
        int hoursCount = ( int ) ( ( endHour - startHour ) * 2 );
        return hoursCount;

    }

    public
    double hourToDouble ( String hour ) {
        String hours, minutes;
        hours = hour.substring ( 0 , 2 );
        minutes = hour.substring ( 3 , 5 );

        double dHour;
        double dMinutes;

        dHour = Double.parseDouble ( hours );
        if ( minutes.equals ( "00" ) ) {
            dMinutes = 0.0;
        }
        else {
            dMinutes = 0.5;
        }

        return dHour + dMinutes;
    }

    private
    boolean checkCorrectOfRequest ( Long sportObjectid , DateTime start , DateTime end ) {
        OpenHours openHours = openHoursRepository.findBySportobjectid ( sportObjectid );
        List <String> opens = chcekDay ( start , openHours );
        SportObject sp = sportObjectReposiotry.getOne ( sportObjectid );
        Date localopen = sp.getDate ( opens.get ( 0 ) ,start.getMonthOfYear (),start.getDayOfMonth (),start.getYear ());
        Date localclose = sp.getDate ( opens.get ( 1 ),start.getMonthOfYear (),start.getDayOfMonth (),start.getYear () );
        System.out.println ("Moj "+start.toString ()+" "+end.toString ()+ " baza "+localopen.toString ()+" "+localclose.toString () );
        if ( ( start.toDate ( ).getTime ( ) >= localopen.getTime ( ) && start.toDate ( ).getTime ( ) <= localclose.getTime ( ) ) && end.toDate ( ).getTime ( ) <= localclose.getTime ( ) ) {


            return true;
        }
        else {

            return false;
        }
    }

    public
    double hourToDouble ( Date date ) {
        double hour = date.getHours ( );
        double minutes = date.getMinutes ( );
        if ( minutes == 0 ) {
            minutes = 0;
        }
        else {
            minutes = 0.5;
        }
        //   System.out.println (hour );
        return hour + minutes;

    }

    private
    List <String> chcekDay ( DateTime day , OpenHours openHoursOfObject ) {
        int numberOfDay = day.getDayOfWeek ( );
        String open;
        String close;
        //System.out.println (day.getDayOfWeek () );
        if ( numberOfDay == 1 ) {

            String hours = openHoursOfObject.getMondayHours ( );
            open = hours.substring ( 0 , 5 );
            close = hours.substring ( 6 , hours.length ( ) );

        }
        else
            if ( numberOfDay == 2 ) {


                String hours = openHoursOfObject.getTusedayHours ( );
                open = hours.substring ( 0 , 5 );
                close = hours.substring ( 6 , hours.length ( ) );
            }
            else
                if ( numberOfDay == 3 ) {

                    String hours = openHoursOfObject.getWensdayHours ( );
                    open = hours.substring ( 0 , 5 );
                    close = hours.substring ( 6 , hours.length ( ) );
                }
                else
                    if ( numberOfDay == 4 ) {

                        String hours = openHoursOfObject.getThrusdayHours ( );
                        open = hours.substring ( 0 , 5 );
                        close = hours.substring ( 6 , hours.length ( ) );
                    }
                    else
                        if ( numberOfDay == 5 ) {

                            String hours = openHoursOfObject.getFridayHours ( );
                            open = hours.substring ( 0 , 5 );
                            close = hours.substring ( 6 , hours.length ( ) );
                        }
                        else
                            if ( numberOfDay == 6 ) {

                                String hours = openHoursOfObject.getSaturdayHours ( );
                                open = hours.substring ( 0 , 5 );
                                close = hours.substring ( 6 , hours.length ( ) );
                            }
                            else {

                                String hours = openHoursOfObject.getSundayHours ( );
                                open = hours.substring ( 0 , 5 );
                                close = hours.substring ( 6 , hours.length ( ) );
                            }
        List <String> interval = new ArrayList <> ( );
        interval.add ( open );
        interval.add ( close );
        return interval;
    }

}
