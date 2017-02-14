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

package com.liferay.portal.security.sso.iam.service.permission;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.
    BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;

/**
 * Checks if the user has the right to access the iam remote methods.
 *
 * @author Marco Fargetta
 */
@Component(
        immediate = true,
        property = { "resource.name="
                + TokenAccessPermissionChecker.RESOURCE_NAME },
        service = ResourcePermissionChecker.class)
public class TokenAccessPermissionChecker extends
        BaseResourcePermissionChecker {

    /**
     * Check the permission.
     *
     * @param permissionChecker System checker
     * @param user User performing the request
     * @param groupId Context group
     * @param actionId Action to perform
     * @throws PortalException If the check control fail
     */
    public static void check(final PermissionChecker permissionChecker,
            final User user, final long groupId, final String actionId)
                    throws PortalException {
        check(permissionChecker, user.getUserId(), groupId, actionId);
    }

    /**
     * Check the permission.
     *
     * @param permissionChecker System checker
     * @param userId User performing the request
     * @param groupId Context group
     * @param actionId Action to perform
     * @throws PortalException If the check control fail
     */
    public static void check(final PermissionChecker permissionChecker,
            final long userId, final long groupId, final String actionId)
                    throws PortalException {
        if (!hasPermission(permissionChecker, userId, groupId, actionId)) {
            throw new PrincipalException.MustHavePermission(userId, actionId);
        }
    }

    /**
     * Verify if the permission is granted.
     *
     * @param permissionChecker System checker
     * @param groupId Context group
     * @param actionId Action to perform
     * @return True if it is granted, false otherwise
     */
    public static boolean hasPermission(
            final PermissionChecker permissionChecker, final long groupId,
            final String actionId) {
        return hasPermission(permissionChecker, -1, groupId, actionId);
    }

    /**
     * Verify if the permission is granted.
     *
     * @param permissionChecker System checker
     * @param primaryKey Key of the resource. This is not used because the
     *  token is not a real resource but is managed as a resource to generate
     *  the remote API
     * @param groupId Context group
     * @param actionId Action to perform
     * @return True if it is granted, false otherwise
     * @return
     */
    public static boolean hasPermission(
            final PermissionChecker permissionChecker, final long primaryKey,
            final long groupId, final String actionId) {
        if (permissionChecker.isCompanyAdmin() || permissionChecker
                .isOmniadmin()) {
            return true;
        }
        if (permissionChecker.getUserId() == primaryKey) {
            return true;
        }
        return contains(permissionChecker, RESOURCE_NAME, groupId, actionId);
    }

    @Override
    public final Boolean checkResource(
            final PermissionChecker permissionChecker, final long classPK,
            final String actionId) {
        return hasPermission(permissionChecker, classPK, actionId);
    }

    /**
     * Name of the resource requiring the permission.
     */
    public static final String RESOURCE_NAME =
            "com.liferay.portal.security.sso.iam";
}
