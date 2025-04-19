package com.example.Springbootpayemntgateway.Controller;

import com.example.Springbootpayemntgateway.Service.RazorpayService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private RazorpayService razorpayService;

    @PostMapping("create-order")
    public String createOrder(@RequestParam int amount,@RequestParam String currency) throws RazorpayException {
        return razorpayService.createOrder(amount,currency,"Recepitent_100");
    }


}
