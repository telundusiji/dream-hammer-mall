package site.teamo.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.UserBo;
import site.teamo.mall.common.enums.Sex;
import org.n3r.idworker.Sid;
import site.teamo.mall.common.util.DateUtil;
import site.teamo.mall.common.util.MD5Utils;
import site.teamo.mall.dao.UsersMapper;
import site.teamo.mall.service.UserService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        Users user = usersMapper.selectOneByExample(example);
        return user == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBo userBo) throws Exception {
        Users users = Users.builder()
                .id(sid.nextShort())
                .username(userBo.getUsername())
                .password(MD5Utils.toMD5(userBo.getPassword()))
                .nickname(userBo.getUsername())
                .face("http://file.te-amo.site/images/D2L/2WM.jpg")
                .birthday(DateUtil.stringToDate("1970-01-01"))
                .sex(Sex.SECRET.type)
                .createdTime(new Date())
                .updatedTime(new Date())
                .build();
        usersMapper.insert(users);
        return users;
    }

    @Override
    public Users queryUserForLogin(String username, String password) throws Exception {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",MD5Utils.toMD5(password));
        Users user = usersMapper.selectOneByExample(example);
        return user;
    }
}
