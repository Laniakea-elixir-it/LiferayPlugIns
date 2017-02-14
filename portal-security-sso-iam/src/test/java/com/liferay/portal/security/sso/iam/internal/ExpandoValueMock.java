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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * Implement an ExpandoValue performing no actions.
 *
 * @author Marco Fargetta
 */
public final class ExpandoValueMock implements ExpandoValue {

    @Override
    public Object clone() {
        return new ExpandoValueMock();
    }

    @Override
    public long getPrimaryKey() {
        return 0;
    }

    @Override
    public void setPrimaryKey(final long primaryKey) {
    }

    @Override
    public long getValueId() {
        return 0;
    }

    @Override
    public void setValueId(final long valueId) {
    }

    @Override
    public long getCompanyId() {
        return 0;
    }

    @Override
    public void setCompanyId(final long companyId) {
    }

    @Override
    public long getTableId() {
        return 0;
    }

    @Override
    public void setTableId(final long tableId) {
    }

    @Override
    public long getColumnId() {
        return 0;
    }

    @Override
    public void setColumnId(final long columnId) {
    }

    @Override
    public long getRowId() {
        return 0;
    }

    @Override
    public void setRowId(final long rowId) {
    }

    @Override
    public String getClassName() {
        return null;
    }

    @Override
    public void setClassName(final String className) {
    }

    @Override
    public long getClassNameId() {
        return 0;
    }

    @Override
    public void setClassNameId(final long classNameId) {
    }

    @Override
    public long getClassPK() {
        return 0;
    }

    @Override
    public void setClassPK(final long classPK) {
    }

    @Override
    public String getData() {
        return this.expandoData;
    }

    @Override
    public void setData(final String aData) {
        this.expandoData = aData;
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
    public int compareTo(final ExpandoValue expandoValue) {
        return 0;
    }

    @Override
    public CacheModel<ExpandoValue> toCacheModel() {
        return null;
    }

    @Override
    public ExpandoValue toEscapedModel() {
        return null;
    }

    @Override
    public ExpandoValue toUnescapedModel() {
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
    public void persist() {
    }

    @Override
    public List<Locale> getAvailableLocales() throws PortalException {
        return null;
    }

    @Override
    public boolean getBoolean() throws PortalException {
        return false;
    }

    @Override
    public boolean[] getBooleanArray() throws PortalException {
        return null;
    }

    @Override
    public ExpandoColumn getColumn() throws PortalException {
        return null;
    }

    @Override
    public Date getDate() throws PortalException {
        return null;
    }

    @Override
    public Date[] getDateArray() throws PortalException {
        return null;
    }

    @Override
    public Locale getDefaultLocale() throws PortalException {
        return null;
    }

    @Override
    public double getDouble() throws PortalException {
        return 0;
    }

    @Override
    public double[] getDoubleArray() throws PortalException {
        return null;
    }

    @Override
    public float getFloat() throws PortalException {
        return 0;
    }

    @Override
    public float[] getFloatArray() throws PortalException {
        return null;
    }

    @Override
    public int getInteger() throws PortalException {
        return 0;
    }

    @Override
    public int[] getIntegerArray() throws PortalException {
        return null;
    }

    @Override
    public long getLong() throws PortalException {
        return 0;
    }

    @Override
    public long[] getLongArray() throws PortalException {
        return null;
    }

    @Override
    public Number getNumber() throws PortalException {
        return null;
    }

    @Override
    public Number[] getNumberArray() throws PortalException {
        return null;
    }

    @Override
    public Serializable getSerializable() throws PortalException {
        return null;
    }

    @Override
    public short getShort() throws PortalException {
        return 0;
    }

    @Override
    public short[] getShortArray() throws PortalException {
        return null;
    }

    @Override
    public String getString() throws PortalException {
        return null;
    }

    @Override
    public String getString(final Locale locale) throws PortalException {
        return null;
    }

    @Override
    public String[] getStringArray() throws PortalException {
        return null;
    }

    @Override
    public String[] getStringArray(final Locale locale) throws PortalException {
        return null;
    }

    @Override
    public Map<Locale, String[]> getStringArrayMap() throws PortalException {
        return null;
    }

    @Override
    public Map<Locale, String> getStringMap() throws PortalException {
        return null;
    }

    @Override
    public void setBoolean(final boolean data) throws PortalException {
    }

    @Override
    public void setBooleanArray(final boolean[] data) throws PortalException {
    }

    @Override
    public void setColumn(final ExpandoColumn column) {
    }

    @Override
    public void setDate(final Date data) throws PortalException {
    }

    @Override
    public void setDateArray(final Date[] data) throws PortalException {
    }

    @Override
    public void setDouble(final double data) throws PortalException {
    }

    @Override
    public void setDoubleArray(final double[] data) throws PortalException {
    }

    @Override
    public void setFloat(final float data) throws PortalException {
    }

    @Override
    public void setFloatArray(final float[] data) throws PortalException {
    }

    @Override
    public void setInteger(final int data) throws PortalException {
    }

    @Override
    public void setIntegerArray(final int[] data) throws PortalException {
    }

    @Override
    public void setLong(final long data) throws PortalException {
    }

    @Override
    public void setLongArray(final long[] data) throws PortalException {
    }

    @Override
    public void setNumber(final Number data) throws PortalException {
    }

    @Override
    public void setNumberArray(final Number[] data) throws PortalException {
    }

    @Override
    public void setShort(final short data) throws PortalException {
    }

    @Override
    public void setShortArray(final short[] data) throws PortalException {
    }

    @Override
    public void setString(final String data) throws PortalException {
    }

    @Override
    public void setString(
            final String data, final Locale locale, final Locale defaultLocale)
                    throws PortalException {
    }

    @Override
    public void setStringArray(final String[] data) throws PortalException {
    }

    @Override
    public void setStringArray(
            final String[] data, final Locale locale,
            final Locale defaultLocale) throws PortalException {
    }

    @Override
    public void setStringArrayMap(
            final Map<Locale, String[]> dataMap, final Locale defaultLocale)
                    throws PortalException {
    }

    @Override
    public void setStringMap(
            final Map<Locale, String> dataMap, final Locale defaultLocale)
                    throws PortalException {
    }

    /**
     * Data exchanged with the tested method.
     */
    private String expandoData;
}
