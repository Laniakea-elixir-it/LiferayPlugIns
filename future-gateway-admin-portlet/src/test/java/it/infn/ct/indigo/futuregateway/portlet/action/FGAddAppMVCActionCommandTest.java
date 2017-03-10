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
package it.infn.ct.indigo.futuregateway.portlet.action;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.futuregateway.server.FGServerConstants;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * FGAddAppMVCActionCommand tests collection.
 */
@RunWith(MockitoJUnitRunner.class)
public class FGAddAppMVCActionCommandTest {

    /**
     * Fake request for process action.
     */
    @Mock
    private ActionRequest actionRequest;

    /**
     * Fake request for http.
     */
    @Mock
    private HttpServletRequest httpRequest;

    /**
     * Fake request for http update.
     */
    @Mock
    private UploadPortletRequest uploadRequest;

    /**
     * Fake request for liferay.
     */
    @Mock
    private LiferayPortletRequest liferayRequest;

    /**
     * Fake response for process action.
     */
    @Mock
    private ActionResponse actionResponse;

    /**
     * Fake theme display.
     */
    @Mock
    private ThemeDisplay themeDisplay;

    /**
     * Fake layout.
     */
    @Mock
    private Layout layout;

    /**
     * Fake portal util.
     */
    @Mock
    private Portal portalImpl;

    /**
     * Fake portlet config.
     */
    @Mock
    private PortletConfig portletConfig;

    /**
     * Fake FGServerManager.
     */
    @Mock
    private FGServerManager fgSManager;

    /**
     * Prepare the environment.
     * @throws Exception In case of a problem to replicate Liferay context
     */
    @Before
    public final void setUp() throws Exception {
        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
        PortalUtil pu = new PortalUtil();
        pu.setPortal(portalImpl);
        Mockito.when(themeDisplay.getCompanyId()).thenReturn(0L);
        Mockito.when(themeDisplay.getUserId()).thenReturn(0L);
        Mockito.when(themeDisplay.getLayout()).thenReturn(layout);
        Mockito.when(layout.isTypeControlPanel()).thenReturn(true);
        Mockito.when(
                actionRequest.getAttribute(
                        JavaConstants.JAVAX_PORTLET_CONFIG)).
            thenReturn(portletConfig);
        Mockito.when(
                portletConfig.getInitParameter(
                        "add-process-action-success-action")).
            thenReturn("true");
        Mockito.when(actionRequest.getAttribute(WebKeys.THEME_DISPLAY)).
            thenReturn(themeDisplay);
        Mockito.when(actionRequest.getParameter("redirect")).
            thenReturn(null);
        Mockito.when(
                portalImpl.getUploadPortletRequest(
                        Mockito.any(PortletRequest.class))).
            thenReturn(uploadRequest);
        Mockito.when(
                portalImpl.getHttpServletRequest(
                        Mockito.any(PortletRequest.class))).
            thenReturn(httpRequest);
        Mockito.when(
                portalImpl.getOriginalServletRequest(
                        Mockito.any(HttpServletRequest.class))).
            thenReturn(httpRequest);
        Mockito.when(liferayRequest.getPortletName()).thenReturn("");
        Mockito.when(
                portalImpl.getLiferayPortletRequest(
                        Mockito.any(PortletRequest.class))).
            thenReturn(liferayRequest);
    }

