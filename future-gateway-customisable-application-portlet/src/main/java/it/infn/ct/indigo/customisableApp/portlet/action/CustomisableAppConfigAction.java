package it.infn.ct.indigo.customisableApp.portlet.action;

import org.osgi.service.component.annotations.Component;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=CustomisableApplication"
        },
        service = ConfigurationAction.class
)
public class CustomisableAppConfigAction extends DefaultConfigurationAction{


}
