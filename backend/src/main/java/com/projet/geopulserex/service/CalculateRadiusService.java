package com.projet.geopulserex.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Date;
import com.projet.geopulserex.entity.Position;

import com.projet.geopulserex.customclass.SimpleCoord;

@Service
public class CalculateRadiusService {

    @Value("${TGVspeed}")
    public int TGVspeed;

    @Value("${normalTrainSpeed}")
    public int normalTrainSpeed;
    
   public SimpleCoord getStartRadius(List<Position> posEncadrantes){
       
        return new SimpleCoord(String.valueOf(posEncadrantes.get(0).getX()),
        String.valueOf(posEncadrantes.get(0).getY()));
   }

    public SimpleCoord getEndRadius(String enginNumb, Date formatedGivenHour, Date timePosition, SimpleCoord radiusStart){

        int maxExceeding = (int) ((formatedGivenHour.getTime() - timePosition.getTime())/60000);//minutes
        String trainType = String.valueOf(enginNumb.charAt(0));
        int speed = normalTrainSpeed;
        if("T".equals(trainType)){
            speed = TGVspeed;
        }
        int speedMinute = speed/60; //en km/minute
        int distance = speedMinute*maxExceeding*1000; // en m
    
        double lenghtDemiGrandaxe = 6378137; // demi grand axe wsg84 en mètre
        double startLat = Double.parseDouble(radiusStart.getX());
        double pi =  Math.PI;

        double latEndRadius = startLat + ((distance/lenghtDemiGrandaxe)*(180/pi));

        return new SimpleCoord(Double.toString(latEndRadius),radiusStart.getY());
    }
}
