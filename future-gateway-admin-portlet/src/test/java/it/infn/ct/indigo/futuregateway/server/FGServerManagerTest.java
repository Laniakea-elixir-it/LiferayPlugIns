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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.liferay.expando.kernel.service.ExpandoValueLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.sso.iam.IAM;

import it.infn.ct.indigo.futuregateway.utils.DataTest;

/**
 * FGServerManager tests collection.
 */
@RunWith(MockitoJUnitRunner.class)
public class FGServerManagerTest {

    @Mock
    private HttpURLConnection connection;

    @Mock
    private IAM iam;

    @Mock
    private ExpandoValueLocalService expandoValueService;

    @Spy
    private FGServerManager fgsm;

    /**
     * Test.
     */
    @Test
    public void testGetInfrastructures() {
        Random rand = new Random();
        int companyId = rand.nextInt();
        int userId = rand.nextInt();
        try {
            Mockito.when(connection.getInputStream()).thenReturn(
                    new ByteArrayInputStream(DataTest.INFRAS.getBytes()));
            Mockito.when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
            Mockito.when(iam.getUserToken(Mockito.anyLong())).thenReturn("");
            fgsm.setIam(iam);
//            Mockito.when(expandoValueService.getData(companyId,
//                    FGServerManager.class.getName(), "FG", "fgUrl", 0, "")).thenReturn("");
            fgsm.setExpandoValueLocalService(expandoValueService);
            Mockito.doReturn(connection).when(fgsm).getFGConnection(
                            Mockito.anyLong(),
                            Mockito.anyString(),
                            Mockito.isNull(String.class),
                            Mockito.anyString(),
                            Mockito.anyString(),
                            Mockito.anyString());
            Map<String,String> infras = fgsm.getInfrastructures(companyId, userId);
            Assert.assertArrayEquals(DataTest.INFRAS_ID, infras.keySet().toArray());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
//            try {
//                Mockito.verify(fgsm).getFGConnection(
//                        Mockito.anyLong(),
//                        Mockito.anyString(),
//                        Mockito.isNull(String.class),
//                        Mockito.anyString(),
//                        Mockito.anyString(),
//                        Mockito.anyString());
//            } catch (PortalException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
        }
    }

}
