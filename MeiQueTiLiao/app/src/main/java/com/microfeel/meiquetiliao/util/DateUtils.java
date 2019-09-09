package com.microfeel.meiquetiliao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zy2 on 2017/12/20.
 */

public class DateUtils {
    public static String GetNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
