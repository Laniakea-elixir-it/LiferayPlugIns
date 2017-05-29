package it.infn.ct.indigo.customisableApp.portlet.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import it.infn.ct.indigo.futuregateway.server.FGServerManager;

@Component(
        property = {
                "javax.portlet.name=CustomisableApplication"
        },
        service = ConfigurationAction.class
)
public class CustomisableApplicationAction extends DefaultConfigurationAction{

    @Override
    public void include(PortletConfig portletConfig, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        try {
            request.setAttribute("FGEndPoint", fgServerManager.getFGUrl(themeDisplay.getCompanyId()));
        } catch(PortalException pe) {
            request.setAttribute("FGEndPoint", "This is it");
        }
        super.include(portletConfig, request, response);
    }

    
    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
            throws Exception {

        setPreference(actionRequest, "applicationId", ParamUtil.getString(actionRequest, "applicationId"));
        setPreference(actionRequest, "jsonApp", ParamUtil.getString(actionRequest, "jsonApp"));
        super.processAction(portletConfig, actionRequest, actionResponse);
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
