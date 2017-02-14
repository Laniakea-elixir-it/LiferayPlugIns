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

package it.infn.ct.indigo.futuregateway.application.list;

import it.infn.ct.indigo.futuregateway.constants.FutureGatewayAdminPortletKeys;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Includes the portlet in the control panel inside the categories applications.
 */
@Component(
    immediate = true,
    property = {
        "panel.app.order:Integer=10",
        "panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_APPS
    },
    service = PanelApp.class
)
public class FutureGatewayAdminPanelApp extends BasePanelApp {

    @Override
    public final String getPortletId() {
        return FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN;
    }

    @Override
    @Reference(
            target = "(javax.portlet.name="
                    + FutureGatewayAdminPortletKeys.FUTURE_GATEWAY_ADMIN + ")",
            unbind = "-"
    )
    public final void setPortlet(final Portlet portlet) {
        super.setPortlet(portlet);
    }
}
