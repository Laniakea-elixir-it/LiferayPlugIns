package pl.psnc.indigo.kepler.status.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

// @formatter:off
@Component(
        immediate = true,
        property = {
            "javax.portlet.name=KeplerStatusPortlet",
            "mvc.command.name=greet"
        },
        service = MVCActionCommand.class)
// @formatter:on
public class GreeterActionCommand implements MVCActionCommand {
    private static final Log LOG =
            LogFactoryUtil.getLog(GreeterActionCommand.class);

    @Override
    public final boolean processAction(final ActionRequest actionRequest,
                                       final ActionResponse actionResponse) {
        GreeterActionCommand.handleActionCommand(actionRequest);
        return true;
    }

    private static void handleActionCommand(
            final PortletRequest actionRequest) {
        final String name =
                ParamUtil.get(actionRequest, "name", StringPool.BLANK);

        if (GreeterActionCommand.LOG.isInfoEnabled()) {
            GreeterActionCommand.LOG.info(String.format("Hello %s", name));
        }

        final String greetingMessage =
                String.format("Hello %s! Welcome to OSGi", name);
        actionRequest.setAttribute("GREETER_MESSAGE", greetingMessage);
        SessionMessages.add(actionRequest, "greetingMessage", greetingMessage);
    }
}
