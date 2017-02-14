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

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PortalUtil;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;

/**
 * Implementation of the add infrastructure in FG server render command.
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name="
                        + FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN,
                "mvc.command.name=/fg/addInfra"
        },
        service = MVCRenderCommand.class
)
public class FGAddInfraMVCRenderCommand implements MVCRenderCommand {

    @Override
    public final String render(
            final RenderRequest renderRequest,
            final RenderResponse renderResponse) throws PortletException {

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
        return "/add_infrastructure.jsp";
    }
}
