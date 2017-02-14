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
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;
import it.infn.ct.indigo.futuregateway.server.FGServerConstants;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * Implementation of the add application in FG server render command.
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name="
                        + FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN,
                "mvc.command.name=/fg/addApp"
        },
        service = MVCRenderCommand.class
)
public class FGAddAppMVCRenderCommand implements MVCRenderCommand {

    @Override
    public final String render(
            final RenderRequest renderRequest,
            final RenderResponse renderResponse) throws PortletException {

        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(
                WebKeys.THEME_DISPLAY);
        try {
            Map<String, String> infras = fgServerManager.getInfrastructures(
                    themeDisplay.getCompanyId(), themeDisplay.getUserId());
            if (infras.isEmpty()) {
                return "/application-no-infras.jsp";
            }
            renderRequest.setAttribute(
                    FGServerConstants.INFRASTRUCTURE_COLLECTION,
                        infras
                    );
        } catch (Exception e) {
            if (e instanceof IOException) {
                SessionErrors.add(renderRequest, e.getClass());
                return "/error.jsp";
            } else {
                throw new PortletException(e);
            }
        }
        return "/add_application.jsp";
    }

    /**
     * Sets the FG Server manager.
     * This is used to get information of the service and for interactions.
     *
     * @param fgServerManeger The FG Server manager
     */
    @Reference(unbind = "-")
    protected final void setFGServerManeger(
            final FGServerManager fgServerManeger) {
        this.fgServerManager = fgServerManeger;
    }

    /**
     * The logger.
     */
    private Log log = LogFactoryUtil.getLog(FGAddAppMVCRenderCommand.class);

    /**
     * The reference to the FG Server manager.
     */
    private FGServerManager fgServerManager;
}
