package it.infn.ct.indigo.customisableApp.portlet.backends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpMethods;

import it.infn.ct.indigo.customisableApp.portlet.backends.utils.OneDataElement;

public class OneData {
    private String oneZone;
    private String basePath;
    private String token;

    public OneData(String oneZone, String basePath, String token) {
        super();
        StringBuilder fullUrl = new StringBuilder();
        if (!oneZone.startsWith("http")) {
            fullUrl.append("https://");
        }
        fullUrl.append(oneZone);
        if (!oneZone.contains(":")) {
            fullUrl.append(":8443");
        }
        if (!oneZone.endsWith("/")) {
            fullUrl.append("/api/v3/onezone/");
        }
        this.oneZone = fullUrl.toString();
        this.basePath = basePath;
        this.token = token;

        
        /*******************************************
         * 
         * DA RIMUOVERE
         * (inizio)
         * 
         *******************************************/
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
 
        // Install the all-trusting trust manager
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
 
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /***************************
        *
        * (fine)
        *
        ***************************/    
    }

    public List<OneDataElement> getElementsInFolder() {
        if (basePath.isEmpty() || basePath.equals("#")) {
            return getSpaces();
        }
        OneDataElement space = getSpace(basePath.substring(1).split("/")[0]);
        String providerUrl = getProviderUrl(space.getProviders());
        return null;
    }
    
    private List<OneDataElement> getSpaces() {
        List<OneDataElement> spaces = new ArrayList<>();
        JSONObject rawSpaces;
        String fullUrl = oneZone + "user/spaces";
        String json = oneDataGet(fullUrl);
        try {
            rawSpaces = JSONFactoryUtil.createJSONObject(json);
            JSONArray spcList = rawSpaces.getJSONArray("spaces");
            log.debug("Found " + spcList.length() + " spaces");
            for (int i=0; i < spcList.length(); i++) {
                OneDataElement space = getSpace(fullUrl + "/" + spcList.getString(i));
                if (space != null) {
                    spaces.add(space);
                    log.debug("Included the space " + space.getName());
                }
            }
        } catch (JSONException e) {
            log.error("The json returned by OneZone has errors: " + json);
        }
        
        return spaces;
    }

    private OneDataElement getSpace(String spaceUrl) {
        JSONObject rawSpace;
        OneDataElement space = new OneDataElement();
        String json = oneDataGet(spaceUrl);
        try {
            rawSpace = JSONFactoryUtil.createJSONObject(json);
            space.setId(rawSpace.getString("spaceId"));
            log.debug("Found the space " + space.getId());
            space.setName(rawSpace.getString("name"));
            space.setFolder(true);
            JSONArray prs = rawSpace.getJSONArray("providersSupports");
            for (int i = 0; i < prs.length(); i++) {
                space.addProvider(prs.getString(i));
            }
        } catch (JSONException e) {
            log.error("The json returned by OneZone has errors: " + json);
        }
        return space;
    }

    private String getProviderUrl(List<String> providerList) {
        return null;
    }

    private String oneDataGet(String urlFull) {
        StringBuilder resource = new StringBuilder();
        log.debug("Get the collection from " + urlFull);
        try {
            URL url = new URL(urlFull);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(HttpMethods.GET);
            connection.setRequestProperty("X-Auth-Token", "indigo:" + token);
            connection.setRequestProperty("Content-Type", "application/json");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                connection.disconnect();
                log.debug("Error contacting OneData: "
                        + connection.getResponseCode());
                return null;
            }
            BufferedReader resIn = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = resIn.readLine()) != null) {
                resource.append(inputLine);
            }
            resIn.close();
        } catch (MalformedURLException e) {
            log.error("OneZone url cannot be generated. The server is: " + oneZone);
            return null;
        } catch (IOException e) {
            log.error("Impossible to connect with OneZone: " + oneZone);
            return null;
        }
        log.debug("This is the oneData response: " + resource.toString());
        
        return resource.toString();

    }

    /**
     * The logger.
     */
    private Log log = LogFactoryUtil.getLog(OneData.class);
}
