package com.projet.geopulserex.customclass;

//Objet utilisé dans CustomExceptionHandler pour renvoyer le message et le code d'erreur.
public class SimpleExceptionObject {

    private String message;

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public SimpleExceptionObject(String message){
        this.message = message;
    }

    
}
