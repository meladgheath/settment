package org.softwareengine.core.model;

public enum Paths {
    SETTING(iconPath()+"icons8-settings-64.png"),
    ICONS(iconPath()+"adding.png"),
    LOCK(iconPath()+"icons8-lock-512.png"),
    UNLOCK(iconPath()+"icons8-padlock-512.png"),
    STATESMENT(iconPath()+"payment.png"),
    CONVENANT(iconPath()+"icons8-weak-financial-growth-80.png"),
    BANKS(iconPath()+"icons8-merchant-account-64.png"),
    ACCOUNT_NUMBER(iconPath()+"accounting.png")
    ;

    public final String path ;

    Paths(String p) {
        path = p ;
    }

    public String getPath(){
        return path ;
    }

    private static String iconPath() {
        return "/icons/";
    }
}