package com.zayvius.zs_ads.ads;

public class ZayviusAdsOnOff {
    protected static boolean ad_admob;
    protected static boolean ad_applovinmax;
    protected static boolean ad_applovinzone;
    //public static boolean ad_unity;
    public static void allads(boolean admob,boolean applovinmax,boolean applovinzone ){
        ad_admob = admob;
        ad_applovinmax = applovinmax;
        ad_applovinzone = applovinzone;
        //ad_unity = unity;
    }

}
