package site.teamo.mall.service.impl.usercenter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.usercenter.CenterUserBO;
import site.teamo.mall.dao.UsersMapper;
import site.teamo.mall.service.usercenter.CenterUserService;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users user = usersMapper.selectByPrimaryKey(userId);
        user.setPassword(null);
        return user;
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {
        Users user = new Users();
        BeanUtils.copyProperties(centerUserBO, user);
        user.setId(userId);
        user.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(user);

        Users u = usersMapper.selectByPrimaryKey(userId);
        u.setPassword(null);
        return u;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String url) {
        Users users = Users.builder()
                .id(userId)
                .face(url)
                .updatedTime(new Date())
                .build();
        usersMapper.updateByPrimaryKeySelective(users);
        return usersMapper.selectByPrimaryKey(userId);
    }
}
