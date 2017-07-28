package it.infn.ct.indigo.customisableApp.portlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * Main portlet class for the customisable application.
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=INFN",
                "com.liferay.portlet.header-portlet-javascript=/js/fg-api.js",
                "com.liferay.portlet.header-portlet-css=/css/style.min.css",
                "com.liferay.portlet.instanceable=false",
                "javax.portlet.name=CustomisableApplication",
                "javax.portlet.display-name=Customisable application Portlet",
                "javax.portlet.init-param.template-path=/",
                "javax.portlet.init-param.config-template=/configuration.jsp",
                "javax.portlet.init-param.view-template=/view.jsp",
                "javax.portlet.resource-bundle=content.Language",
                "javax.portlet.security-role-ref=power-user,user"
                },
        service = Portlet.class
)
public class CustomisableApplicationPortlet extends MVCPortlet {


    @Override
    public final void doView(final RenderRequest renderRequest,
            final RenderResponse renderResponse)
                    throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(
                WebKeys.THEME_DISPLAY);

        try {
            renderRequest.setAttribute(
                    "FGEndPoint",
                    fgServerManager.getFGUrl(themeDisplay.getCompanyId()));
        } catch (PortalException pe) {

        }

        super.doView(renderRequest, renderResponse);
    }

    /**
     * Sets the FG Server manager.
     * This is used to get information of the service and for interactions.
     *
     * @param fgServerManeger The FG Server manager
     */
    @Reference(unbind = "-")
    protected final void setFGServerManeger(
            final FGServerManager fgServerManeger) {
        this.fgServerManager = fgServerManeger;
    }

    /**
     * The reference to the FG Server manager.
     */
    private FGServerManager fgServerManager;

}
