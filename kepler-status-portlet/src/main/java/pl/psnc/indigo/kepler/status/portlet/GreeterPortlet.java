package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import org.osgi.service.component.annotations.Component;

import javax.portlet.Portlet;

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
public class GreeterPortlet extends MVCPortlet {
}
