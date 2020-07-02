package site.teamo.mall.service;

import site.teamo.mall.bean.UserAddress;
import site.teamo.mall.bean.bo.AddressBO;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void deleteUserAddress(String userId,String addressId);

    void updateUserAddressSetDefault(String userId,String addressId);

    UserAddress queryAddress(String userId,String addressId);
}
