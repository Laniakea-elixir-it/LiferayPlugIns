/**
 * *********************************************************************
 * Copyright (c) 2016: Istituto Nazionale di Fisica Nucleare (INFN) -
 * INDIGO-DataCloud
 *
 * See http://www.infn.it and and http://www.consorzio-cometa.it for details on
 * the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **********************************************************************
 */
package it.infn.ct.indigo.futuregateway.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.expando.kernel.exception.DuplicateColumnNameException;
import com.liferay.expando.kernel.exception.DuplicateTableNameException;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.security.sso.iam.IAM;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;

/**
 * Manager of the Future Gateway instance.
 * The manager is responsible to keep the information of the Future Gateway
 * instance to use in the portal and the basic interaction during the
 * administration.
 *
 * <i>Note: the class is responsible for the interaction server side,
 * other interactions will occure on the client side by the javascript
 * part of the portlet.</i>
 */
@Component(immediate = true, service = FGServerManager.class)
public class FGServerManager {

    /**
     * Sets the FG end point for the portal instance.
     *
     * @param companyId The id of the instance
     * @param url The URL of the FG
     * @throws PortalException If the value cannot be saved
     */
    public final void setFGUrl(final long companyId, final String url)
            throws PortalException {
        expandoValueService.addValue(
                companyId, FGServerManager.class.getName(), "FG", "fgUrl",
                0, url);
    }

    /**
     * Retrieves the FG end point for the portal instance.
     *
     * @param companyId The id of the instance
     * @return The URL of the FG
     * @throws PortalException If the values cannot be accessed
     */
    public final String getFGUrl(final long companyId)
            throws PortalException {
        return expandoValueService.getData(companyId,
                FGServerManager.class.getName(), "FG", "fgUrl", 0, "");
    }

