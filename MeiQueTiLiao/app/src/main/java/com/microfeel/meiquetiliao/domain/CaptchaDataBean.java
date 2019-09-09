package com.microfeel.meiquetiliao.domain;

/**
 * Created by zy2 on 2017/8/29.
 */



public class CaptchaDataBean {


    /**
     * stringContent : eJxlz11PgzAUgOF7fgXprUa6lo9h4kVdYFsCUzZwcTcE10I6tFTazTnjf1erRhLP7fvknJw3y7ZtkCeri2q77fZCl-pVMmBf2gCC878oJadlpUvc03*RHSXvWVnVmvUmjjzPQxAODadMaF7zX4F9jH0EkesPkKJtaS59G-dzBUIB8oaENyamUTGZR3K*WatmQZR6yXFaTMPH5mYyvZOZOopGE0kT4nTL-EA2hEdEpe0M7oNonOyWDo5X63rW3p52WcXHZw9OjGkRL55P4v46zK4GJzV-Yj9vjRD2Qxy4g3pgveKdMABBQ*DXAOvd*gAofl1P
     * result : 0
     * message : 调用成功
     */

    private String stringContent;
    private int result;
    private String message;

    public String getStringContent() {
        return stringContent;
    }

    public void setStringContent(String stringContent) {
        this.stringContent = stringContent;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
