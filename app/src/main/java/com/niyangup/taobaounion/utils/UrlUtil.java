package com.niyangup.taobaounion.utils;

public class UrlUtil {
    public static String createHomePageUrl(int materialId, int page) {
        return "discovery/" + materialId + "/" + page;
    }

    public static String getCoverPath(String url, int size) {
        String result = "https:" + url + "_" + Math.round(size) + "x" + Math.round(size) + ".jpg";
        LogUtil.d("tag", result);
        return result;
    }
}
