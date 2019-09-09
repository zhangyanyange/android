package com.mvc.microfeel.util;

import java.util.ArrayList;

/**
 * Created by zy2 on 2017/2/27.
 */

public class TextBoolean {
    public static boolean isHasFalse(ArrayList<Boolean> arrayList){
        for (int i = 0; i < arrayList.size(); i++) {
            if(!arrayList.get(i)){
                return true;
            }
        }
        return false;
    }
}
