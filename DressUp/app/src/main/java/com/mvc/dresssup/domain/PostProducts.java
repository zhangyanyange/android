package com.mvc.dresssup.domain;

import java.util.List;

/**
 * Created by zy2 on 2017/11/1.
 */

public class PostProducts {

    /**
     * AddressId : sample string 1
     * CartBindingModel : [{"Id":"sample string 1","Num":2},{"Id":"sample string 1","Num":2}]
     */

    private String AddressId;
    private List<AddCar> CartBindingModel;

    public String getAddressId() {
        return AddressId;
    }

    public void setAddressId(String AddressId) {
        this.AddressId = AddressId;
    }

    public List<AddCar> getCartBindingModel() {
        return CartBindingModel;
    }

    public void setCartBindingModel(List<AddCar> CartBindingModel) {
        this.CartBindingModel = CartBindingModel;
    }
}
