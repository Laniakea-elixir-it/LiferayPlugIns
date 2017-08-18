package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.security.sso.iam.model.TokenInfo;
import com.liferay.portal.security.sso.iam.service.TokenServiceUtil;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;

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
    private static final Log LOG = LogFactoryUtil.getLog(KeplerPortlet.class);

    @Override
    public final void doView(final RenderRequest renderRequest,
                             final RenderResponse renderResponse)
            throws IOException, PortletException {
        try {
            final TokenInfo tokenInfo =
                    KeplerPortlet.getTokenInfo(renderRequest);

            if ((tokenInfo != null) && (tokenInfo.getSubject() != null)) {
                renderRequest.setAttribute("isIamUser", true);
                renderRequest
                        .setAttribute("iamSubject", tokenInfo.getSubject());
            } else {
                renderRequest.setAttribute("isIamUser", false);
            }
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
}
