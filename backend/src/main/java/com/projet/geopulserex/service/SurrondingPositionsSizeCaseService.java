package com.projet.geopulserex.service;


import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.projet.geopulserex.customclass.SimpleCoord;
import com.projet.geopulserex.customclass.positioncontollerresponseobjects.PositionControllerCourseResponse;
import com.projet.geopulserex.customclass.positioncontollerresponseobjects.PositionControllerEnginResponse;
import com.projet.geopulserex.entity.Position;
import com.projet.geopulserex.exception.Geo2railException;
import com.projet.geopulserex.exception.NoPositionfoundException;

@Service
public class SurrondingPositionsSizeCaseService {

    @Autowired
    CalculateRadiusService calculateRadius;

    @Autowired
    private CreateGeo2railPayloadService geo2railPayload;

    @Autowired
    private Geo2railRequestService geo2railrequest;

    @Autowired
    private ExtractCoordFromLignGeo2railService extractCoord;

    @Autowired
    private ExtractPolyligneFromGeo2railService getPolyligne;

    
    /**
     * 
     * @param timeStamp
     * @param enginNumb
     * @param surrondingPos
     * @param maxExceeding
     * @return
     * @throws ParseException
     */
    public PositionControllerEnginResponse enginSurrondingPosSizeEqualOne(String timeStamp,String enginNumb, List<Position> surrondingPos, int maxExceeding) throws ParseException{
        
        SimpleDateFormat givenHourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date formatedGivenHour = givenHourFormat.parse(timeStamp);
            Date timePosition = surrondingPos.get(0).getUtc_observation();
            if (formatedGivenHour.getTime() - timePosition.getTime() < 0) {
                throw new NoPositionfoundException(
                        "La position donnée est antérieure à la première position disponible: "
                                + timePosition.toString());
            } else {
                if (formatedGivenHour.getTime() - timePosition.getTime() < maxExceeding) {// millisecondes
                    SimpleCoord radiusStart = calculateRadius.getStartRadius(surrondingPos);
                    SimpleCoord radiusEnd = calculateRadius.getEndRadius(enginNumb, formatedGivenHour, timePosition,
                            radiusStart);
                    return new PositionControllerEnginResponse(null, radiusStart, radiusEnd);
                } else {
                    int timeToMessage = maxExceeding / 60000;
                    throw new NoPositionfoundException("La position donnée est postérieure de plus de " + timeToMessage
                            + " minutes à la dernière position disponible");
                }
            }
    }

    /**
     * 
     * @param timeStamp
     * @param enginNumb
     * @param surrondingPos
     * @return
     * @throws AuthenticationException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     */
    public PositionControllerEnginResponse enginSurrondingPosSizeEqualTwo(String timeStamp, String enginNumb, List<Position> surrondingPos) throws AuthenticationException, ClientProtocolException, IOException, ParseException{

        // préparation du json pour la requete à géo2rail
        JSONObject geo2railJSONpayload = geo2railPayload.getGeo2railPayload(surrondingPos);
        // requete pour récupération d'un trajet géo2rail entre les deux positions encadrantes
        JSONObject geo2railgeoJson = geo2railrequest.getGeo2railVector(geo2railJSONpayload);
        // extraction de la polyligne de la reponse geo2rail
        JSONArray geo2railSegment;
        if (geo2railgeoJson.has("features")) {
            geo2railSegment = getPolyligne.getPolyligne(geo2railgeoJson);
        } else {
            throw new Geo2railException(geo2railgeoJson.get("status").toString());
        }
        // recupération de la position du trajet géo2rail la plus proche de la position estimée de l'engin à l'heure donnée
        int estimatedPositionIndex = extractCoord.getPositionIndex(geo2railSegment, surrondingPos, timeStamp);
        SimpleCoord estimatedPositionCoord = extractCoord.getPosition(estimatedPositionIndex, geo2railSegment);
        Position estimatedPosition = new Position();
        estimatedPosition.setX(Double.valueOf(estimatedPositionCoord.getX()));
        estimatedPosition.setY(Double.valueOf(estimatedPositionCoord.getY()));
        // recupération des coordonnées d'une des extrémités du segment necessaire pour construire le rayon pour construire le cercle dans le frontend
        SimpleCoord radiusEnding = extractCoord.getPosition(0, geo2railSegment);
        // recupération des coordonnées du centre du segment pour être utilisées comme l'autre extremité du rayon pour construire le cercle dans le frontend
        int indexCoordCircleCenter = geo2railSegment.length() / 2;
        SimpleCoord circleCenter = extractCoord.getPosition(indexCoordCircleCenter, geo2railSegment);

        return new PositionControllerEnginResponse(estimatedPosition, circleCenter, radiusEnding);
    }

