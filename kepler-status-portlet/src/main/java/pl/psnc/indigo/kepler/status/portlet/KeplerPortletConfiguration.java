package pl.psnc.indigo.kepler.status.portlet;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.net.URI;

// @formatter:off
@ObjectClassDefinition(
        pid = "pl.psnc.indigo.kepler.status.portlet.KeplerPortletConfiguration",
        name = "Kepler Status Portlet - Configuration")
// @formatter:on
public interface KeplerPortletConfiguration {
    @AttributeDefinition(defaultValue = "http://localhost/apis")
    URI futureGatewayUri();
}
