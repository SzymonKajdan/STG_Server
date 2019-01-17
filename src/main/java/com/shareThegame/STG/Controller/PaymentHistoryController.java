package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.PaymentHisotry;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.PaymentHistoryRepository;
import com.shareThegame.STG.Repository.SportObjectReposiotry;
import com.shareThegame.STG.Repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public
class PaymentHistoryController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SportObjectReposiotry sportObjectReposiotry;
    @Autowired
    PaymentHistoryRepository paymentHistoryRepository;


           @PostMapping ( value = "/getActiveReserv", produces = "application/json")
        public @ResponseBody
        String  getActiveReserv(){
            String useremail=userAuth ();
            User user=userRepository.findByEmail ( useremail );
            List<PaymentHisotry> paymentHisotryList=paymentHistoryRepository.findAllByUseridAndStartrentAfter ( user.getId (),new Date (  ) );
               Collections.sort ( paymentHisotryList );
            JSONArray jsonObject=new JSONArray (  );
            for(PaymentHisotry it:paymentHisotryList){
                Map<String,Object> sportObjectMap = new HashMap<> ();
                sportObjectMap.put ( "exprrent",it.getExprrent () );
                sportObjectMap.put ( "cost",it.getCost () );
                sportObjectMap.put ( "startrent",it.getStartrent () );
                sportObjectMap.put ( "sportobjectid",it.getSportobjectid () );
                sportObjectMap.put ( "statusOfPaymnet",it.isStautsofpayment () );
                sportObjectMap.put ( "id",it.getId () );
                JSONArray tmp=new JSONArray ( );
                tmp.put (sportObjectMap );
                jsonObject.put (  sportObjectMap);

            }

        // jsonObject.put ( paymentHisotryList );
        return jsonObject.toString ();


    }
    @PostMapping ( value = "/getHistoryReserv", produces = "application/json" )
    public @ResponseBody
    String  getHistoryReserv() {
        String useremail = userAuth ( );
        User user = userRepository.findByEmail ( useremail );
        List <PaymentHisotry> paymentHisotryList = paymentHistoryRepository.findAllByUseridAndStartrentBefore ( user.getId ( ) , new Date ( ) );
        Collections.sort ( paymentHisotryList );
        JSONArray jsonObject = new JSONArray ( );
        for (PaymentHisotry it : paymentHisotryList) {
            Map <String, Object> sportObjectMap = new HashMap <> ( );
            sportObjectMap.put ( "exprrent" , it.getExprrent ( ) );
            sportObjectMap.put ( "cost" , it.getCost ( ) );
            sportObjectMap.put ( "startrent" , it.getStartrent ( ) );
            sportObjectMap.put ( "statusOfPaymnet",it.isStautsofpayment () );
            sportObjectMap.put ( "sportobjectid" , it.getSportobjectid ( ) );
            sportObjectMap.put ( "id" , it.getId ( ) );
            JSONArray tmp = new JSONArray ( );
            tmp.put ( sportObjectMap );
            jsonObject.put ( sportObjectMap );

        }
        return  jsonObject.toString ();
    }
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
