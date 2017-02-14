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
package com.liferay.portal.settings.authentication.iam.web.internal.servlet.
    taglib;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseJSPDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;

/**
 * @author Marco Fargetta
 */
@Component(
        immediate = true,
        property = { "portal.settings.authentication.tabs.name=iam" },
        service = DynamicInclude.class)
public class PortalSettingsIAMAuthenticationDynamicInclude extends
        BaseJSPDynamicInclude {

    @Override
    protected final String getJspPath() {
        return "/com.liferay.portal.settings.web/iam.jsp";
    }

    @Override
    protected final Log getLog() {
        return log;
    }

    @Override
    @Reference(
            target =
                "(osgi.web.symbolicname=com.liferay.portal.settings."
                        + "authentication.iam.web)",
            unbind = "-")
    protected final void setServletContext(
            final ServletContext servletContext) {
        super.setServletContext(servletContext);
    }

    /**
     * The logger.
     */
    private final Log log = LogFactoryUtil.getLog(
            PortalSettingsIAMAuthenticationDynamicInclude.class);
}
