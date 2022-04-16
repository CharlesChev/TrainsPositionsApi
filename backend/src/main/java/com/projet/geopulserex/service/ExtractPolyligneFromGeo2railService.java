package com.projet.geopulserex.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ExtractPolyligneFromGeo2railService {
    
    public JSONArray getPolyligne(JSONObject geo2railJsonRes){

        JSONArray posArray = (JSONArray)geo2railJsonRes.get("features");
        JSONObject indexZeroJson = (JSONObject)posArray.get(0);
        JSONObject geometryJson = (JSONObject)indexZeroJson.get("geometry");
        return (JSONArray)geometryJson.get("coordinates");
    }
}
