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
package com.liferay.portal.security.sso.iam.datasource;

import javax.sql.DataSource;

/**
 * This implements a fake data source.
 * A fake datasource is used in conjunction with the service builder
 * to create models not persisted in Liferay DB but still providing the remote
 * interface.
 *
 * @author Marco Fargetta
 */
public final class FakeDataSource {
    /**
     * Return a DataSource without real connection to any DB.
     *
     * @return DataSource Always returns null.
     */
    public static DataSource getDataSource() {
        return null;
    }

    /**
     * Default constructor to avoid useless objects.
     */
    private FakeDataSource() {
    }
}
