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

package com.liferay.portal.security.sso.iam.internal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portlet.expando.service.impl.ExpandoValueLocalServiceImpl;

/**
 * @author Marco Fargetta
 */
@RunWith(MockitoJUnitRunner.class)
public class IAMImplTest {

    /**
     * ExpandoValue mock.
     */
    @Mock
    private ExpandoValueLocalServiceImpl expandoValueLocalService;

    /**
     * IAMImpl tested object.
     */
    @InjectMocks
    private IAMImpl iam;


    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#addOrUpdateUser(
     * javax.servlet.http.HttpSession, long, java.lang.String,
     * java.lang.String, java.util.List)}
     * .
     */
//    @Test
//    public final void testAddOrUpdateUser() {
//        fail("Not yet implemented"); // TODO
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#getLoginRedirect(
     * long, java.lang.String, java.util.List, boolean)}
     * .
     */
//    @Test
//    public final void testGetLoginRedirect() {
//        fail("Not yet implemented"); // TODO
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#isEnabled(long)}
     * .
     */
//    @Test
//    public final void testIsEnabled() {
//        doReturn(new
//        IAMConfigurationUtil(true)).when(iam).getIAMConfiguration(anyInt());
//        assertTrue("It should be enabled", iam.isEnabled(1));
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#getUserToken(long)}
     * .
     */
//    @Test
//    public final void testGetUserToken() {
//        fail("Not yet implemented"); // TODO
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#hasRefreshToken(
     * com.liferay.portal.kernel.model.User)}
     * .
     */
    @Test
    public final void testHasRefreshToken() {
        ExpandoValue ev = new ExpandoValueMock();
        ev.setData("aToken");
        when(expandoValueLocalService.getValue(
                anyLong(), anyString(), anyString(), anyString(), anyLong()))
                .thenReturn(ev);
        assertTrue(iam.hasRefreshToken(new UserMock()));
    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#getTokenUser(
     * long, java.lang.String)}
     * .
     */
//    @Test
//    public final void testGetTokenUser() {
//        fail("Not yet implemented"); // TODO
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#getTokenSubject(
     * long, java.lang.String)}
     * .
     */
//    @Test
//    public final void testGetTokenSubjectLongString() {
//        fail("Not yet implemented"); // TODO
//    }

    /**
     * Test method for
     * {@link
     * com.liferay.portal.security.sso.iam.internal.IAMImpl#getTokenSubject(
     * long, long)}
     * .
     */
//    @Test
//    public final void testGetTokenSubjectLongLong() {
//        fail("Not yet implemented"); // TODO
//    }
}
