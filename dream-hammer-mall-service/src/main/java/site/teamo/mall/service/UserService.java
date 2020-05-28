package site.teamo.mall.service;

import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.UserBo;

public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username
     * @return true：已经存在；false：不存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建一个用户
     * @param userBo
     * @return 创建好的用户对象
     */
    Users createUser(UserBo userBo) throws Exception;

    Users queryUserForLogin(String username,String password) throws Exception;

}
