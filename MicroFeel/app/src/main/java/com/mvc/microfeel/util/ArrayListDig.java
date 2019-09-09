package com.mvc.microfeel.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zy2 on 2017/2/27.
 */

public class ArrayListDig {
    public static String verityBigAndSmall(HashMap<Double,String> shopCount, HashMap<Double,String> shopMax){
       for (Map.Entry<Double,String> s1 : shopCount.entrySet()) {
           for (Map.Entry<Double,String> s2 : shopMax.entrySet()){
               if(s1.getValue().equals(s2.getValue())){
                   if(s1.getKey()>s2.getKey()){
                       return s2.getValue()+","+s2.getKey();
                   }
               }
           }
       }
        return "";
    }
}
