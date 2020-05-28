package site.teamo.mall.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.common.util.MallJSONResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车",tags = "购物车")
@RequestMapping("/shopcart")
@RestController
public class ShoppingCartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @ApiOperation(value = "添加商品",notes = "添加商品")
    @PostMapping("/add")
    public MallJSONResult add(
            @ApiParam(name = "用户id",value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "购物车商品BO",value = "购物车商品BO")
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
            ){
        if(StringUtils.isBlank(userId)){
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "删除商品",notes = "删除商品")
    @PostMapping("/del")
    public MallJSONResult del(
            @ApiParam(name = "用户id",value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "",value = "商品规格id")
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        if(StringUtils.isBlank(userId)){
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        return MallJSONResult.ok();
    }
}
