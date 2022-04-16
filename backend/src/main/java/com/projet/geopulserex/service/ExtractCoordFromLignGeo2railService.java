package com.projet.geopulserex.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.util.List;

import com.projet.geopulserex.customclass.SimpleCoord;
import com.projet.geopulserex.entity.Position;
import com.projet.geopulserex.exception.Geo2railException;

@Service
public class ExtractCoordFromLignGeo2railService {

    /**
     * 
     * @param geo2railSegment
     * @param surrondingPos
     * @param timeStamp
     * @return
     * @throws ParseException
     */
    public int getPositionIndex(JSONArray geo2railSegment, List<Position> surrondingPos, String timeStamp)
            throws ParseException {

        if (geo2railSegment.length()<1 || geo2railSegment.isEmpty()){
            throw new Geo2railException("Le moteur géo2rail n'a pas renvoyé de trajet valide.");
        }else{
            Date timePosInf = (Date) surrondingPos.get(0).getUtc_observation();
            Date timePosSup = (Date) surrondingPos.get(1).getUtc_observation();
            long timeBetweenPosInfAndPosSup = timePosSup.getTime() - timePosInf.getTime(); // en milliseconde
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date formatedGivenHour = (Date) dateFormat.parse(timeStamp);
            long timeBetweenPosInfAndPosSupTimeFormat = formatedGivenHour.getTime() - timePosInf.getTime(); // en
                                                                                                            // milliseconde
            int givenHourIndex = (int) Math
                    .floor(((timeBetweenPosInfAndPosSupTimeFormat * 100) / timeBetweenPosInfAndPosSup) / 10) * 10;

            return (int) Math.ceil(((geo2railSegment.length()) * givenHourIndex) / 100);
        }
    }

    /**
     * 
     * @param indexOfEstimatedEnginPosition
     * @param geo2railSegment
     * @return
     */
    public SimpleCoord getPosition(int indexOfEstimatedEnginPosition, JSONArray geo2railSegment) {

        JSONArray estimatedEnginPosition;
        if (geo2railSegment.get(indexOfEstimatedEnginPosition) instanceof JSONArray) {
            estimatedEnginPosition = (JSONArray) geo2railSegment.get(indexOfEstimatedEnginPosition);
        } else {
            estimatedEnginPosition = geo2railSegment;
        }

        String xCord = (String) estimatedEnginPosition.get(0).toString();
        String yCord = (String) estimatedEnginPosition.get(1).toString();

        return new SimpleCoord(xCord, yCord);

    }
}
