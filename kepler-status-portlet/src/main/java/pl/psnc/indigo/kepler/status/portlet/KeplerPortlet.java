package pl.psnc.indigo.kepler.status.portlet;

import aQute.bnd.annotation.metatype.Configurable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.security.sso.iam.model.TokenInfo;
import com.liferay.portal.security.sso.iam.service.TokenServiceUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

// @formatter:off
@Component(
        immediate = true,
        configurationPid = "pl.psnc.indigo.kepler.status.portlet.KeplerPortletConfiguration",
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
    private static final Logger LOG =
            LoggerFactory.getLogger(KeplerPortlet.class);

    private volatile KeplerPortletConfiguration configuration;

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

            final URI futureGatewayUri = configuration.futureGatewayUri();
            if (futureGatewayUri == null) {
                renderRequest.setAttribute("error",
                                           "You must configure FutureGateway " +
                                           "URI");
                return;
            }

            renderRequest.setAttribute("token", tokenInfo.getToken());
            renderRequest.setAttribute("futuregatewayUri", futureGatewayUri);
        } catch (final PortalException e) {
            throw new PortletException(
                    "Failed to prepare variables for Kepler portlet", e);
        } finally {
            super.doView(renderRequest, renderResponse);

        }
    }

    private static TokenInfo getTokenInfo(final PortletRequest renderRequest)
            throws PortalException {
        final ServiceContext serviceContext =
                ServiceContextFactory.getInstance(renderRequest);
        return TokenServiceUtil.getToken(serviceContext);
    }

    @Activate
    @Modified
    protected final void activate(final Map<String, Object> properties) {
        configuration = Configurable
                .createConfigurable(KeplerPortletConfiguration.class,
                                    properties);
    }
}
