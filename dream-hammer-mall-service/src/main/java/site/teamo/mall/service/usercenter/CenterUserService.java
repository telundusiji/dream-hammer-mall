package site.teamo.mall.service.usercenter;

import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.usercenter.CenterUserBO;

public interface CenterUserService {

    Users queryUserInfo(String userId);

    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    Users updateUserFace(String userId,String url);
}
