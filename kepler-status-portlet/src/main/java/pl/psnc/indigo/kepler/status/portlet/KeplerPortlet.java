package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.iam.model.TokenInfo;
import com.liferay.portal.security.sso.iam.service.TokenServiceUtil;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.net.URI;

/**
 * A Liferay portlet which asks for FutureGateway's application name and
 * lists all tasks with corresponding runtime data of the given application
 * type.
 */
// @formatter:off
@Component(
        immediate = true,
        property = {
            "com.liferay.portlet.css-class-wrapper=portlet-greeter",
            "com.liferay.portlet.display-category=category.sample",
            "com.liferay.portlet.instanceable=false",
            "javax.portlet.name=KeplerStatusPortlet",
            "javax.portlet.display-name=Kepler Status Portlet",
            "javax.portlet.init-param.template-path=/",
            "javax.portlet.init-param.view-template=/view.jsp",
            "javax.portlet.security-role-ref=power-user,user",
        },
        service = Portlet.class)
// @formatter:on
public class KeplerPortlet extends MVCPortlet {
    /**
     * Injected dependency to get portal-wise configuration about FutureGateway.
     */
    private FGServerManager fgServerManager;

    /**
     * Setter used for dependency injection by OSGi.
     *
     * @param manager An instance holding portal-wise configuration
     *                options about FutureGateway.
     */
    @Reference
    public final void setFgServerManager(final FGServerManager manager) {
        fgServerManager = manager;
    }

    @Override
    public final void doView(final RenderRequest renderRequest,
                             final RenderResponse renderResponse)
            throws IOException, PortletException {
        try {
            final TokenInfo tokenInfo =
                    KeplerPortlet.getTokenInfo(renderRequest);
            if ((tokenInfo == null) || (tokenInfo.getSubject() == null)) {
                renderRequest
                        .setAttribute("error", "You must log in using IAM");
                return;
            }

            final ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                    .getAttribute(WebKeys.THEME_DISPLAY);
            final URI futureGatewayUri = URI.create(
                    fgServerManager.getFGUrl(themeDisplay.getCompanyId()));
            renderRequest.setAttribute("token", tokenInfo.getToken());
            renderRequest.setAttribute("futuregatewayUri", futureGatewayUri);
        } catch (final PortalException e) {
            throw new PortletException(
                    "Failed to prepare variables for Kepler portlet", e);
        } finally {
            super.doView(renderRequest, renderResponse);

        }
    }

    /**
     * Gets token info concerning the current user of the portlet.
     *
     * @param renderRequest A request which allows to recognize user of the
     *                      portlet.
     * @return A {@link TokenInfo} object which hold the IAM token itself and
     * some metadata around it.
     * @throws PortalException If a REST call to IAM token service fails.
     */
    private static TokenInfo getTokenInfo(final PortletRequest renderRequest)
            throws PortalException {
        final ServiceContext serviceContext =
                ServiceContextFactory.getInstance(renderRequest);
        return TokenServiceUtil.getToken(serviceContext);
    }
}
