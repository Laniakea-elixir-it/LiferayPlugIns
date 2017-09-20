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

package com.liferay.portal.security.sso.iam.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author Marco Fargetta
 */
public final class IAMConstants {
    /**
     * Service name in OSGi.
     */
    public static final String SERVICE_NAME =
            "com.liferay.portal.security.sso.iam";
    /**
     * Login scopes for the OpenIDConnect.
     */
    public static final List<String> SCOPES_LOGIN = Arrays.asList("openid",
            "profile", "email");

    /**
     * Refresh time of the end points and JWT keys refresh.
     *
     * Default 4 hours.
     */
    public static final int KEYS_REFRESH = 1000 * 60 * 60 * 4;

    /**
     * Default constructor to avoid instances.
     */
    private IAMConstants() {
    }
}
