package com.example.firebaseauth;

public class Panel {

    private final String panelType;
    private final String power;
    private final String capacity;
    private final String usagePeriod;
    private final String address;
    private final String photoUrl;

    public Panel(final String panelType, final String power, final String capacity,
                 final String usagePeriod, final String address, final String photoUrl) {
        this.panelType = panelType;
        this.power = power;
        this.capacity = capacity;
        this.usagePeriod = usagePeriod;
        this.address = address;
        this.photoUrl = photoUrl;
    }

    public String getPanelType() {
        return panelType;
    }

    public String getPower() {
        return power;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getUsagePeriod() {
        return usagePeriod;
    }

    public String getAddress() {
        return address;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}