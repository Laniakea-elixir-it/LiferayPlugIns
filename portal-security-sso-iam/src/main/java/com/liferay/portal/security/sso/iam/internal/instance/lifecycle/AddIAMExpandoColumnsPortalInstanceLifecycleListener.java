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

package com.liferay.portal.security.sso.iam.internal.instance.lifecycle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.instance.lifecycle.
    BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Marco Fargetta
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddIAMExpandoColumnsPortalInstanceLifecycleListener extends
        BasePortalInstanceLifecycleListener {

    @Override
    public final void portalInstanceRegistered(final Company company)
            throws Exception {
        Long companyId = CompanyThreadLocal.getCompanyId();

        try {
            CompanyThreadLocal.setCompanyId(company.getCompanyId());

            long classNameId = classNameLocalService.getClassNameId(User.class
                    .getName());

            ExpandoTable expandoTable = expandoTableLocalService.fetchTable(
                    company.getCompanyId(), classNameId,
                    ExpandoTableConstants.DEFAULT_TABLE_NAME);

            if (expandoTable == null) {
                expandoTable = expandoTableLocalService.addTable(company
                        .getCompanyId(), classNameId,
                        ExpandoTableConstants.DEFAULT_TABLE_NAME);
            }

            UnicodeProperties properties = new UnicodeProperties();

            properties.setProperty(ExpandoColumnConstants.PROPERTY_HIDDEN,
                    "false");
            properties.setProperty("visible-with-update-permission", "false");
            properties.setProperty(ExpandoColumnConstants.INDEX_TYPE, Integer
                    .toString(ExpandoColumnConstants.INDEX_TYPE_TEXT));

            addExpandoColumn(expandoTable, "iamUserID", properties);
            addExpandoColumn(expandoTable, "iamAccessToken", properties);
            addExpandoColumn(expandoTable, "iamRefreshToken", properties);
        } finally {
            CompanyThreadLocal.setCompanyId(companyId);
        }
    }

    /**
     * Adds a new expando column to the expando table.
     * The added column will have the String type.
     *
     * @param expandoTable The expando table to modify
     * @param name The column name
     * @param properties The column properties
     * @throws Exception If the column cannot be added
     */
    protected final void addExpandoColumn(final ExpandoTable expandoTable,
            final String name, final UnicodeProperties properties)
                    throws Exception {

        ExpandoColumn expandoColumn = expandoColumnLocalService.getColumn(
                expandoTable.getTableId(), name);

        if (expandoColumn != null) {
            return;
        }

        expandoColumn = expandoColumnLocalService.addColumn(expandoTable
                .getTableId(), name, ExpandoColumnConstants.STRING);

        expandoColumnLocalService.updateTypeSettings(expandoColumn
                .getColumnId(), properties.toString());
    }

    /**
     * Sets the module life cycle.
     * @param moduleServiceLifecycle The module life cycle
     */
    @Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
    protected void setModuleServiceLifecycle(
            final ModuleServiceLifecycle moduleServiceLifecycle) {
    }

    /**
     * Reference to the class name service.
     */
    @Reference
    private ClassNameLocalService classNameLocalService;

    /**
     * Reference to the expando column service.
     */
    @Reference
    private ExpandoColumnLocalService expandoColumnLocalService;

    /**
     * Reference to the expando table service.
     */
    @Reference
    private ExpandoTableLocalService expandoTableLocalService;
}