    /**
     * Add a new resource into the FG service.
     *
     * @param companyId The id of the instance
     * @param collection The name of the collection to post the new resource
     * @param resource The resource information in JSON format
     * @param token The token of the user performing the action
     * @return The Id of the new resource
     * @throws PortalException Cannot retrieve the server endpoint
     * @throws IOException Connect communicate with the server
     */
    public final String addResource(final long companyId,
            final String collection, final String resource,
            final String token)
                    throws PortalException, IOException {
        log.debug("Adding the new " + collection + ": " + resource);
        HttpURLConnection connection = getFGConnection(
                companyId, collection, null, token, HttpMethods.POST,
                FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_CONTENT_TYPE);
        OutputStream os = connection.getOutputStream();
        os.write(resource.getBytes());
        os.flush();
        if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new IOException("Impossible to add the resource."
                    + "Server response with code: "
                    + connection.getResponseCode());
        }
        StringBuilder result = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream()))) {
            String readLine;
            while ((readLine = br.readLine()) != null) {
                result.append(readLine);
            }
        }
        connection.disconnect();
        JSONObject jRes = JSONFactoryUtil.createJSONObject(result.toString());
        return jRes.getString(
                FGServerConstants.ATTRIBUTE_ID);
    }

    /**
     * Add a new resource into the FG service.
     *
     * @param companyId The id of the instance
     * @param collection The name of the collection to post the new resource
     * @param resource The resource information in JSON format
     * @param userId The id of the user performing the action
     * @return The Id of the new resource
     * @throws Exception The resource cannot be added
     */
    public final String addResource(final long companyId,
            final String collection, final String resource,
            final long userId)
                    throws Exception {
        return addResource(companyId, collection, resource,
                iam.getUserToken(userId));
    }

    /**
     * Add files into a resource on FG service.
     *
     * @param companyId The id of the instance
     * @param collection The name of the collection to post the new resource
     * @param resourceId The resource requiring the files
     * @param files The files to add to the resource
     * @param token The token of the user performing the action
     * @throws PortalException Cannot retrieve the server endpoint
     * @throws IOException Connect communicate with the server
     */
    public final void submitFilesResource(final long companyId,
            final String collection, final String resourceId,
            final Map<String, File> files, final String token)
                    throws PortalException, IOException {
        String boundary = Long.toHexString(System.currentTimeMillis());
        String crlf = "\r\n";
        log.info("Adding new files to " + collection + "/" + resourceId);

        HttpURLConnection connection = getFGConnection(
                companyId, collection, resourceId + "/input", token,
                HttpMethods.POST,
                "multipart/form-data; boundary=" + boundary);
        try (OutputStream output = connection.getOutputStream();
                PrintWriter writer = new PrintWriter(
                        new OutputStreamWriter(
                                output, StandardCharsets.UTF_8), true);) {
            for (String fName: files.keySet()) {
                writer.append("--" + boundary).append(crlf);
                writer.append(
                        "Content-Disposition: form-data; "
                        + "name=\"file[]\"; filename=\""
                        + fName + "\"").append(crlf);
                writer.append("Content-Type: "
                        + URLConnection.guessContentTypeFromName(
                                fName)).append(crlf);
                writer.append("Content-Transfer-Encoding: binary").
                    append(crlf);
                writer.append(crlf).flush();
                Files.copy(files.get(fName).toPath(), output);
                output.flush();
                writer.append(crlf).flush();
            }
            writer.append("--" + boundary + "--").append(crlf).flush();
        }
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("Impossible to post files to the resource "
                    + collection + "/" + resourceId
                    + ". Server response with code: "
                    + connection.getResponseCode());
        }
    }

    /**
     * Add files into a resource on FG service.
     *
     * @param companyId The id of the instance
     * @param collection The name of the collection to post the new resource
     * @param resourceId The resource requiring the files
     * @param files The files to add to the resource
     * @param userId The id of the user performing the action
     * @throws Exception Impossible to retrieve the user token
     */
    public final void submitFilesResource(final long companyId,
            final String collection, final String resourceId,
            final Map<String, File> files, final long userId)
                    throws Exception {
        submitFilesResource(companyId, collection, resourceId,
                files, iam.getUserToken(userId));
    }

    /**
     * Retrieves a collection from the FG service.
     *
     * @param companyId The id of the instance
     * @param collection The name of the collection to post the new resource
     * @param token The authorisation token for the user
     * @return The full raw collection in json format
     * @throws PortalException Cannot retrieve the server endpoint
     * @throws IOException Connect communicate with the server
     */
    public String getCollection(final long companyId,
            final String collection, final String token)
                    throws PortalException, IOException {
        HttpURLConnection connection = getFGConnection(
                companyId, collection, null, token, HttpMethods.GET,
                FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_CONTENT_TYPE);
        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            connection.disconnect();
            log.debug("FG server response code not correct: "
                    + connection.getResponseCode());
            throw new IOException("Server response with code: "
                    + connection.getResponseCode());
        }
        BufferedReader collIn = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder collections = new StringBuilder();
        while ((inputLine = collIn.readLine()) != null) {
            collections.append(inputLine);
        }
        collIn.close();
        log.debug("This is the collection: " + collections.toString());
        return collections.toString();
    }

    /**
     * Retrieves the available infrastructures from the FG service.
     *
     * @param companyId The id of the instance
     * @param userId The id of the user performing the request
     * @return A map of enabled infrastructures. The map contains id and name
     * @throws Exception Cannot communicate with the server
     */
    public final Map<String, String> getInfrastructures(
            final long companyId, final long userId)
                    throws Exception {
        Map<String, String> mapInfras = new HashMap<>();
        String rawCollection = getCollection(companyId,
                FGServerConstants.INFRASTRUCTURE_COLLECTION,
                iam.getUserToken(userId));
        log.debug("Infrastructure json: " + rawCollection);
        JSONObject jsonInfras =
                JSONFactoryUtil.createJSONObject(rawCollection);
        JSONArray jAInfras = jsonInfras.getJSONArray(
                FGServerConstants.INFRASTRUCTURE_COLLECTION);
        log.debug("Available " + jAInfras.length() + " infrastructures");
        for (int i = 0; i < jAInfras.length(); i++) {
            JSONObject jOInfra = jAInfras.getJSONObject(i);
            log.debug("Infrastructure "
                    + jOInfra.getString(FGServerConstants.ATTRIBUTE_ID)
                    + " enabled option is "
                    + jOInfra.getBoolean(FGServerConstants.ATTRIBUTE_ENABLED));
            if (jOInfra.getBoolean(FGServerConstants.ATTRIBUTE_ENABLED)) {
                mapInfras.put(
                        jOInfra.getString(FGServerConstants.ATTRIBUTE_ID),
                        jOInfra.getString(FGServerConstants.ATTRIBUTE_NAME));
            }
        }
        return mapInfras;
    }

    /**
     * Retrive the connection to the server.
     *
     * @param companyId The companyId
     * @param collection The collection
     * @param resourceId The resource
     * @param token The Token
     * @param method The request method
     * @param contentType The content type
     * @return The active connection.
     * @throws PortalException Cannot retrieve the information
     * @throws IOException Cannot open the connection
     */
    public HttpURLConnection getFGConnection(final long companyId,
            final String collection, final String resourceId,
            final String token, final String method, final String contentType)
                    throws PortalException, IOException {
        String finalPath = collection;
        if (resourceId != null) {
            finalPath = finalPath + "/" + resourceId;
        }
        URL url = new URL(getFGUrl(companyId) + "/" + finalPath);
        log.debug("Get the collection from " + url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Authorization",
                "Bearer " + token);
        connection.setRequestProperty("Content-Type", contentType);
        return connection;
    }

    /**
     * Actions to do when the component is activated.
     * The only activity is to create the tables in the DB to save
     * the configurations for the FG
     */
    @Activate
    protected final void activate() {
        List<Company> companys = companyService.getCompanies();

        for (Company company : companys) {
            try {
                setupExpando(company.getCompanyId());
            } catch (Exception e) {
                if (log.isWarnEnabled()) {
                    log.warn("Unable to setup FutureGateway service for"
                            + " company " + company.getCompanyId() + ": "
                            + e.getMessage());
                }
            }
        }
    }

    /**
     * Sets the company service.
     * Used to retrieve the list of instances during the activation
     *
     * @param companyLocalService The local company service
     */
    @Reference(unbind = "-")
    protected final void setCompanyLocalService(
            final CompanyLocalService companyLocalService) {
        companyService = companyLocalService;
    }

    /**
     * Sets the ExpandoColumn service.
     * Used to add columns in the expando table related to the FG
     *
     * @param expandoColumnLocalService The local expando column service
     */
    @Reference(unbind = "-")
    protected final void setExpandoColumnLocalService(
            final ExpandoColumnLocalService expandoColumnLocalService) {
        expandoColumnService = expandoColumnLocalService;
    }

    /**
     * Sets the ExpandoTable service.
     * Used to create an expando table for the FG
     *
     * @param expandoTableLocalService The local expando table service
     */
    @Reference(unbind = "-")
    protected final void setExpandoTableLocalService(
            final ExpandoTableLocalService expandoTableLocalService) {
        expandoTableService = expandoTableLocalService;
    }

    /**
     * Sets the ExpandoValue service.
     * Used to save and retrive values from the expando table
     *
     * @param expandoValueLocalService The local expando value service
     */
    @Reference(unbind = "-")
    protected final void setExpandoValueLocalService(
            final ExpandoValueLocalService expandoValueLocalService) {
        expandoValueService = expandoValueLocalService;
    }

    /**
     * Sets the ModuleServiceLifecycle.
     * <b>Not used</b>
     * @param moduleServiceLifecycle The module service lifecycle
     */
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
    protected final void setModuleServiceLifecycle(
            final ModuleServiceLifecycle moduleServiceLifecycle) {
    }

    /**
     * Creates the expando table for the FG.
     *
     * @param companyId The id of the instance owning the table
     * @throws Exception If the table cannot be created
     */
    private void setupExpando(final long companyId) throws Exception {
        ExpandoTable table = null;
        try {
            table = expandoTableService.addTable(
                    companyId, FGServerManager.class.getName(), "FG");
        } catch (DuplicateTableNameException dtne) {
            table = expandoTableService.getTable(
                    companyId, FGServerManager.class.getName(), "FG");
        }

        try {
            expandoColumnService.addColumn(
                    table.getTableId(), "fgUrl", ExpandoColumnConstants.STRING);
        } catch (DuplicateColumnNameException dcne) {
        }
    }

    /**
     * Sets the iam component.
     *
     * @param iamComp The iam component
     */
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
    }

    /**
     * The logger.
     */
    private final Log log = LogFactoryUtil.getLog(FGServerManager.class);

    /**
     * The Company service.
     */
    private CompanyLocalService companyService;

    /**
     * The ExpandoColumn service.
     */
    private ExpandoColumnLocalService expandoColumnService;

    /**
     * The ExpandoTable service.
     */
    private ExpandoTableLocalService expandoTableService;

    /**
     * The ExpandoValue service.
     */
    private ExpandoValueLocalService expandoValueService;

    /**
     * The IAM service.
     */
    private IAM iam;
}
