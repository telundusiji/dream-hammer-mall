package site.teamo.mall.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import site.teamo.mall.bean.UserAddress;
import site.teamo.mall.bean.bo.AddressBO;
import site.teamo.mall.common.enums.YesNo;
import site.teamo.mall.dao.UserAddressMapper;
import site.teamo.mall.service.AddressService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    public List<UserAddress> queryAll(String userId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        return userAddressMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault = 0;
        List<UserAddress> userAddresses = queryAll(addressBO.getUserId());
        if(CollectionUtils.isEmpty(userAddresses)){
            isDefault = 1;
        }
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO,userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddressMapper.delete(userAddress);
    }

    @Override
    public void updateUserAddressSetDefault(String userId, String addressId) {
        UserAddress query = new UserAddress();
        query.setUserId(userId);
        query.setIsDefault(YesNo.YES.type);
        userAddressMapper.select(query).forEach(x->{
            x.setIsDefault(YesNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(x);
        });
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddress.setIsDefault(YesNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryAddress(String userId, String addressId) {
        UserAddress query = new UserAddress();
        query.setUserId(userId);
        query.setId(addressId);
        return userAddressMapper.selectOne(query);
    }
}
