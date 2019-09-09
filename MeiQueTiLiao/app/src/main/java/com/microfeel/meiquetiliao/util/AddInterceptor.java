package com.microfeel.meiquetiliao.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zy2 on 2018/2/27.
 */

public class AddInterceptor implements Interceptor {
    private Request newRequest;

    @Override
    public Response intercept(final Chain chain) throws IOException {

        final Request originalRequest = chain.request();
        newRequest = originalRequest
                .newBuilder()
                .header("Authorization", "Bearer " + PrefUtils.getString(UIUtils.getContext(), "assets_token", ""))
                .build();
        // 保存新的 token
        return chain.proceed(newRequest);
    }
}

