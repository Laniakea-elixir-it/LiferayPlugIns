package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

// @formatter:off
@Component(
        immediate = true,
        property = {
            "javax.portlet.name=KeplerStatusPortlet",
            "mvc.command.name=greet"
        },
        service = MVCActionCommand.class)
// @formatter:on
public class KeplerActionCommand implements MVCActionCommand {
    @Override
    public final boolean processAction(final ActionRequest actionRequest,
                                       final ActionResponse actionResponse) {
        return true;
    }
}
