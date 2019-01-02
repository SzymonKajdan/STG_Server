package com.shareThegame.STG.Controller;

import com.shareThegame.STG.Repository.EmailSender;
import com.shareThegame.STG.Model.User;
import com.shareThegame.STG.Repository.UserRepository;
import com.shareThegame.STG.Service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Controller
    public class EmailController {
        private final EmailSender emailSender;
        private final TemplateEngine templateEngine;
        @Autowired
        public EmailController(EmailSender emailSender,
                               TemplateEngine templateEngine){
            this.emailSender = emailSender;
            this.templateEngine = templateEngine;
        }
        @Autowired
        UserRepository userRepository;
        @Autowired
        UserService userService;

        @PostMapping (value = "/rememberPassword" ,produces = "application/json",consumes = "application/x-www-form-urlencoded;charset=UTF-8")
        public @ResponseBody
      String rememberPassword( String email ) {
            User user= userRepository.findByEmail ( email );
            System.out.println (user );
            if(user!=null) {
                Context context = new Context ( );
                String password=passwordGenerator ();
                userService.changePassword ( user,password );
                context.setVariable ( "header" , "STG" );
                context.setVariable ( "title" , "Nowe hasło dla "+user.getUsername () );
                context.setVariable ( "description" , password );
                String body = templateEngine.process ( "ResetPassword" , context );
                emailSender.sendEmail ( user.getEmail (), "STG nowe hasło " , body );
                return new JSONObject (  ).put ( "message","MAIL_SEND" ).toString ();
            }else{
                return new JSONObject (  ).put ( "message","MAIL_NOT_SEND" ).toString ();
            }
        }

        private String passwordGenerator(){
           String newPass=  RandomStringUtils.randomAscii(7);
            System.out.println (newPass );
            return newPass;
        }
    }

