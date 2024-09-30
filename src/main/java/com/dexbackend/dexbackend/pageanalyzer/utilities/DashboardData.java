package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.util.List;

public class DashboardData {
    private String name;
    private String url;
    private List<String> widgets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getWidgets() {
        return widgets;
    }

    public void setWidgets(List<String> widgets) {
        this.widgets = widgets;
    }
}
