package com.microfeel.meiquetiliao.util;

import android.widget.Toast;

/**
 * Created by zy2 on 2017/12/7.
 */

public class ShowToastTime {
    private static Toast toast = null;

    public static void showTextToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(UIUtils.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
