package com.liferay.login.authentication.iam.web.internal.servlet.filter;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.BaseFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.security.auth.AuthException;

import com.liferay.portal.security.sso.iam.IAM;
import com.liferay.portal.security.sso.iam.constants.IAMWebKeys;

/**
 * @author: Marco Tangaro
 *
 * This filter just redirect /c/portal/login to /c/portal/c/portal/iam_openidconnect?cmd=login
 * thus triggering INDIGO IAM authentication workflow.
 * getUser call is copied from IAMAutoLogin
 */

@Component(
immediate = true,
property = {
"servlet-context-name=",
"servlet-filter-name=IAMFilter",
"url-pattern=/c/portal/login"
},
 service = Filter.class
)
public class IAMFilter extends BaseFilter {

    @Override
    protected void processFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws Exception {

        log.debug("mtangaro custom filter IAMFilter");

        long companyId = PortalUtil.getCompanyId(request);

        if (!iam.isEnabled(companyId)) {
          return;
        }

        User user = getUser(request, companyId);

        if (user == null){
          log.debug("User is not logged-in. This trigger the filter");
          String loginURL = "/c/portal/iam_openidconnect?cmd=login";
          response.sendRedirect(loginURL);
        }

        super.processFilter(request,response,filterChain);

    }

    // This is copied from IAMAutoLogin
    protected final User getUser(final HttpServletRequest request,
            final long companyId)
            throws Exception {

        HttpSession session = request.getSession();

        String emailAddress = GetterUtil.getString(session.getAttribute(
                IAMWebKeys.IAM_USER_EMAIL_ADDRESS));

        if (Validator.isNotNull(emailAddress)) {
            session.removeAttribute(IAMWebKeys.IAM_USER_EMAIL_ADDRESS);

            return userLocalService.getUserByEmailAddress(companyId,
                    emailAddress);
        } else {
            String iamUserId = GetterUtil.getString((String) session
                    .getAttribute(IAMWebKeys.IAM_USER_ID));

            if (Validator.isNotNull(iamUserId)) {
                long classNameId = ClassNameLocalServiceUtil.getClassNameId(
                        User.class);
                List<ExpandoValue> values = ExpandoValueLocalServiceUtil
                        .getColumnValues(companyId, classNameId,
                                ExpandoTableConstants.DEFAULT_TABLE_NAME,
                                "iamUserID", iamUserId, -1, -1);
                if (values.size() > 1) {
                    throw new AuthException(
                            "Multiple user with the same IAM identifier");
                }
                if (values.size() == 1) {
                    return userLocalService.getUser(values.get(0)
                            .getClassPK());
                }
            }
        }

        return null;
    }

    // This is copied from IAMAutoLogin
    @Reference(unbind = "-")
    protected final void setIam(final IAM iamComp) {
        this.iam = iamComp;
    }

    // This is copied from IAMAutoLogin
    @Reference(unbind = "-")
    protected final void setUserLocalService(
            final UserLocalService userLocalServiceComp) {
        this.userLocalService = userLocalServiceComp;
    }

    // This is copied from IAMAutoLogin
    private IAM iam;

    // This is copied from IAMAutoLogin
    private UserLocalService userLocalService;

    @Override
    protected Log getLog() {
         return log;
    }

    private static final Log log = LogFactoryUtil.getLog(IAMFilter.class);

}
