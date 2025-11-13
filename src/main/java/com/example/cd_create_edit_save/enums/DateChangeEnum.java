package com.example.cd_create_edit_save.enums;

public enum DateChangeEnum {
    CAMPAIGN_SCHEDULE_CHANGE("Campaign Schedule Change"),
    PRODUCT_LAUNCH_CHANGE("Product Launch Change"),
    EXECUTIVE_ORDER("Executive Order");

    private final String displayName;

    DateChangeEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
