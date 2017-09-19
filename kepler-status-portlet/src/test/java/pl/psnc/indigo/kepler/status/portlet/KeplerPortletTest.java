package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.iam.model.TokenInfo;
import com.liferay.portal.security.sso.iam.service.TokenServiceUtil;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.net.URI;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class KeplerPortletTest {
    @PrepareForTest(ServiceContextFactory.class)
    @Test(expected = PortletException.class)
    public final void getTokenInfoThrowsException() throws Exception {
        PowerMockito.mockStatic(ServiceContextFactory.class);

        final KeplerPortlet keplerPortlet = mock(KeplerPortlet.class);

        BDDMockito.given(ServiceContextFactory
                                 .getInstance(any(RenderRequest.class)))
                  .willThrow(PortalException.class);

        keplerPortlet
                .doView(mock(RenderRequest.class), mock(RenderResponse.class));
    }

    @PrepareForTest({ServiceContextFactory.class, TokenServiceUtil.class,
                     ServiceTrackerFactory.class})
    @Test
    public final void getTokenInfoReturnsNull() throws Exception {
        PowerMockito.mockStatic(ServiceContextFactory.class);
        PowerMockito.mockStatic(ServiceTrackerFactory.class);
        PowerMockito.mockStatic(TokenServiceUtil.class);

        final ServiceContext serviceContext = mock(ServiceContext.class);
        final KeplerPortlet keplerPortlet = mock(KeplerPortlet.class);
        final RenderRequest renderRequest = mock(RenderRequest.class);
        final RenderResponse renderResponse = mock(RenderResponse.class);

        BDDMockito.given(ServiceContextFactory
                                 .getInstance(any(RenderRequest.class)))
                  .willReturn(serviceContext);
        BDDMockito.given(TokenServiceUtil.getToken(serviceContext))
                  .willReturn(null);

        keplerPortlet.doView(renderRequest, renderResponse);
        verify(renderRequest)
                .setAttribute("error", "You must log in using IAM");
    }

    @PrepareForTest({ServiceContextFactory.class, TokenServiceUtil.class,
                     ServiceTrackerFactory.class})
    @Test
    public final void getTokenInfoReturnsNullSubject() throws Exception {
        PowerMockito.mockStatic(ServiceContextFactory.class);
        PowerMockito.mockStatic(ServiceTrackerFactory.class);
        PowerMockito.mockStatic(TokenServiceUtil.class);

        final ServiceContext serviceContext = mock(ServiceContext.class);
        final KeplerPortlet keplerPortlet = mock(KeplerPortlet.class);
        final RenderRequest renderRequest = mock(RenderRequest.class);
        final RenderResponse renderResponse = mock(RenderResponse.class);

        final TokenInfo tokenInfo = new TokenInfo();

        BDDMockito.given(ServiceContextFactory
                                 .getInstance(any(RenderRequest.class)))
                  .willReturn(serviceContext);
        BDDMockito.given(TokenServiceUtil.getToken(serviceContext))
                  .willReturn(tokenInfo);

        keplerPortlet.doView(renderRequest, renderResponse);
        verify(renderRequest)
                .setAttribute("error", "You must log in using IAM");
    }

    @PrepareForTest({ServiceContextFactory.class, TokenServiceUtil.class,
                     ServiceTrackerFactory.class, FGServerManager.class})
    @Test
    public final void doView() throws Exception {
        final ServiceContext serviceContext =
                KeplerPortletTest.mockServiceContext();
        final TokenInfo tokenInfo =
                KeplerPortletTest.mockTokenInfo(serviceContext);

        final ThemeDisplay themeDisplay = mock(ThemeDisplay.class);
        when(themeDisplay.getCompanyId()).thenReturn(0L);

        final RenderRequest renderRequest = mock(RenderRequest.class);
        when(renderRequest.getAttribute(eq(WebKeys.THEME_DISPLAY)))
                .thenReturn(themeDisplay);

        final FGServerManager fgServerManager =
                PowerMockito.mock(FGServerManager.class);
        PowerMockito.when(fgServerManager.getFGUrl(anyLong()))
                    .thenReturn("http://testing");

        final KeplerPortlet keplerPortlet = mock(KeplerPortlet.class);
        keplerPortlet.setFgServerManager(fgServerManager);
        keplerPortlet.doView(renderRequest, mock(RenderResponse.class));
        verify(renderRequest).setAttribute("token", tokenInfo.getToken());
        verify(renderRequest)
                .setAttribute("futuregatewayUri", URI.create("http://testing"));
    }

    private static TokenInfo mockTokenInfo(final ServiceContext serviceContext)
            throws PortalException {
        final TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setSubject("");
        tokenInfo.setToken("TESTING");

        PowerMockito.mockStatic(ServiceTrackerFactory.class);
        PowerMockito.mockStatic(TokenServiceUtil.class);
        PowerMockito.when(TokenServiceUtil.getToken(serviceContext))
                    .thenReturn(tokenInfo);

        return tokenInfo;
    }

    private static ServiceContext mockServiceContext() throws PortalException {
        final ServiceContext serviceContext = mock(ServiceContext.class);

        PowerMockito.mockStatic(ServiceContextFactory.class);
        PowerMockito.when(ServiceContextFactory
                                  .getInstance(any(RenderRequest.class)))
                    .thenReturn(serviceContext);

        return serviceContext;
    }
}
