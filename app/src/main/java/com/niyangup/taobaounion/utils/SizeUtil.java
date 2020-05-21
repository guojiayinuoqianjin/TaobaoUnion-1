package com.niyangup.taobaounion.utils;

import android.content.Context;

public class SizeUtil {

    public static int dip2px(Context context, float dpValue) {
        float sCale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * sCale + 0.5f);
    }

}
