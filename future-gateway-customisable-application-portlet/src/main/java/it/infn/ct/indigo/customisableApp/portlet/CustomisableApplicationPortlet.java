package it.infn.ct.indigo.customisableApp.portlet;

import java.io.IOException;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.customisableApp.portlet.converter.Converter;
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
                "com.liferay.portlet.requires-namespaced-parameters=false",
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
            log.error("Impossible to get the FG end-point");
        }

        super.doView(renderRequest, renderResponse);
    }

    
    
    
    @Override
    public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws IOException, PortletException {
        super.serveResource(resourceRequest, resourceResponse);
        String resourceID = GetterUtil.getString(
                resourceRequest.getResourceID());

        log.debug("Server resource from portlet");
        if (resourceID.equals("/yaml/convert")) {

        String yaml = ParamUtil.getString(resourceRequest, "yamlFile", "");
        log.debug("Generating the json parameter file from the yaml: " + yaml);
        if (yaml.isEmpty()) {
//            return true;
            return;
        }
        Converter conv = new Converter();
        try {
            resourceResponse.setContentType("application/json");
            resourceResponse.getWriter().append(conv.readYamlToJsonArray(yaml).toString());
        } catch (IOException e) {
            log.error("Impossible to send the json with OneData files "
                    + "back to the user.");
        }
        }
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

    /**
     * The logger.
     */
    private Log log = LogFactoryUtil.getLog(
            CustomisableApplicationPortlet.class);
}
