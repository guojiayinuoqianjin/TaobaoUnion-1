package com.niyangup.taobaounion.utils;

public class UrlUtil {
    public static String createHomePageUrl(int materialId, int page) {
        return "discovery/" + materialId + "/" + page;
    }

    public static String getCoverPath(String url) {
        return "https:" + url;
    }
}
