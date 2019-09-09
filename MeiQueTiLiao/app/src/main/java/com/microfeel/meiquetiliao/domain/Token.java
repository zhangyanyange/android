package com.microfeel.meiquetiliao.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zy2 on 2018/2/27.
 */

public class Token {


    /**
     * access_token : 4Y9OcXwPIp2CO2HzSOkfWt0fr4cEoXj1SuU-jVmBLSUChyWu_A-Z6nItVM4wOBTNOOZS9U9uXElTBN8QEeQPnaOoKWjD7eqQbCxNFFB7cRFeFu-r7eKvDhWJLF6eDEuuFR-2e0lkGtdr_oUXQeX6ZJy-N9lDb1bHkuG36t-Zj0ZLe1mkZQW0xDRYuVIo7pi5W08XPQNOZwlQvQl9KKFvcH1vrPbu8Ln2kDj1YLqvWPTVLABA-5oTi5hgnp7mAVYOGnlCifEsSIZMx5NRFCFcFw3q4j3kwN5LyzbnaOHsX4HX5-QfKqn0qBDqAoV2pzb_yVp9RQtAv1RevVnL-C9GmlfRrGMoFvH-VY3AKgaB8ilqdecU4gJf-97Nn8bHAM__cV6PRLfDInBXntkDmadPuFm87Ay32CmbzNSxrBUjljM_4xO8iuzBaUapLgSuIdBw
     * token_type : bearer
     * expires_in : 86399
     * refresh_token : 368a74d955de492f95b5aa78cc02f569
     * result : 0
     * message : 调用成功
     * .issued : Tue, 27 Feb 2018 08:23:58 GMT
     * .expires : Wed, 28 Feb 2018 08:23:58 GMT
     */

    private String access_token;
    private String token_type;
    private int expires_in;
    private String refresh_token;
    private String result;
    private String message;

    @SerializedName(".expires")
    private String _$Expires263; // FIXME check this code

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String get_$Expires263() {
        return _$Expires263;
    }

    public void set_$Expires263(String _$Expires263) {
        this._$Expires263 = _$Expires263;
    }
}
