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

package it.infn.ct.indigo.futuregateway.portlet.configuration.icon;

import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon
    .BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon
    .PortletConfigurationIcon;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;

/**
 * Basic configuration of the FG Admin portlet.
 */
@Component(
        immediate = true,
        property = {"javax.portlet.name="
                + FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN},
        service = PortletConfigurationIcon.class
)
public class SetServiceConfigurationIcon extends
        BaseJSPPortletConfigurationIcon {

    @Override
    public final  boolean isShow(final PortletRequest portletRequest) {
        return true;
    }

    @Override
    public final String getJspPath() {
        return "/configuration/icon/update_server.jsp";
    }

    @Override
    public final String getMessage(final PortletRequest portletRequest) {
        ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
            "content.Language", getLocale(portletRequest), getClass());

        return LanguageUtil.get(resourceBundle, "updpate-fg-server");
    }

    @Override
    public final String getURL(
        final PortletRequest portletRequest,
        final PortletResponse portletResponse) {

        return "javascript:;";
    }

    @Override
    public final double getWeight() {
        return FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_WIGHT;
    }

    @Override
    @Reference(
        target = "(osgi.web.symbolicname=future.gateway.admin.portlet)",
        unbind = "-"
    )
    public final void setServletContext(final ServletContext servletContext) {
        super.setServletContext(servletContext);
    }
}
