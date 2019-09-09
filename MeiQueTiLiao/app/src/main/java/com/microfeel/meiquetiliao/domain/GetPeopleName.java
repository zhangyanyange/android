package com.microfeel.meiquetiliao.domain;

/**
 * Created by zy2 on 2017/12/6.
 */


public class GetPeopleName {

    /**
     * Email : 王建宝
     * HasRegistered : true
     * LoginProvider : null
     */

    private String Email;
    private boolean HasRegistered;
    private Object LoginProvider;

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public boolean isHasRegistered() {
        return HasRegistered;
    }

    public void setHasRegistered(boolean HasRegistered) {
        this.HasRegistered = HasRegistered;
    }

    public Object getLoginProvider() {
        return LoginProvider;
    }

    public void setLoginProvider(Object LoginProvider) {
        this.LoginProvider = LoginProvider;
    }
}
