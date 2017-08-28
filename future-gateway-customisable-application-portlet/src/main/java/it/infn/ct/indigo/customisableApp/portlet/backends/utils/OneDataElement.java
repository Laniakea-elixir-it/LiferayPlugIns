package it.infn.ct.indigo.customisableApp.portlet.backends.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a resource stored in OneData.
 */
public class OneDataElement {
    /**
     * The resource id.
     */
    private String id;

    /**
     * The resource name.
     */
    private String name;

    /**
     * List of provider ids supporting the resource.
     */
    private List<String> providers = new ArrayList<>();

    /**
     * Provider for the resource.
     */
    private String provider;

    /**
     * A flag indicating if the resource has to be shown as a folder.
     */
    private boolean folder;

    /**
     * Retrieves the id.
     *
     * @return The resource id
     */
    public final String getId() {
        return id;
    }

    /**
     * Sets the resource id.
     *
     * @param anId The resource id
     */
    public final void setId(final String anId) {
        this.id = anId;
    }

    /**
     * Retrieves the name.
     *
     * @return The resource name
     */
    public final String getName() {
        return name;
    }

    /**
     * Sets the resource name.
     *
     * @param aName A name
     */
    public final void setName(final String aName) {
        this.name = aName;
    }

    /**
     * Returns true if the resource can be associated to a folder.
     * This is the case when the resource can contain other resources as
     * a sub-path
     *
     * @return True if this is a folder, false otherwise
     */
    public final boolean isFolder() {
        return folder;
    }

    /**
     * Sets if the resource has to be considered as a folder.
     *
     * @param isFolder True if this has to be a folder, false otherwise
     */
    public final void setFolder(final boolean isFolder) {
        this.folder = isFolder;
    }

    /**
     * Adds a provider to the list of supporting providers.
     *
     * @param aProviderId A provider id
     */
    public final void addProvider(final String aProviderId) {
        providers.add(aProviderId);
    }

    /**
     * Retrieves the list of providers.
     *
     * @return The list of supporting providers
     */
    public final List<String> getProviders() {
        return providers;
    }

    /**
     * Sets the list of supporting providers.
     *
     * @param someProviders A provider list
     */
    public final void setProviders(final List<String> someProviders) {
        this.providers = someProviders;
    }

    /**
     * Retrieve the provider used for the resource.
     * If null the provided or default provider is used. As an example for
     * resource managed by a OneZone the value is null
     *
     * @return Provider used for the resource
     */
    public final String getProvider() {
        return provider;
    }

    /**
     * Sets the provider for the identifid resource.
     *
     * @param aProvider A provider
     */
    public final void setProvider(final String aProvider) {
        this.provider = aProvider;
    }
}
