package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Model.ChargeRequest;

import com.shareThegame.STG.Service.StripeService;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckoutController {

    @Value ("pk_test_LqwS35HxVYhAuIy28LXE7qv5")
    private String stripePublicKey;
    @Autowired
    private StripeService paymentsService;
    @PostMapping ( value = "/checkout", produces = "application/json", consumes = "application/x-www-form-urlencoded;charset=UTF-8" )
    public String checkout ( ChargeRequest chargeRequest) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
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
}