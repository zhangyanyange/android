package com.microfeel.meiquetiliao.util;

import com.google.gson.Gson;
import com.microfeel.meiquetiliao.domain.Token;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by zy2 on 2018/2/27.
 */

public class TokenInterceptor implements Interceptor {
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    private Request newRequest;

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Response originalResponse = chain.proceed(originalRequest);

        // 获取返回的数据字符串
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = originalResponse.body().source();
        source.request(Integer.MAX_VALUE);
        Buffer buffer = source.buffer();
        Charset charset = UTF_8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset();
        }
        String bodyString = buffer.clone().readString(charset);

        // 如果 token 已经过期
        if (bodyString.contains("已拒绝为此请求授权")) {
            Map<String, String> map = new HashMap<>();
            map.put("grant_type", "refresh_token");
            map.put("refresh_token", PrefUtils.getString(UIUtils.getContext(), "token", ""));

            Response response1 = OkHttpUtils.post().params(map).url("https://meiqueapi.microfeel.net:12301/MQTLApi/token").build().execute();
            if (response1.isSuccessful()) {
                Gson gson = new Gson();
                Token token = gson.fromJson(response1.body().string(), Token.class);
                PrefUtils.putString(UIUtils.getContext(), "token", token.getRefresh_token());
                newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token.getAccess_token())
                        .build();
                originalResponse.body().close();
                // 保存新的 token
                return chain.proceed(newRequest);
            }else{
               PrefUtils.putInt(UIUtils.getContext(),"refresh_token_code",response1.code());
            }
        }
        return originalResponse;
    }
}

