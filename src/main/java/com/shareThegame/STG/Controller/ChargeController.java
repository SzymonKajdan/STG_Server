package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.ChargeRequest;
import com.shareThegame.STG.Service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @PostMapping ( value = "/charge", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )@ResponseBody
    public String charge ( ChargeRequest chargeRequest )
            throws StripeException {
        JSONObject json = new JSONObject ();

        chargeRequest.setDescription("Opłata za rezerwacje nr "+chargeRequest.getPaymentid ());
        chargeRequest.setCurrency(ChargeRequest.Currency.PLN);
        chargeRequest.setAmount(10*100);
        Charge charge = paymentsService.charge ( chargeRequest );
        json.put("status", charge.getStatus());
        json.put("chargeId", charge.getId());
        json.put("balance_transaction", charge.getBalanceTransaction());
        return json.toString();
    }

    @ExceptionHandler (StripeException.class)
    public String handleError (  StripeException ex ) {
        JSONObject jsonObject=new JSONObject (  );
        jsonObject.put("error", ex.getMessage());
        return jsonObject.toString ();
    }
}