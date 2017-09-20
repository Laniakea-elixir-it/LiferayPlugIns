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

package com.liferay.portal.security.sso.iam.internal.util;

import java.util.Date;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.security.sso.iam.configuration.IAMConfiguration;
import com.liferay.portal.security.sso.iam.constants.IAMConstants;

/**
 * IAM endpoint generator.
 * Retrieves the iam endpoints and cache them for multiple use.
 */
public final class IAMEndPointsManager {

    /**
     * The IAM end point.
     */
    private static IAMEndPoints iep = null;

    /**
     * Time of last refresh.
     */
    private static Date lastUpdate = null;

    /**
     * Default constructor to avoid instances.
     */
    private IAMEndPointsManager() {
    }
    /**
     * Generating the iam or re-use an existing one.
     * @param iamConf IAM Configuration settings
     * @return The IAMEndPoints object
     * @throws ConfigurationException In case the configuration is not working
     */
    public static synchronized IAMEndPoints getIAMEndPointsInstance(
            final IAMConfiguration iamConf) throws ConfigurationException {
        if (iep == null
                || lastUpdate.before(new Date(
                        System.currentTimeMillis()
                        - IAMConstants.KEYS_REFRESH))) {
            iep = new IAMEndPoints(iamConf);
            lastUpdate = new Date();
        }
        return iep;
    }
}
