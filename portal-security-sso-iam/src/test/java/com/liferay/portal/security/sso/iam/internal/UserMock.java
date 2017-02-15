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

        return 0;
    }

    @Override
    public void setPrimaryKey(final long primaryKey) {


    }

    @Override
    public long getMvccVersion() {

        return 0;
    }

    @Override
    public void setMvccVersion(final long mvccVersion) {


    }

    @Override
    public String getUuid() {

        return null;
    }

    @Override
    public void setUuid(final String uuid) {


    }

    @Override
    public long getUserId() {

        return 0;
    }

    @Override
    public void setUserId(final long userId) {


    }

    @Override
    public String getUserUuid() {

        return null;
    }

    @Override
    public void setUserUuid(final String userUuid) {


    }

    @Override
    public long getCompanyId() {

        return 0;
    }

    @Override
    public void setCompanyId(final long companyId) {


    }

    @Override
    public Date getCreateDate() {

        return null;
    }

    @Override
    public void setCreateDate(final Date createDate) {


    }

    @Override
    public Date getModifiedDate() {

        return null;
    }

    @Override
    public void setModifiedDate(final Date modifiedDate) {


    }

    @Override
    public boolean getDefaultUser() {

        return false;
    }

    @Override
    public boolean isDefaultUser() {

        return false;
    }

    @Override
    public void setDefaultUser(final boolean defaultUser) {


    }

    @Override
    public long getContactId() {

        return 0;
    }

    @Override
    public void setContactId(final long contactId) {


    }

    @Override
    public String getPassword() {

        return null;
    }

    @Override
    public void setPassword(final String password) {


    }

    @Override
    public boolean getPasswordEncrypted() {

        return false;
    }

    @Override
    public boolean isPasswordEncrypted() {

        return false;
    }

    @Override
    public void setPasswordEncrypted(final boolean passwordEncrypted) {


    }

    @Override
    public boolean getPasswordReset() {

        return false;
    }

    @Override
    public boolean isPasswordReset() {

        return false;
    }

    @Override
    public void setPasswordReset(final boolean passwordReset) {


    }

    @Override
    public Date getPasswordModifiedDate() {

        return null;
    }

    @Override
    public void setPasswordModifiedDate(final Date passwordModifiedDate) {


    }

    @Override
    public String getDigest() {

        return null;
    }

    @Override
    public void setDigest(final String digest) {


    }

    @Override
    public String getReminderQueryQuestion() {

        return null;
    }

    @Override
    public void setReminderQueryQuestion(final String reminderQueryQuestion) {


    }

    @Override
    public String getReminderQueryAnswer() {

        return null;
    }

    @Override
    public void setReminderQueryAnswer(final String reminderQueryAnswer) {


    }

    @Override
    public int getGraceLoginCount() {

        return 0;
    }

    @Override
    public void setGraceLoginCount(final int graceLoginCount) {


    }

    @Override
    public String getScreenName() {

        return null;
    }

    @Override
    public void setScreenName(final String screenName) {


    }

    @Override
    public String getEmailAddress() {

        return null;
    }

    @Override
    public void setEmailAddress(final String emailAddress) {


    }

    @Override
    public long getFacebookId() {

        return 0;
    }

    @Override
    public void setFacebookId(final long facebookId) {


    }

    @Override
    public String getGoogleUserId() {

        return null;
    }

    @Override
    public void setGoogleUserId(final String googleUserId) {


    }

    @Override
    public long getLdapServerId() {

        return 0;
    }

    @Override
    public void setLdapServerId(final long ldapServerId) {


    }

    @Override
    public String getOpenId() {

        return null;
    }

    @Override
    public void setOpenId(final String openId) {


    }

    @Override
    public long getPortraitId() {

        return 0;
    }

    @Override
    public void setPortraitId(final long portraitId) {


    }

    @Override
    public String getLanguageId() {

        return null;
    }

    @Override
    public void setLanguageId(final String languageId) {


    }

    @Override
    public String getTimeZoneId() {

        return null;
    }

    @Override
    public void setTimeZoneId(final String timeZoneId) {


    }

    @Override
    public String getGreeting() {

        return null;
    }

    @Override
    public void setGreeting(final String greeting) {


    }

    @Override
    public String getComments() {

        return null;
    }

    @Override
    public void setComments(final String comments) {


    }

    @Override
    public String getFirstName() {

        return null;
    }

    @Override
    public void setFirstName(final String firstName) {


    }

    @Override
    public String getMiddleName() {

        return null;
    }

    @Override
    public void setMiddleName(final String middleName) {


    }

    @Override
    public String getLastName() {

        return null;
    }

    @Override
    public void setLastName(final String lastName) {


    }

    @Override
    public String getJobTitle() {

        return null;
    }

    @Override
    public void setJobTitle(final String jobTitle) {


    }

    @Override
    public Date getLoginDate() {

        return null;
    }

    @Override
    public void setLoginDate(final Date loginDate) {


    }

    @Override
    public String getLoginIP() {

        return null;
    }

    @Override
    public void setLoginIP(final String loginIP) {


    }

    @Override
    public Date getLastLoginDate() {

        return null;
    }

    @Override
    public void setLastLoginDate(final Date lastLoginDate) {


    }

    @Override
    public String getLastLoginIP() {

        return null;
    }

    @Override
    public void setLastLoginIP(final String lastLoginIP) {


    }

    @Override
    public Date getLastFailedLoginDate() {

        return null;
    }

    @Override
    public void setLastFailedLoginDate(final Date lastFailedLoginDate) {


    }

    @Override
    public int getFailedLoginAttempts() {

        return 0;
    }

    @Override
    public void setFailedLoginAttempts(final int failedLoginAttempts) {


    }

    @Override
    public boolean getLockout() {

        return false;
    }

    @Override
    public boolean isLockout() {

        return false;
    }

    @Override
    public void setLockout(final boolean lockout) {


    }

    @Override
    public Date getLockoutDate() {

        return null;
    }

    @Override
    public void setLockoutDate(final Date lockoutDate) {


    }

    @Override
    public boolean getAgreedToTermsOfUse() {

        return false;
    }

    @Override
    public boolean isAgreedToTermsOfUse() {

        return false;
    }

    @Override
    public void setAgreedToTermsOfUse(final boolean agreedToTermsOfUse) {


    }

    @Override
    public boolean getEmailAddressVerified() {

        return false;
    }

    @Override
    public boolean isEmailAddressVerified() {

        return false;
    }

    @Override
    public void setEmailAddressVerified(final boolean emailAddressVerified) {


    }

    @Override
    public int getStatus() {

        return 0;
    }

    @Override
    public void setStatus(final int status) {


    }

    @Override
    public boolean isNew() {

        return false;
    }

    @Override
    public void setNew(final boolean n) {


    }

    @Override
    public boolean isCachedModel() {

        return false;
    }

    @Override
    public void setCachedModel(final boolean cachedModel) {


    }

    @Override
    public boolean isEscapedModel() {

        return false;
    }

    @Override
    public Serializable getPrimaryKeyObj() {

        return null;
    }

    @Override
    public void setPrimaryKeyObj(final Serializable primaryKeyObj) {


    }

    @Override
    public ExpandoBridge getExpandoBridge() {

        return null;
    }

    @Override
    public void setExpandoBridgeAttributes(final BaseModel<?> baseModel) {


    }

    @Override
    public void setExpandoBridgeAttributes(final ExpandoBridge expandoBridge) {


    }

    @Override
    public void setExpandoBridgeAttributes(
            final ServiceContext serviceContext) {


    }

    @Override
    public int compareTo(final User user) {

        return 0;
    }

    @Override
    public CacheModel<User> toCacheModel() {

        return null;
    }

    @Override
    public User toEscapedModel() {

        return null;
    }

    @Override
    public User toUnescapedModel() {

        return null;
    }

    @Override
    public String toXmlString() {

        return null;
    }

    @Override
    public Map<String, Object> getModelAttributes() {

        return null;
    }

    @Override
    public boolean isEntityCacheEnabled() {

        return false;
    }

    @Override
    public boolean isFinderCacheEnabled() {

        return false;
    }

    @Override
    public void resetOriginalValues() {


    }

    @Override
    public void setModelAttributes(final Map<String, Object> attributes) {


    }

    @Override
    public Class<?> getModelClass() {

        return null;
    }

    @Override
    public String getModelClassName() {

        return null;
    }

    @Override
    public StagedModelType getStagedModelType() {

        return null;
    }

    @Override
    public void persist() {


    }

    @Override
    public void addRemotePreference(final RemotePreference remotePreference) {


    }

    @Override
    public Contact fetchContact() {

        return null;
    }

    @Override
    public List<Address> getAddresses() {

        return null;
    }

    @Override
    public Date getBirthday() throws PortalException {

        return null;
    }

    @Override
    public String getCompanyMx() throws PortalException {

        return null;
    }

    @Override
    public Contact getContact() throws PortalException {

        return null;
    }

    @Override
    public String getDigest(final String password) {

        return null;
    }

    @Override
    public String getDisplayEmailAddress() {

        return null;
    }

    @Override
    public String getDisplayURL(final String portalURL, final String mainPath)
            throws PortalException {

        return null;
    }

    @Override
    public String getDisplayURL(
            final String portalURL, final String mainPath,
            final boolean privateLayout) throws PortalException {

        return null;
    }

    @Override
    public String getDisplayURL(final ThemeDisplay themeDisplay)
            throws PortalException {

        return null;
    }

    @Override
    public String getDisplayURL(
            final ThemeDisplay themeDisplay, final boolean privateLayout)
                    throws PortalException {

        return null;
    }

    @Override
    public List<EmailAddress> getEmailAddresses() {

        return null;
    }

    @Override
    public boolean getFemale() throws PortalException {

        return false;
    }

    @Override
    public String getFullName() {

        return null;
    }

    @Override
    public String getFullName(
            final boolean usePrefix, final boolean useSuffix) {

        return null;
    }

    @Override
    public Group getGroup() {

        return null;
    }

    @Override
    public long getGroupId() {

        return 0;
    }

    @Override
    public long[] getGroupIds() {

        return null;
    }

    @Override
    public List<Group> getGroups() {

        return null;
    }

    @Override
    public String getInitials() {

        return null;
    }

    @Override
    public Locale getLocale() {

        return null;
    }

    @Override
    public String getLogin() throws PortalException {

        return null;
    }

    @Override
    public boolean getMale() throws PortalException {

        return false;
    }

    @Override
    public List<Group> getMySiteGroups() throws PortalException {

        return null;
    }

    @Override
    public List<Group> getMySiteGroups(final int max) throws PortalException {

        return null;
    }

    @Override
    public List<Group> getMySiteGroups(
            final String[] classNames, final int max) throws PortalException {

        return null;
    }

    @Override
    public long[] getOrganizationIds() throws PortalException {

        return null;
    }

    @Override
    public long[] getOrganizationIds(final boolean includeAdministrative)
            throws PortalException {

        return null;
    }

    @Override
    public List<Organization> getOrganizations() throws PortalException {

        return null;
    }

    @Override
    public List<Organization> getOrganizations(
            final boolean includeAdministrative) throws PortalException {

        return null;
    }

    @Override
    public String getOriginalEmailAddress() {

        return null;
    }

    @Override
    public boolean getPasswordModified() {

        return false;
    }

    @Override
    public PasswordPolicy getPasswordPolicy() throws PortalException {

        return null;
    }

    @Override
    public String getPasswordUnencrypted() {

        return null;
    }

    @Override
    public List<Phone> getPhones() {

        return null;
    }

    @Override
    public String getPortraitURL(final ThemeDisplay themeDisplay)
            throws PortalException {

        return null;
    }

    @Override
    public int getPrivateLayoutsPageCount() throws PortalException {

        return 0;
    }

    @Override
    public int getPublicLayoutsPageCount() throws PortalException {

        return 0;
    }

    @Override
    public Set<String> getReminderQueryQuestions() throws PortalException {

        return null;
    }

    @Override
    public RemotePreference getRemotePreference(final String name) {

        return null;
    }

    @Override
    public Iterable<RemotePreference> getRemotePreferences() {

        return null;
    }

    @Override
    public long[] getRoleIds() {

        return null;
    }

    @Override
    public List<Role> getRoles() {

        return null;
    }

    @Override
    public List<Group> getSiteGroups() throws PortalException {

        return null;
    }

    @Override
    public List<Group> getSiteGroups(final boolean includeAdministrative)
            throws PortalException {

        return null;
    }

    @Override
    public long[] getTeamIds() {

        return null;
    }

    @Override
    public List<Team> getTeams() {

        return null;
    }

    @Override
    public TimeZone getTimeZone() {

        return null;
    }

    @Override
    public Date getUnlockDate() throws PortalException {

        return null;
    }

    @Override
    public Date getUnlockDate(final PasswordPolicy passwordPolicy) {

        return null;
    }

    @Override
    public long[] getUserGroupIds() {

        return null;
    }

    @Override
    public List<UserGroup> getUserGroups() {

        return null;
    }

    @Override
    public List<Website> getWebsites() {

        return null;
    }

    @Override
    public boolean hasCompanyMx() throws PortalException {

        return false;
    }

    @Override
    public boolean hasCompanyMx(final String emailAddress)
            throws PortalException {

        return false;
    }

    @Override
    public boolean hasMySites() throws PortalException {

        return false;
    }

    @Override
    public boolean hasOrganization() {

        return false;
    }

    @Override
    public boolean hasPrivateLayouts() throws PortalException {

        return false;
    }

    @Override
    public boolean hasPublicLayouts() throws PortalException {

        return false;
    }

    @Override
    public boolean hasReminderQuery() {

        return false;
    }

    @Override
    public boolean isActive() {

        return false;
    }

    @Override
    public boolean isEmailAddressComplete() {

        return false;
    }

    @Override
    public boolean isEmailAddressVerificationComplete() {

        return false;
    }

    @Override
    public boolean isFemale() throws PortalException {

        return false;
    }

    @Override
    public boolean isMale() throws PortalException {

        return false;
    }

    @Override
    public boolean isPasswordModified() {

        return false;
    }

    @Override
    public boolean isReminderQueryComplete() {

        return false;
    }

    @Override
    public boolean isSetupComplete() {

        return false;
    }

    @Override
    public boolean isTermsOfUseComplete() {

        return false;
    }

    @Override
    public void setPasswordModified(final boolean passwordModified) {


    }

    @Override
    public void setPasswordUnencrypted(final String passwordUnencrypted) {


    }

}
