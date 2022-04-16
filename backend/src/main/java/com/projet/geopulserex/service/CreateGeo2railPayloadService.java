package com.projet.geopulserex.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.List;
import com.projet.geopulserex.entity.Position;

@Service
public class CreateGeo2railPayloadService {

    public JSONObject getGeo2railPayload(List<Position> surrondingPos) {
        int i = 0;
        JSONObject geo2railJSONpayload = new JSONObject();
        JSONArray features = new JSONArray();
        while (i != surrondingPos.size()) {
            JSONObject feature = new JSONObject();
            JSONObject geometry = new JSONObject();
            geometry.put("type", "Point");
            JSONArray coordinates = new JSONArray();
            coordinates.put(surrondingPos.get(i).getX());
            coordinates.put(surrondingPos.get(i).getY());
            geometry.put("coordinates", coordinates);
            JSONObject properties = new JSONObject();
            properties.put("lieu", "");
            properties.put("horaireJalonnement", "");
            properties.put("ordre", i);
            feature.put("properties", properties);
            feature.put("geometry", geometry);
            feature.put("type", "Feature");
            features.put(feature);
            i++;
        }
        geo2railJSONpayload.put("features", features);
        geo2railJSONpayload.put("type", "FeatureCollection");

        return geo2railJSONpayload;
    }

}
