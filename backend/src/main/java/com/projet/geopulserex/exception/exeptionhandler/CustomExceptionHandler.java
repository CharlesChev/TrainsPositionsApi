package com.projet.geopulserex.exception.exeptionhandler;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projet.geopulserex.customclass.SimpleExceptionObject;
import com.projet.geopulserex.exception.Geo2railException;
import com.projet.geopulserex.exception.GeomobileException;
import com.projet.geopulserex.exception.NoPositionfoundException;

import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(GeomobileException.class)//connexion a géomobile ok mais aucune position trouvée
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionObject handleGeomobileException(GeomobileException e){
        String message  = e.getMessage();
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(org.json.JSONException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public org.json.JSONException handleJSONException(org.json.JSONException e){
        return e;
    }

    @ExceptionHandler(NoPositionfoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public SimpleExceptionObject handleJSONException(NoPositionfoundException e){
        String message  = e.getMessage();
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)// parametres de requete pas aux bons formats
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionObject handlePathVariableException(javax.validation.ConstraintViolationException e){
        String message = e.getMessage();
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(java.io.IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleExceptionObject handleIOException(java.io.IOException e){
        String message = e.getMessage();
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(org.apache.http.auth.AuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleExceptionObject handleClientProtocolException(org.apache.http.auth.AuthenticationException e){
        String message = "Le mot de de passe ou l'identifiant géo2rail est érronné.";
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(java.text.ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionObject handleParseException(java.text.ParseException e){
        String message = "L'heure et la date doivent être au format 'yyyy-MM-dd HH:mm:ss'.";
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(org.apache.http.client.ClientProtocolException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SimpleExceptionObject handleClientProtocolException(org.apache.http.client.ClientProtocolException e){
        String message = "Il y a une erreur dans la requete faite à Géo2rail.";
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(Geo2railException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleExceptionObject handleClientProtocolException(Geo2railException e){
        String message  = e.getMessage();
        if("SERVICE_INDISPONIBLE".equals(message)){ 
            message = "Le service Géo2rail est indisponible, il n'est donc pas possible d'estimer une position.";
        }
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(org.springframework.dao.DataAccessResourceFailureException.class)//géomobile en pls
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleExceptionObject handleClientProtocolException(org.springframework.dao.DataAccessResourceFailureException e){
        String message  = e.getMessage();
        return new SimpleExceptionObject(message);
    }

    @ExceptionHandler(org.hibernate.exception.JDBCConnectionException.class)//géomobile en pls
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleExceptionObject handleClientProtocolException(org.hibernate.exception.JDBCConnectionException e){
        String message  = e.getMessage();
        return new SimpleExceptionObject(message);
    }

}
