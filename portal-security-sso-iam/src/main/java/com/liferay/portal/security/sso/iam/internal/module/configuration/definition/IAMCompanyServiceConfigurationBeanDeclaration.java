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

package com.liferay.portal.security.sso.iam.internal.module.
    configuration.definition;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.settings.definition.
    ConfigurationBeanDeclaration;
import com.liferay.portal.security.sso.iam.configuration.IAMConfiguration;

/**
 * @author Marco Fargetta
 */
@Component
public class IAMCompanyServiceConfigurationBeanDeclaration implements
        ConfigurationBeanDeclaration {

    @Override
    public final Class<?> getConfigurationBeanClass() {
        return IAMConfiguration.class;
    }

}
