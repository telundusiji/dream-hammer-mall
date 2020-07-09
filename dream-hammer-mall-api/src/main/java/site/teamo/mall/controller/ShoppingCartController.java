package site.teamo.mall.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.common.util.MallJSONResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Api(value = "购物车", tags = "购物车")
@RequestMapping("/shopcart")
@RestController
public class ShoppingCartController {

    private static final String REDIS_KEY_SHOP_CART = "shopCart";

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);


    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping("/add")
    public MallJSONResult add(
            @ApiParam(name = "用户id", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "购物车商品BO", value = "购物车商品BO")
            @RequestBody ShopcartBO shopcartBO,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }
        String shopCart = redisTemplate.opsForValue().get(REDIS_KEY_SHOP_CART + ":" + userId);
        if (StringUtils.isBlank(shopCart)) {
            redisTemplate.opsForValue().set(REDIS_KEY_SHOP_CART + ":" + userId, JSON.toJSONString(Arrays.asList(new ShopcartBO[]{shopcartBO})));
        } else {
            List<ShopcartBO> shopcartBOS = JSON.parseArray(shopCart, ShopcartBO.class);
            shopcartBOS.remove(shopcartBO);
            shopcartBOS.add(shopcartBO);
            redisTemplate.opsForValue().set(REDIS_KEY_SHOP_CART + ":" + userId,JSON.toJSONString(shopcartBOS));
        }
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @PostMapping("/del")
    public MallJSONResult del(
            @ApiParam(name = "用户id", value = "用户id")
            @RequestParam String userId,
            @ApiParam(name = "", value = "商品规格id")
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (StringUtils.isBlank(userId)) {
            return MallJSONResult.errorMsg("用户id不能为空");
        }

        String shopCart = redisTemplate.opsForValue().get(REDIS_KEY_SHOP_CART + ":" + userId);
        if (StringUtils.isNotBlank(shopCart)) {
            List<ShopcartBO> shopcartBOS = JSON.parseArray(shopCart, ShopcartBO.class);
            shopcartBOS.remove(ShopcartBO.builder().specId(itemSpecId).build());
            redisTemplate.opsForValue().set(REDIS_KEY_SHOP_CART + ":" + userId,JSON.toJSONString(shopcartBOS));
        }
        return MallJSONResult.ok();
    }
}
