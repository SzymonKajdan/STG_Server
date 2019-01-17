package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.ChargeRequest;
import com.shareThegame.STG.Model.PaymentHisotry;
import com.shareThegame.STG.Repository.PaymentHistoryRepository;
import com.shareThegame.STG.Service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;
    @Autowired
    private
    PaymentHistoryRepository paymentHistoryRepository;

    @PostMapping ( value = "/charge", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    @ResponseBody
    public String charge ( @RequestBody String  object )throws StripeException {
        JSONObject js=new JSONObject ( object );
        ChargeRequest chargeRequest=new ChargeRequest ();
        chargeRequest.setStripeToken ( js.get("stripeToken").toString () );
        chargeRequest.setStripeToken ( js.get ( "stripeEmail" ).toString () );
        JSONObject json = new JSONObject ();
        System.out.println ("charge " );
        //Sym.out.println (price );
        chargeRequest.setAmount ( Integer.valueOf (  js.get ( "price" ).toString ()) );
        chargeRequest.setDescription("Opłata za rezerwacje nr ");
        chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
        chargeRequest.setAmount(chargeRequest.getAmount ()*10*10);
        chargeRequest.setAmount ( chargeRequest.getAmount ()/2 );
        Charge charge = paymentsService.charge ( chargeRequest );
        if(charge.getStatus ().equals ( "succeeded" ))
        {
            PaymentHisotry paymentHistory=paymentHistoryRepository.getOne ( Long.valueOf ( js.get ( "id" ).toString () ) ) ;
            paymentHistory.setStautsofpayment ( true );
            paymentHistoryRepository.save ( paymentHistory );
        }
        json.put("status", charge.getStatus());
        System.out.println (json.toString () );
        return json.toString();
    }

    @ExceptionHandler (StripeException.class)
    public String handleError (  StripeException ex ) {
        JSONObject jsonObject=new JSONObject (  );
        jsonObject.put("error", ex.getMessage());
        return jsonObject.toString ();
    }




}