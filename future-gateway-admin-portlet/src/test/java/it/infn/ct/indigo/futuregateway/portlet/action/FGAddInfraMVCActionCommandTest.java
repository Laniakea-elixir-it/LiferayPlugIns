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

import java.io.IOException;

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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * FGAddInfraMVCActionCommand tests collection.
 */
@RunWith(MockitoJUnitRunner.class)
public class FGAddInfraMVCActionCommandTest {

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
        Mockito.when(actionRequest.getParameter("fg-infra-name")).
            thenReturn("fg-infra-name");
        Mockito.when(actionRequest.getParameter("fg-infra-description")).
            thenReturn("fg-infra-description");
        Mockito.when(actionRequest.getParameter("fg-infra-enabled")).
            thenReturn("true");
        String[] paramNames = {"fg-infra-parameter-name-1"};
        Mockito.when(
                httpRequest.getParameterValues("fg-infra-parameter-name")).
            thenReturn(paramNames);
        String[] paramValues = {"fg-infra-parameter-value-1"};
        Mockito.when(
                httpRequest.getParameterValues("fg-infra-parameter-value")).
            thenReturn(paramValues);
        String[] paramDescriptions = {"fg-infra-parameter-description-1"};
        Mockito.when(
            httpRequest.getParameterValues(
                    "fg-infra-parameter-description")).
            thenReturn(paramDescriptions);
        try {
            Mockito.when(
                fgSManager.addResource(
                        Mockito.anyLong(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyLong())).
                thenReturn("");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        FGAddInfraMVCActionCommand fgAIMAC = new FGAddInfraMVCActionCommand();
        fgAIMAC.setFGServerManeger(fgSManager);
        try {
            fgAIMAC.doProcessAction(actionRequest, actionResponse);
            ArgumentCaptor<String> json = ArgumentCaptor.forClass(
                    String.class);
            Mockito.verify(fgSManager).addResource(
                    Mockito.anyLong(), Mockito.anyString(),
                    json.capture(), Mockito.anyLong());
            JSONObject jObj = JSONFactoryUtil.createJSONObject(
                    json.getValue());
            Assert.assertEquals("fg-infra-name", jObj.getString("name"));
            Assert.assertEquals(
                    "fg-infra-description", jObj.getString("description"));
            Assert.assertEquals("true", jObj.getString("enabled"));
            Assert.assertEquals(
                    "fg-infra-parameter-name-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("name"));
            Assert.assertEquals(
                    "fg-infra-parameter-description-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("description"));
            Assert.assertEquals(
                    "fg-infra-parameter-value-1",
                    jObj.getJSONArray("parameters").
                    getJSONObject(0).get("value"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Test the process action with a file error.
     */
    @Test
    public final void testDoProcessActionWithIOError() {
        Mockito.when(actionRequest.getParameter("fg-infra-name")).
            thenReturn("fg-infra-name");
        Mockito.when(actionRequest.getParameter("fg-infra-description")).
            thenReturn("fg-infra-description");
        Mockito.when(actionRequest.getParameter("fg-infra-enabled")).
            thenReturn("true");
        String[] paramNames = {"fg-infra-parameter-name-1"};
        Mockito.when(
                httpRequest.getParameterValues("fg-infra-parameter-name")).
            thenReturn(paramNames);
        String[] paramValues = {"fg-infra-parameter-value-1"};
        Mockito.when(
                httpRequest.getParameterValues("fg-infra-parameter-value")).
            thenReturn(paramValues);
        String[] paramDescriptions = {"fg-infra-parameter-description-1"};
        Mockito.when(
            httpRequest.getParameterValues(
                    "fg-infra-parameter-description")).
            thenReturn(paramDescriptions);
        try {
            Mockito.when(
                fgSManager.addResource(
                        Mockito.anyLong(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyLong())).
                thenThrow(new IOException());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        FGAddInfraMVCActionCommand fgAIMAC = new FGAddInfraMVCActionCommand();
        fgAIMAC.setFGServerManeger(fgSManager);
        try {
            fgAIMAC.doProcessAction(actionRequest, actionResponse);
            ArgumentCaptor<String> rpName = ArgumentCaptor.forClass(
                    String.class);
            ArgumentCaptor<String> rpValue = ArgumentCaptor.forClass(
                    String.class);
            Mockito.verify(actionResponse).setRenderParameter(
                    rpName.capture(), rpValue.capture());
            Assert.assertEquals("mvcPath", rpName.getValue());
            Assert.assertEquals("/add_infrastructure.jsp", rpValue.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
