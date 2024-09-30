package com.dexbackend.dexbackend.pageanalyzer.utilities;

import java.util.List;

public class GetData {
    private String[] gridConfigArr;
    private List<DashboardData> dashboards;

    public String[] getGridConfigArr() {
        return gridConfigArr;
    }

    public void setGridConfigArr(String[] gridConfigArr) {
        this.gridConfigArr = gridConfigArr;
    }
    

    // Getters and Setters
    public List<DashboardData> getDashboards() {
        return dashboards;
    }

    public void setDashboards(List<DashboardData> dashboards) {
        this.dashboards = dashboards;
    }
}
