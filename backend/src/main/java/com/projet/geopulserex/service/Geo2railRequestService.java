package com.projet.geopulserex.service;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

import com.projet.geopulserex.exception.Geo2railException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Geo2railRequestService {

    @Value("${ge2railUser}")
    public String user;
    @Value("${geo2railPassword}")
    public String password;
    @Value("${geo2railRootUrl}")
    public String geo2railRootUrl;

    
     /**
      * 
      * @param geo2railJSONpayload
      * @return
      * @throws AuthenticationException
      * @throws ClientProtocolException
      * @throws IOException
      */
    public JSONObject getGeo2railVector(JSONObject geo2railJSONpayload)
            throws AuthenticationException, ClientProtocolException, IOException {

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost postRequest = new HttpPost(geo2railRootUrl);
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user, password);
        String strRootJOSN = geo2railJSONpayload.toString();
        StringEntity payload = new StringEntity(strRootJOSN);
        postRequest.setEntity(payload);
        postRequest.setHeader("openRailRoutingWebClient", "GeopulseRexDev");
        postRequest.setHeader("Accept", "application/json");
        postRequest.setHeader("Content-type", "application/json");
        postRequest.addHeader(new BasicScheme().authenticate(creds, postRequest, null));
        CloseableHttpResponse response = client.execute(postRequest);

        HttpEntity responseBody = response.getEntity();
        int status = response.getStatusLine().getStatusCode();
        if (status != 200) {
            throw new Geo2railException(
                    "Code reponse geo2rail: " + status + ". Le service geo2rail semble inacessible.");
        }
        String geo2railResp = EntityUtils.toString(responseBody);
        client.close();

        return new JSONObject(geo2railResp);
    }

}
