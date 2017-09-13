package it.infn.ct.indigo.customisableApp.portlet.action;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import it.infn.ct.indigo.customisableApp.portlet.converter.Converter;

/**
 * YAML resource management.
 */
@Component(
        immediate = true,
        property = {
                "javax.portlet.name=CustomisableApplication",
                "mvc.command.name=/yaml/convert"
                },
        service = MVCResourceCommand.class
)
public class CustomisableApplicationYAMLConverterMVCResourceCommand
        implements MVCResourceCommand {

    @Override
    public final boolean serveResource(
            final ResourceRequest resourceRequest,
            final ResourceResponse resourceResponse)
                    throws PortletException {
        String yaml = ParamUtil.getString(resourceRequest, "yamlFile", "");
        log.debug("Generating the json parameter file from the yaml: " + yaml);
        if (yaml.isEmpty()) {
            return true;
        }
        Converter conv = new Converter();
        try {
            resourceResponse.setContentType("application/json");
            resourceResponse.getWriter().append(conv.readYamlToJsonArray(yaml).toString());
        } catch (IOException e) {
            log.error("Impossible to send the json with OneData files "
                    + "back to the user.");
        }
        return false;
    }

    /**
     * The logger.
     */
    private Log log = LogFactoryUtil.getLog(
            CustomisableApplicationYAMLConverterMVCResourceCommand.class);
}