    /**
     * 
     * @param timeStamp
     * @param surrondingPos
     * @return
     * @throws ParseException
     * @throws NoPositionfoundException
     */
    public String courseSurrondingPosSizeEqualOne(String timeStamp, List<Position> surrondingPos) throws ParseException, NoPositionfoundException {

        SimpleDateFormat givenHourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date formatedGivenHour = givenHourFormat.parse(timeStamp);
            Date timePosition = surrondingPos.get(0).getUtc_observation();
            if (formatedGivenHour.getTime() - timePosition.getTime() < 0) {
                return "La position donnée est antérieure à la première position disponible: " + timePosition.toString();
            } else {
                return "La position donnée est supérieure à la dernière position disponible: " + timePosition.toString();
            }
    }

    /**
     * 
     * @param timeStamp
     * @param surrondingPos
     * @param courseNumb
     * @return
     * @throws AuthenticationException
     * @throws ClientProtocolException
     * @throws IOException
     * @throws ParseException
     */
    public PositionControllerCourseResponse courseSurrondingPosSizeEqualTwo(String timeStamp, List<Position> surrondingPos, String courseNumb) throws AuthenticationException, ClientProtocolException, IOException, ParseException{

        // Compare les courses id des positions retournées pour être sur qu'elles appartiennent à la même course et que la réponse soit pertinente.
        String firstSurrondingPosUtcStart = surrondingPos.get(0).getCourse().get(0).getUtc_depart().toString();
        String secondSurrondingPosUtcDepart = surrondingPos.get(1).getCourse().get(0).getUtc_depart().toString();
        if (firstSurrondingPosUtcStart.equals(secondSurrondingPosUtcDepart) == false) {
            throw new NoPositionfoundException(
                "L'horodatage demandé ne correspond pas avec la course demandée. La course " + courseNumb
                        + " précédante commence le " + firstSurrondingPosUtcStart + ". La course " + courseNumb
                        + " suivante commence le " + secondSurrondingPosUtcDepart + ".");
        }

        // préparion du json pour la requete à géo2rail
        JSONObject geo2railJSONpayload = geo2railPayload.getGeo2railPayload(surrondingPos);
        // requete pour récupération d'un trajet géo2rail entre les deux positions encadrantes
        JSONObject geo2railgeoJson = geo2railrequest.getGeo2railVector(geo2railJSONpayload);
        // extraction de la polyligne de la reponse geo2rail
        JSONArray geo2railSegment;
        if (geo2railgeoJson.has("features")) {
            geo2railSegment = getPolyligne.getPolyligne(geo2railgeoJson);
        } else {
            throw new Geo2railException(geo2railgeoJson.get("status").toString());
        }
        // recupération de la position du trajet géo2rail la plus proche de la position estimée de l'engin à l'heure donnée
        int estimatedPositionIndex = extractCoord.getPositionIndex(geo2railSegment, surrondingPos, timeStamp);
        SimpleCoord estimatedPositionCoord = extractCoord.getPosition(estimatedPositionIndex, geo2railSegment);
        Position estimatedPosition = new Position();
        estimatedPosition.setX(Double.valueOf(estimatedPositionCoord.getX()));
        estimatedPosition.setY(Double.valueOf(estimatedPositionCoord.getY()));
        String probalitySegment = geo2railSegment.toString();

        return new PositionControllerCourseResponse(estimatedPosition, probalitySegment);
    }
        
}
