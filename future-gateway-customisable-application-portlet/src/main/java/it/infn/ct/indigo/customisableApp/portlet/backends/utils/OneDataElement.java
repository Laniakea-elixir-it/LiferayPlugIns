package it.infn.ct.indigo.customisableApp.portlet.backends.utils;

import java.util.ArrayList;
import java.util.List;

public class OneDataElement {
    String id;
    String name;
    List<String> providers = new ArrayList<>();
    boolean folder;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isFolder() {
        return folder;
    }
    public void setFolder(boolean folder) {
        this.folder = folder;
    }
    public void addProvider(String provider) {
        providers.add(provider);
    }
    public List<String> getProviders() {
        return providers;
    }
    public void setProviders(List<String> providers) {
        this.providers = providers;
    }
    
}
