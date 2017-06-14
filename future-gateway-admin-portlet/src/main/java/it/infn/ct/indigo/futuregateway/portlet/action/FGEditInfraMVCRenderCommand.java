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

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;
import it.infn.ct.indigo.futuregateway.server.FGServerConstants;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * Implementation of the add infrastructure in FG server render command.
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name="
                        + FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN,
                "mvc.command.name=/fg/addInfra",
                "mvc.command.name=/fg/modifyInfra",
        },
        service = MVCRenderCommand.class
)
public class FGEditInfraMVCRenderCommand implements MVCRenderCommand {

    @Override
    public final String render(
            final RenderRequest renderRequest,
            final RenderResponse renderResponse) throws PortletException {
        String infraId;

        try {
            PortalUtil.getSelectedUser(renderRequest);
        } catch (Exception e) {
            if (e instanceof PrincipalException) {
                SessionErrors.add(renderRequest, e.getClass());
                return "/error.jsp";
            } else {
                throw new PortletException(e);
            }
        }
        infraId = ParamUtil.getString(
                renderRequest,
                FGServerConstants.VIEW_EDIT_RESOURCE_ID,
                null);
        if (infraId != null) {
            try {
                String infrastructure = fgServerManager.getResource(
                        PortalUtil.getCompanyId(renderRequest),
                        FGServerConstants.INFRASTRUCTURE_COLLECTION,
                        infraId, PortalUtil.getUserId(renderRequest));
                JSONObject infraJ = JSONFactoryUtil.createJSONObject(
                        infrastructure);
                renderRequest.setAttribute("infra_id",
                        infraJ.getString("id"));
                renderRequest.setAttribute("infra_name",
                        infraJ.getString("name"));
                renderRequest.setAttribute("infra_description",
                        infraJ.getString("description"));
                renderRequest.setAttribute("infra_enabled",
                        infraJ.getBoolean("enabled"));
                renderRequest.setAttribute("infra_virtual",
                        infraJ.getBoolean("virtual"));
                Map<String, String> paramValues = new HashMap<>();
                Map<String, String> paramDescs = new HashMap<>();
                JSONArray params = infraJ.getJSONArray("parameters");
                if (params != null) {
                    for (int i = 0; i < params.length(); i++) {
                        JSONObject par = params.getJSONObject(i);
                        paramValues.put(par.getString("name"),
                                par.getString("value"));
                        paramDescs.put(par.getString("name"),
                                par.getString("description"));
                    }
                }
                renderRequest.setAttribute("infra_parameters_values",
                        paramValues);
                renderRequest.setAttribute("infra_parameters_descriptions",
                        paramDescs);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                SessionErrors.add(renderRequest, FGServerManager.class);
            }
        }
        return "/add_infrastructure.jsp";
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
    private Log log = LogFactoryUtil.getLog(FGEditInfraMVCRenderCommand.class);

    /**
     * The reference to the FG Server manager.
     */
    private FGServerManager fgServerManager;
}
