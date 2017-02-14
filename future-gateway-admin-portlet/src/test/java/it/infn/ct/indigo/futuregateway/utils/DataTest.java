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
package it.infn.ct.indigo.futuregateway.utils;

public class DataTest {
    public static final String INFRAS = "{\"infrastructures\":[{"
            + "\"id\": \"123\",\"name\": \"infra1\",\"date\": "
            + "\"2015-08-11T12:12:00.421Z\",\"enabled\": true,\"virtual\": "
            + "false,\"_links\": [{\"rel\": \"self\",\"href\": "
            + "\"/v1.0/infrastructures/123\"}]},{\"id\": \"456\","
            + "\"name\": \"infra2\",\"date\": \"2015-08-14T16:37:34.563Z\","
            + "\"enabled\": false,\"virtual\": true,\"_links\": [{"
            + "\"rel\": \"self\",\"href\": \"/v1.0/infrastructures/456\""
            + "}]}],\"_links\": [{\"rel\": \"self\",\"href\": "
            + "\"/v1.0/infrastructures?page=1\",},{\"rel\": \"next\","
            + "\"href\": \"/v1.0/infrastructures?page=2\",}]}";
    
    public static final String[] INFRAS_ID = {"123", "456"};
}
