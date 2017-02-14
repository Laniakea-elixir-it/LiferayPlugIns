/**
 * *********************************************************************
 * Copyright (c) 2016: Istituto Nazionale di Fisica Nucleare (INFN) -
 * INDIGO-DataCloud
 *
 * See http://www.infn.it and and http://www.consorzio-cometa.it for details on
 * the copyright holders.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **********************************************************************
 */

package com.liferay.portal.security.sso.iam.internal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.Phone;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.Team;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.Website;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.RemotePreference;

/**
 * Implement a User performing no actions.
 *
 * @author Marco Fargetta
 */
public final class UserMock implements User {

    @Override
    public Object clone() {
        return new UserMock();
    }

    @Override
    public long getPrimaryKey() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setPrimaryKey(final long primaryKey) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getMvccVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setMvccVersion(final long mvccVersion) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getUuid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUuid(final String uuid) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getUserId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setUserId(final long userId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getUserUuid() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setUserUuid(final String userUuid) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getCompanyId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setCompanyId(final long companyId) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getCreateDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCreateDate(final Date createDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getModifiedDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModifiedDate(final Date modifiedDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getDefaultUser() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDefaultUser() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDefaultUser(final boolean defaultUser) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getContactId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setContactId(final long contactId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPassword(final String password) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getPasswordEncrypted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPasswordEncrypted() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPasswordEncrypted(final boolean passwordEncrypted) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getPasswordReset() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPasswordReset() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPasswordReset(final boolean passwordReset) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getPasswordModifiedDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPasswordModifiedDate(final Date passwordModifiedDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getDigest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDigest(final String digest) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getReminderQueryQuestion() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setReminderQueryQuestion(final String reminderQueryQuestion) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getReminderQueryAnswer() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setReminderQueryAnswer(final String reminderQueryAnswer) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getGraceLoginCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setGraceLoginCount(final int graceLoginCount) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getScreenName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setScreenName(final String screenName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getEmailAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setEmailAddress(final String emailAddress) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getFacebookId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFacebookId(final long facebookId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getGoogleUserId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setGoogleUserId(final String googleUserId) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getLdapServerId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setLdapServerId(final long ldapServerId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getOpenId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOpenId(final String openId) {
        // TODO Auto-generated method stub

    }

    @Override
    public long getPortraitId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setPortraitId(final long portraitId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLanguageId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLanguageId(final String languageId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getTimeZoneId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTimeZoneId(final String timeZoneId) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getGreeting() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setGreeting(final String greeting) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getComments() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setComments(final String comments) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getFirstName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setFirstName(final String firstName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getMiddleName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setMiddleName(final String middleName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastName(final String lastName) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getJobTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setJobTitle(final String jobTitle) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getLoginDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLoginDate(final Date loginDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLoginIP() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLoginIP(final String loginIP) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getLastLoginDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastLoginDate(final Date lastLoginDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getLastLoginIP() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastLoginIP(final String lastLoginIP) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getLastFailedLoginDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLastFailedLoginDate(final Date lastFailedLoginDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getFailedLoginAttempts() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setFailedLoginAttempts(final int failedLoginAttempts) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getLockout() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isLockout() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setLockout(final boolean lockout) {
        // TODO Auto-generated method stub

    }

    @Override
    public Date getLockoutDate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setLockoutDate(final Date lockoutDate) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getAgreedToTermsOfUse() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAgreedToTermsOfUse() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setAgreedToTermsOfUse(final boolean agreedToTermsOfUse) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean getEmailAddressVerified() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmailAddressVerified() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setEmailAddressVerified(final boolean emailAddressVerified) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getStatus() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setStatus(final int status) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setNew(final boolean n) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isCachedModel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setCachedModel(final boolean cachedModel) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isEscapedModel() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setPrimaryKeyObj(final Serializable primaryKeyObj) {
        // TODO Auto-generated method stub

    }

    @Override
    public ExpandoBridge getExpandoBridge() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setExpandoBridgeAttributes(final BaseModel<?> baseModel) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setExpandoBridgeAttributes(final ExpandoBridge expandoBridge) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setExpandoBridgeAttributes(
            final ServiceContext serviceContext) {
        // TODO Auto-generated method stub

    }

    @Override
    public int compareTo(final User user) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public CacheModel<User> toCacheModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User toEscapedModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User toUnescapedModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String toXmlString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isEntityCacheEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFinderCacheEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetOriginalValues() {
        // TODO Auto-generated method stub

    }

    @Override
    public void setModelAttributes(final Map<String, Object> attributes) {
        // TODO Auto-generated method stub

    }

    @Override
    public Class<?> getModelClass() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getModelClassName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StagedModelType getStagedModelType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void persist() {
        // TODO Auto-generated method stub

    }

    @Override
    public void addRemotePreference(final RemotePreference remotePreference) {
        // TODO Auto-generated method stub

    }

    @Override
    public Contact fetchContact() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Address> getAddresses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getBirthday() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCompanyMx() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Contact getContact() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDigest(final String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayEmailAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayURL(final String portalURL, final String mainPath)
            throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayURL(
            final String portalURL, final String mainPath,
            final boolean privateLayout) throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayURL(final ThemeDisplay themeDisplay)
            throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDisplayURL(
            final ThemeDisplay themeDisplay, final boolean privateLayout)
                    throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<EmailAddress> getEmailAddresses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getFemale() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getFullName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFullName(
            final boolean usePrefix, final boolean useSuffix) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Group getGroup() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getGroupId() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public long[] getGroupIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> getGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getInitials() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getLogin() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getMale() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Group> getMySiteGroups() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> getMySiteGroups(final int max) throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> getMySiteGroups(
            final String[] classNames, final int max) throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getOrganizationIds() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getOrganizationIds(final boolean includeAdministrative)
            throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Organization> getOrganizations() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Organization> getOrganizations(
            final boolean includeAdministrative) throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getOriginalEmailAddress() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean getPasswordModified() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PasswordPolicy getPasswordPolicy() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPasswordUnencrypted() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Phone> getPhones() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPortraitURL(final ThemeDisplay themeDisplay)
            throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getPrivateLayoutsPageCount() throws PortalException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getPublicLayoutsPageCount() throws PortalException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Set<String> getReminderQueryQuestions() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RemotePreference getRemotePreference(final String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterable<RemotePreference> getRemotePreferences() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getRoleIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Role> getRoles() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> getSiteGroups() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Group> getSiteGroups(final boolean includeAdministrative)
            throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getTeamIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Team> getTeams() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TimeZone getTimeZone() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getUnlockDate() throws PortalException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Date getUnlockDate(final PasswordPolicy passwordPolicy) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long[] getUserGroupIds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserGroup> getUserGroups() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Website> getWebsites() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasCompanyMx() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasCompanyMx(final String emailAddress)
            throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasMySites() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasOrganization() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasPrivateLayouts() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasPublicLayouts() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean hasReminderQuery() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isActive() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmailAddressComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEmailAddressVerificationComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFemale() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isMale() throws PortalException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isPasswordModified() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isReminderQueryComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSetupComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isTermsOfUseComplete() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setPasswordModified(final boolean passwordModified) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setPasswordUnencrypted(final String passwordUnencrypted) {
        // TODO Auto-generated method stub

    }

}
