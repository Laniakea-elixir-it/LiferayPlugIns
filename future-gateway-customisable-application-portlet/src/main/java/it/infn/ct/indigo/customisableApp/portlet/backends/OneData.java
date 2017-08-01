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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

/**
 * Implements the interaction with OneData remote services.
 */
public class OneData {
    /**
     * URL of the OneZone endpoint.
     * It can be only the hostname or the full url to the APIs
     */
    private String oneZone;
    /**
     * The path to check in OneZone.
     * The format is <i>#_space_id_/_folder_1_/_folder_2_/.../_folder_n_</i>
     */
    private String basePath;
    /**
     * The user token.
     * It has to be the IAM token
     */
    private String token;

    /**
     * Initialise the OneData object with the final url of OneZone
     * and the other parameters.
     * @param oneZoneUrl The url of OneZone.
     * It can be only the hostname or the full url to the APIs
     * @param basePathDiscovery The path where the discovery should start.
     * The format is <i>#_space_id_/_folder_1_/_folder_2_/.../_folder_n_</i>
     * @param tokenIAM The user token released by IAM.
     */
    public OneData(final String oneZoneUrl, final String basePathDiscovery,
            final String tokenIAM) {
        super();
        StringBuilder fullUrl = new StringBuilder();
        if (!oneZoneUrl.startsWith("http")) {
            fullUrl.append("https://");
        }
        fullUrl.append(oneZoneUrl);
        if (!oneZoneUrl.contains(":")) {
            fullUrl.append(":8443");
        }
        if (!oneZoneUrl.endsWith("/")) {
            fullUrl.append("/api/v3/onezone/");
        }
        this.oneZone = fullUrl.toString();
        this.basePath = basePathDiscovery;
        this.token = tokenIAM;

        /*******************************************
         *
         * DA RIMUOVERE
         * (inizio)
         *
         *******************************************/
        // Create a trust manager that does not
        // validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[]
                            getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            final X509Certificate[] certs,
                            final String authType) {
                    }
                    public void checkServerTrusted(
                            final X509Certificate[] certs,
                            final String authType) {
                    }
            }
        };

        // Install the all-trusting trust manager
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(final String hostname,
                    final SSLSession session) {
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

    /**
     * Retrieves the element registered in the base path.
     *
     * @return A list of {@link OneDataElement}. Each item represents
     * an entry in the directory defined in the base path associated
     * with this object
     */
    public final List<OneDataElement> getElementsInFolder() {
        if (basePath.isEmpty() || basePath.equals("#")) {
            log.debug("Checking the OneData root " + basePath);
            return getSpaces();
        }
        log.debug("Looking for files in " + basePath);
        OneDataElement space = getSpace(
                oneZone + "user/spaces/"
                        + basePath.substring(1).split("/")[0]);
        List<String> providerUrls = getProviderUrls(space.getProviders());
        Random rand = new Random();
        List<OneDataElement> content = null;
        while (content == null && !providerUrls.isEmpty()) {
            int pos = rand.nextInt(providerUrls.size());
            String url = providerUrls.get(pos);
            providerUrls.remove(pos);
            log.debug("Check file list on " + url);
            List<OneDataElement> files = getFiles(url, space);
            log.debug("The provider found " + files.size() + " file entries");
            if (files != null && !files.isEmpty()) {
                return files;
            }
        }
        return null;
    }

    /**
     * Retrieves the file list.
     *
     * @param providerUrl Url of OneProvider. Can be the hostname
     * only or the full url to the endpoint
     * @param space The space containg the directory tree.
     *
     * @return A list of {@link OneDataElement}. Each item represents a file
     * or a directory in the associated path
     */
    private List<OneDataElement> getFiles(
            final String providerUrl, final OneDataElement space) {
        List<OneDataElement> files = new ArrayList<>();
        JSONArray rawFiles;
        StringBuilder fullUrl = new StringBuilder();
        fullUrl.append(providerUrl).append("files/").append(space.getName());
        if (basePath.contains("/")) {
            fullUrl.append(basePath.substring(basePath.indexOf("/")));
        }
        log.debug("Lookup files in folder " + fullUrl.toString());
        String json = oneDataGet(fullUrl.toString());
        try {
            rawFiles = JSONFactoryUtil.createJSONArray(json);
            log.debug("Found " + rawFiles.length() + " files/directories");
            for (int i = 0; i < rawFiles.length(); i++) {
                JSONObject rawFile = rawFiles.getJSONObject(i);
                OneDataElement file = new OneDataElement();
                file.setId(rawFile.getString("id"));
                String fileName = rawFile.getString("path");
                file.setName(fileName.substring(fileName.lastIndexOf("/") + 1));
                file.setFolder(isFolder(providerUrl + "/" + fileName));

                if (file != null) {
                    files.add(file);
                    log.debug("Included the file " + file.getName());
                }
            }
        } catch (JSONException e) {
            log.error("The json returned by OneZone has errors: " + json);
        }
        return files;
    }

    /**
     * Checks if a file is a folder.
     *
     * @param fileUrl The url of the file. The url has contains all
     * the elements defined for the OneProvider
     * @return True if the fileUrl represent a folder, false otherwise
     */
    private boolean isFolder(final String fileUrl) {
        JSONArray rawSpace;
        String json = oneDataGet(fileUrl);
        try {
            rawSpace = JSONFactoryUtil.createJSONArray(json);
            if (rawSpace.length() == 1) {
                return !fileUrl.endsWith(
                        rawSpace.getJSONObject(0).getString("path"));
            }
        } catch (JSONException e) {
            log.error("The json returned by OneZone has errors: " + json);
        }
        return true;
    }

    /**
     * Retrieves the user spaces.
     *
     * @return A list of {@link OneDataElement}. Each item represents a user
     * space in OneZone
     */
    private List<OneDataElement> getSpaces() {
        List<OneDataElement> spaces = new ArrayList<>();
        JSONObject rawSpaces;
        String fullUrl = oneZone + "user/spaces";
        String json = oneDataGet(fullUrl);
        try {
            rawSpaces = JSONFactoryUtil.createJSONObject(json);
            JSONArray spcList = rawSpaces.getJSONArray("spaces");
            log.debug("Found " + spcList.length() + " spaces");
            for (int i = 0; i < spcList.length(); i++) {
                OneDataElement space = getSpace(
                        fullUrl + "/" + spcList.getString(i));
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

    /**
     * Retrieves space details.
     *
     * @param spaceUrl Url of the space.
     *
     * @return A {@link OneDataElement} representing the space.
     */
    private OneDataElement getSpace(final String spaceUrl) {
        JSONObject rawSpace;
        OneDataElement space = new OneDataElement();
        String json = oneDataGet(spaceUrl);
        try {
            rawSpace = JSONFactoryUtil.createJSONObject(json);
            space.setId(rawSpace.getString("spaceId"));
            log.debug("Found the space " + space.getId());
            space.setName(rawSpace.getString("name"));
            space.setFolder(true);
            JSONObject prs = rawSpace.getJSONObject("providersSupports");
            for (Iterator<String> keys = prs.keys(); keys.hasNext();) {
                space.addProvider(keys.next());
            }
        } catch (JSONException e) {
            log.error("The json returned by OneZone has errors: " + json);
        }
        return space;
    }

    /**
     * Retrieves the urls of the providers supporting the space containing the
     * associated path.
     *
     * @param providerList A list of provider ids.
     *
     * @return The list of urls.
     */
    private List<String> getProviderUrls(final List<String> providerList) {
        List<String> providerUrls = new ArrayList<>();
        String fullUrl = oneZone + "providers/";
        for (String provider: providerList) {
            try {
                JSONObject providerDetails =
                        JSONFactoryUtil.createJSONObject(
                                oneDataGet(fullUrl + provider));
                JSONArray urls = providerDetails.getJSONArray("urls");
                for (int i = 0; i < urls.length(); i++) {
                    StringBuilder urlProvider = new StringBuilder();
                    String url = urls.getString(i);
                    if (!url.startsWith("http")) {
                        urlProvider.append("https://");
                    }
                    urlProvider.append(url);
                    if (!url.contains(":")) {
                        urlProvider.append(":8443");
                    }
                    if (!url.endsWith("/")) {
                        urlProvider.append("/api/v3/oneprovider/");
                    }
                    providerUrls.add(urlProvider.toString());
                }
            } catch (JSONException e) {
                log.error("Impossible to read json for "
                        + fullUrl + provider + ". " + e.getMessage());
            }
        }
        return providerUrls;
    }

    /**
     * Connects to OneData service a return the output.
     *
     * @param urlFull Url to connect.
     *
     * @return The output of the GET operation.
     */
    private String oneDataGet(final String urlFull) {
        StringBuilder resource = new StringBuilder();
        log.debug("Get the collection from " + urlFull);
        try {
            URL url = new URL(urlFull);
            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();
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
            log.error("Cannot generate url, it is: " + urlFull);
            return null;
        } catch (IOException e) {
            log.error("Impossible to read output from OneData at: " + urlFull);
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
