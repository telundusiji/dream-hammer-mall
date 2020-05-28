package site.teamo.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.bo.AddressBO;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.AddressService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "收货地址",tags = "收货地址")
@RequestMapping("/address")
@RestController
public class AddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "收货地址列表",notes = "收货地址列表")
    @PostMapping("/list")
    public MallJSONResult list(
            @ApiParam(name = "用户id",value = "用户id")
            @RequestParam String userId
            ){
        if(StringUtils.isBlank(userId)){
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        return MallJSONResult.ok(addressService.queryAll(userId));
    }

    @ApiOperation(value = "新增收货地址",notes = "新增收货地址")
    @PostMapping("/add")
    public MallJSONResult add(
            @ApiParam(name = "收货地址bo",value = "收货地址bo")
            @RequestBody AddressBO addressBO
            ){
        if(addressBO==null){
            return MallJSONResult.errorMsg("信息有误");
        }
        MallJSONResult mallJSONResult = addressBO.check();
        if(mallJSONResult.getStatus()!=200){
            return mallJSONResult;
        }
        addressService.addNewUserAddress(addressBO);
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址",notes = "用户修改地址")
    @PostMapping("/update")
    public MallJSONResult update(
            @ApiParam(name = "收货地址bo",value = "收货地址bo")
            @RequestBody AddressBO addressBO
    ){
        if(addressBO==null){
            return MallJSONResult.errorMsg("信息有误");
        }
        if(StringUtils.isBlank(addressBO.getAddressId())){
            return MallJSONResult.errorMsg("地址id不能为空");
        }
        MallJSONResult mallJSONResult = addressBO.check();
        if(mallJSONResult.getStatus()!=200){
            return mallJSONResult;
        }
        addressService.updateUserAddress(addressBO);
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址",notes = "用户修改地址")
    @PostMapping("/delete")
    public MallJSONResult delete(
            @ApiParam(name = "用户id",value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "地址id",value = "地址id")
            @RequestParam String addressId
    ){

        if(StringUtils.isBlank(userId)||StringUtils.isBlank(addressId)){
            return MallJSONResult.errorMsg("用户id和地址id不能为空");
        }
        addressService.deleteUserAddress(userId,addressId);
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "用户修改地址",notes = "用户修改地址")
    @PostMapping("/setDefault")
    public MallJSONResult setDefault(
            @ApiParam(name = "用户id",value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "地址id",value = "地址id")
            @RequestParam String addressId
    ){

        if(StringUtils.isBlank(userId)||StringUtils.isBlank(addressId)){
            return MallJSONResult.errorMsg("用户id和地址id不能为空");
        }
        addressService.updateUserAddressSetDefault(userId,addressId);
        return MallJSONResult.ok();
    }
}
