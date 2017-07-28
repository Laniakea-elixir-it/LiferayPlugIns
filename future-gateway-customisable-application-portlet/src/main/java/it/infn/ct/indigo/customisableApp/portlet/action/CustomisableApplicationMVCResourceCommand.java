package it.infn.ct.indigo.customisableApp.portlet.action;

import java.io.IOException;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.iam.IAM;

import it.infn.ct.indigo.customisableApp.portlet.backends.OneData;
import it.infn.ct.indigo.customisableApp.portlet.backends.utils.OneDataElement;

/**
 * Main portlet class for the customisable application.
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=CustomisableApplication",
                "mvc.command.name=/oneData/resources"
                },
        service = MVCResourceCommand.class
)
public class CustomisableApplicationMVCResourceCommand implements MVCResourceCommand {

    @Override
    public boolean serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
            throws PortletException {
        String oneZone = ParamUtil.getString(resourceRequest, "oneZone", "");
        log.debug("Looking at onezone: " + oneZone);
        if(oneZone.isEmpty()) {
            return true;
        }
        String path = ParamUtil.getString(resourceRequest, "path", "#");

        ThemeDisplay theme = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
        OneData od;
        try {
            od = new OneData(oneZone, path, iam.getUserToken(theme.getUserId()));
        } catch (Exception e1) {
            log.error("Impossible to get a token for the user: " + theme.getUserId());
            return true;
        }
        List<OneDataElement> folder = od.getElementsInFolder();
        JSONArray jFiles = JSONFactoryUtil.createJSONArray();
        for(OneDataElement ode: folder) {
            JSONObject elem = JSONFactoryUtil.createJSONObject();
            elem.put("id", path + ode.getId());
            elem.put("text", ode.getName());
            if(ode.isFolder()) {
                elem.put("children", true);
            }
            jFiles.put(elem);
        }
        
        try {
            resourceResponse.setContentType("application/json");
            resourceResponse.getWriter().append(jFiles.toJSONString());
        } catch (IOException e) {
            log.error("Impossible to send the json with OneData files back to the user.");
        }
        return false;
    }

    /**
     * Sets the iam component.
     *
     * @param iamComp The iam component
     */
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
    }


    /**
     * The IAM service.
     */
    private IAM iam;

    /**
     * The logger.
     */
    private Log log = LogFactoryUtil.getLog(CustomisableApplicationMVCResourceCommand.class);
}