    /**
     * Test the process action.
     */
    @Test
    public final void testDoProcessAction() {
        Mockito.when(actionRequest.getParameter("fg-app-name")).
            thenReturn("fg-app-name");
        Mockito.when(actionRequest.getParameter("fg-app-description")).
            thenReturn("fg-app-description");
        Mockito.when(actionRequest.getParameter("fg-app-enabled")).
            thenReturn("true");
        Mockito.when(actionRequest.getParameter("fg-app-outcome")).
            thenReturn("fg-app-outcome");
        String[] paramNames = {"fg-app-parameter-name-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-parameter-name")).
            thenReturn(paramNames);
        String[] paramValues = {"fg-app-parameter-value-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-parameter-value")).
            thenReturn(paramValues);
        String[] paramDescriptions = {"fg-app-parameter-description-1"};
        Mockito.when(
                httpRequest.getParameterValues(
                        "fg-app-parameter-description")).
            thenReturn(paramDescriptions);
        String[] infras = {"fg-app-infrastructure-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-infrastructure")).
            thenReturn(infras);
        String[] fileUrls = {"http://test.com/fg-app-file-url-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-file-url")).
            thenReturn(fileUrls);
        Mockito.when(uploadRequest.getFiles(Mockito.anyString())).
            thenReturn(new File[1]);
        String[] fileNames = new String[1];
        Mockito.when(uploadRequest.getFileNames(Mockito.anyString())).
            thenReturn(fileNames);
        Mockito.when(
                portalImpl.getUploadPortletRequest(
                        Mockito.any(PortletRequest.class))).
            thenReturn(uploadRequest);
        try {
            Mockito.when(
                    fgSManager.addResource(
                            Mockito.anyLong(), Mockito.anyString(),
                            Mockito.anyString(), Mockito.anyLong())).
                thenReturn("");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        FGAddAppMVCActionCommand fgAAMAC = new FGAddAppMVCActionCommand();
        fgAAMAC.setFGServerManeger(fgSManager);
        try {
            fgAAMAC.doProcessAction(actionRequest, actionResponse);
            ArgumentCaptor<String> json = ArgumentCaptor.forClass(
                    String.class);
            Mockito.verify(fgSManager).addResource(
                    Mockito.anyLong(), Mockito.anyString(),
                    json.capture(), Mockito.anyLong());
            JSONObject jObj = JSONFactoryUtil.createJSONObject(
                    json.getValue());
            Assert.assertEquals("fg-app-name", jObj.getString("name"));
            Assert.assertEquals(
                    "fg-app-description", jObj.getString("description"));
            Assert.assertEquals("fg-app-outcome", jObj.getString("outcome"));
            Assert.assertEquals("true", jObj.getString("enabled"));
            Assert.assertEquals(infras[0], jObj.getJSONArray("infrastructures").
                    getString(0));
            Assert.assertEquals(
                    "fg-app-parameter-name-1", jObj.getJSONArray("parameters").
                    getJSONObject(0).get("name"));
            Assert.assertEquals(
                    "fg-app-parameter-description-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("description"));
            Assert.assertEquals(
                    "fg-app-parameter-value-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("value"));
            Assert.assertEquals(
                    "http://test.com/fg-app-file-url-1",
                    jObj.getJSONArray("files").
                    getString(0));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the process action with a file error.
     */
    @Test
    public final void testDoProcessActionWithErrorOnFile() {
        Mockito.when(actionRequest.getParameter("fg-app-name")).
            thenReturn("fg-app-name");
        Mockito.when(actionRequest.getParameter("fg-app-description")).
            thenReturn("fg-app-description");
        Mockito.when(actionRequest.getParameter("fg-app-enabled")).
            thenReturn("true");
        Mockito.when(actionRequest.getParameter("fg-app-outcome")).
            thenReturn("fg-app-outcome");
        String[] paramNames = {"fg-app-parameter-name-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-parameter-name")).
            thenReturn(paramNames);
        String[] paramValues = {"fg-app-parameter-value-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-parameter-value")).
            thenReturn(paramValues);
        String[] paramDescriptions = {"fg-app-parameter-description-1"};
        Mockito.when(
                httpRequest.getParameterValues(
                        "fg-app-parameter-description")).
            thenReturn(paramDescriptions);
        String[] infras = {"fg-app-infrastructure-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-infrastructure")).
            thenReturn(infras);
        String[] fileUrls = {"fg-app-file-url-1"};
        Mockito.when(httpRequest.getParameterValues("fg-app-file-url")).
            thenReturn(fileUrls);
        Mockito.when(uploadRequest.getFiles(Mockito.anyString())).
            thenReturn(null);
        String[] fileNames = {"file1"};
        Mockito.when(uploadRequest.getFileNames(Mockito.anyString())).
            thenReturn(fileNames);
        Map<String, String> infraMap = new HashMap<>();
        infraMap.put("1", "infra1");
        try {
            Path tempFile = Files.createTempFile(null, null);
            tempFile.toFile().deleteOnExit();
            Files.write(tempFile, fileNames[0].getBytes(),
                    StandardOpenOption.WRITE);
            File[] files = new File[1];
            files[0] = tempFile.toFile();
            Mockito.when(uploadRequest.getFiles(Mockito.anyString())).
                thenReturn(files);
            Mockito.when(
                    fgSManager.addResource(
                            Mockito.anyLong(), Mockito.anyString(),
                            Mockito.anyString(), Mockito.anyLong())).
                thenReturn("");
            Mockito.doThrow(new IOException()).when(fgSManager).
                submitFilesResource(Mockito.anyLong(), Mockito.anyString(),
                        Mockito.anyString(),
                        ArgumentMatchers.<Map<String, File>>any(),
                        Mockito.anyLong());
            Mockito.when(fgSManager.getInfrastructures(
                    Mockito.anyLong(), Mockito.anyLong())).
                thenReturn(infraMap);
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        FGAddAppMVCActionCommand fgAAMAC = new FGAddAppMVCActionCommand();
        fgAAMAC.setFGServerManeger(fgSManager);
        try {
            fgAAMAC.doProcessAction(actionRequest, actionResponse);
            ArgumentCaptor<String> json = ArgumentCaptor.forClass(
                    String.class);
            Mockito.verify(fgSManager).addResource(
                    Mockito.anyLong(), Mockito.anyString(),
                    json.capture(), Mockito.anyLong());
            JSONObject jObj = JSONFactoryUtil.createJSONObject(
                    json.getValue());
            Assert.assertEquals("fg-app-name", jObj.getString("name"));
            Assert.assertEquals(
                    "fg-app-description", jObj.getString("description"));
            Assert.assertEquals("fg-app-outcome", jObj.getString("outcome"));
            Assert.assertEquals("true", jObj.getString("enabled"));
            Assert.assertEquals(infras[0], jObj.getJSONArray("infrastructures").
                    getString(0));
            Assert.assertEquals(
                    "fg-app-parameter-name-1", jObj.getJSONArray("parameters").
                    getJSONObject(0).get("name"));
            Assert.assertEquals(
                    "fg-app-parameter-description-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("description"));
            Assert.assertEquals(
                    "fg-app-parameter-value-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("value"));
            ArgumentCaptor<Object> obInfra = ArgumentCaptor.forClass(
                    Object.class);
            Mockito.verify(actionRequest).setAttribute(
                    Mockito.eq(FGServerConstants.INFRASTRUCTURE_COLLECTION),
                    obInfra.capture());
            Assert.assertEquals(infraMap,
                    (Map<String, String>) obInfra.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
