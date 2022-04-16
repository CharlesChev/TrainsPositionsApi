package com.projet.geopulserex.controller;

import com.projet.geopulserex.customclass.SimpleExceptionObject;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Error404Controller implements ErrorController {
    
    @ResponseBody
    @RequestMapping("/roadError")
    public SimpleExceptionObject handleRoad404Error() {
        String message  = "Erreur 404! C'est route n'existe pas!!!";
        return new SimpleExceptionObject(message);
    }

}
