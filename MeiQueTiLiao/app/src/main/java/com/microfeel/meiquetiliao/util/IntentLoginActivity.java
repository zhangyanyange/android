package com.microfeel.meiquetiliao.util;

import android.app.Activity;
import android.content.Intent;

import com.microfeel.meiquetiliao.activity.LoginActivity;


/**
 * Created by zy2 on 2018/2/28.
 */

public class IntentLoginActivity {
    public static boolean intentLoginActivity(Activity context){
        if(PrefUtils.getInt(UIUtils.getContext(),"refresh_token_code",0)==400){
            PrefUtils.putBoolean(UIUtils.getContext(), "isLogin", false);
            context.startActivity(new Intent(context, LoginActivity.class));
            context.finish();
            return true;
        }else{
            return false;
        }
    }
}
