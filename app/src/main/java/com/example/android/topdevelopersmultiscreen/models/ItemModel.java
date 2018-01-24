package com.example.android.topdevelopersmultiscreen.models;

import java.util.List;

/**
 * Created by Adriana on 1/24/2018.
 */

public class ItemModel {

    private String profile_image;
    private String display_name;
    private String location;
    private List<BadgeCounts> badge_counts;

    public List<BadgeCounts> getBadge_counts() {
        return badge_counts;
    }

    public void setBadge_counts(List<BadgeCounts> badge_counts) {
        this.badge_counts = badge_counts;
    }

    public String getProfile_image() {
        return profile_image;

    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public static class BadgeCounts {
        private int bronze;
        private int silver;
        private int gold;

        public int getBronze() {
            return bronze;
        }

        public void setBronze(int bronze) {
            this.bronze = bronze;
        }

        public int getSilver() {
            return silver;
        }

        public void setSilver(int silver) {
            this.silver = silver;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }

    }


}
