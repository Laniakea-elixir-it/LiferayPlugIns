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

package it.infn.ct.indigo.futuregateway.server;

/**
 * Static elements defined in the FutureGateway.
 *
 */
public final class FGServerConstants {
    /**
     * Blocks public instance of this class.
     */
    private FGServerConstants() {
    }

    /**
     * Name of the collection containing the applications.
     */
    public static final String INPUT_PATH = "input";

    /**
     * Name of the collection containing the applications.
     */
    public static final String APPLICATION_COLLECTION = "applications";

    /**
     * Name of the collection containing the applications.
     */
    public static final String INFRASTRUCTURE_COLLECTION = "infrastructures";

    /**
     * Name of the attribute providing the id.
     */
    public static final String ATTRIBUTE_ID = "id";

    /**
     * Name of the attribute providing the name.
     */
    public static final String ATTRIBUTE_NAME = "name";

    /**
     * Name of the attribute providing if it is enabled.
     */
    public static final String ATTRIBUTE_ENABLED = "enabled";
}
