package it.infn.ct.indigo.customisableApp.portlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.customisableApp.portlet.configuration.Config;
import it.infn.ct.indigo.futuregateway.server.FGServerManager;

/**
 * Main portlet class for the customisable application.
 */
@Component(
        immediate = true,
        property = {
                "com.liferay.portlet.display-category=INFN",
                "com.liferay.portlet.header-portlet-javascript=/js/fg-api.js",
                "com.liferay.portlet.instanceable=true",
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
    /**
     * Configuration.
     */
    private Config config = new Config();

    @Override
    public final void serveResource(final ResourceRequest resourceRequest,
            final ResourceResponse resourceResponse)
                    throws IOException, PortletException {
        try {
            String json = ParamUtil.getString(resourceRequest, "json");
            String path = ParamUtil.getString(resourceRequest, "path");
            String jarray = ParamUtil.getString(resourceRequest, "jarray");

            // create parameter.json file
            if (!json.equals("")) {
                JsonObject jsonObject = new Gson().fromJson(
                        json, JsonObject.class);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String out = gson.toJson(jsonObject);

                config.createParamFile(path, out);
            }

            // get application configuration: i.a. path to parameters.json
            if (!jarray.equals("")) {
                String ans = config.readJsonFile(jarray);
                PrintWriter writer = resourceResponse.getWriter();
                writer.write(ans);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        super.serveResource(resourceRequest, resourceResponse);
    }

    @Override
    public final void doView(final RenderRequest renderRequest,
            final RenderResponse renderResponse)
                    throws IOException, PortletException {
        ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

        try {
            renderRequest.setAttribute("FGEndPoint", fgServerManager.getFGUrl(themeDisplay.getCompanyId()));
        } catch(PortalException pe) {

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
