package com.znz.compass.znzlibray.bean;


import com.znz.compass.znzlibray.views.tab.CustomTabEntity;

public class TabBean implements CustomTabEntity {
    public String title;
    public int unSelectedIcon;
    public int selectedIcon;

    public TabBean(String title) {
        this.title = title;
    }

    public TabBean(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
