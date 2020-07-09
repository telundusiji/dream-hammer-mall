package site.teamo.mall.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import site.teamo.mall.bean.Users;
import site.teamo.mall.bean.bo.ShopcartBO;
import site.teamo.mall.bean.bo.UserBo;
import site.teamo.mall.common.util.CookieUtils;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value = "注册登录", tags = "注册登录")
@RestController
@RequestMapping("/passport")
public class PassportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassportController.class);

    private static final String COOKIE_SHOP_CART = "shopcart";
    private static final String REDIS_KEY_SHOP_CART = "shopCart";

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在")
    @GetMapping("/usernameIsExist")
    public MallJSONResult usernameIsExist(@RequestParam String username) {
        //step1：username校验是否为空
        if (StringUtils.isBlank(username)) {
            return MallJSONResult.errorMsg("用户名不能为空");
        }

        //step2：查找该用户名是否已经存在
        if (userService.queryUsernameIsExist(username)) {
            return MallJSONResult.errorMsg("用户名已经存在");
        }
        //step3：用户名没有重复
        return MallJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/regist")
    public MallJSONResult regist(HttpServletRequest request, HttpServletResponse response, @RequestBody UserBo userBo) {
        try {
            //step1：判断入参不能为空
            if (StringUtils.isBlank(userBo.getUsername())
                    || StringUtils.isBlank(userBo.getPassword())
                    || StringUtils.isBlank(userBo.getConfirmPassword())) {
                return MallJSONResult.errorMsg("用户名或密码不能为空");
            }
            //step2：查找该用户名是否已经存在
            if (userService.queryUsernameIsExist(userBo.getUsername())) {
                return MallJSONResult.errorMsg("用户名已经存在");
            }

            //step3：密码长度不能小于6位
            if (userBo.getPassword().length() < 6) {
                return MallJSONResult.errorMsg("密码长度不能小于6位");
            }

            //step4：判断两次密码是否一致
            if (!StringUtils.equals(userBo.getPassword(), userBo.getConfirmPassword())) {
                return MallJSONResult.errorMsg("两次输入密码不一致");
            }

            Users users = userService.createUser(userBo);
            users.cleanUpSensitiveInformation();
            CookieUtils.setCookie(request, response, "user", JSON.toJSONString(users), true);
            margeShopCart(users.getId(),request,response);
            return MallJSONResult.ok(users);
        } catch (Exception e) {
            LOGGER.error("register user failed", e);
            return MallJSONResult.errorException("用户注册失败");
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户登录")
    @PostMapping("/login")
    public MallJSONResult login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserBo userBo) {
        try {
            //step1：判断入参不能为空
            if (StringUtils.isBlank(userBo.getUsername())
                    || StringUtils.isBlank(userBo.getPassword())) {
                return MallJSONResult.errorMsg("用户名或密码不能为空");
            }

            Users users = userService.queryUserForLogin(userBo.getUsername(), userBo.getPassword());

            if (users == null) {
                return MallJSONResult.errorMsg("用户名或密码不正确");
            }
            users.cleanUpSensitiveInformation();
            CookieUtils.setCookie(request, response, "user", JSON.toJSONString(users), true);
            margeShopCart(users.getId(),request,response);
            return MallJSONResult.ok(users);
        } catch (Exception e) {
            LOGGER.error("register user failed", e);
            return MallJSONResult.errorException("用户登录失败");
        }
    }


    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/logout")
    public MallJSONResult logout(HttpServletRequest request, HttpServletResponse response, @RequestParam String userId) {

        CookieUtils.deleteCookie(request, response, "user");
        CookieUtils.deleteCookie(request, response, COOKIE_SHOP_CART);
        return MallJSONResult.ok();
    }

    public void margeShopCart(String userId, HttpServletRequest request, HttpServletResponse response) {
        String redisShopCart = redisTemplate.opsForValue().get(REDIS_KEY_SHOP_CART + ":" + userId);
        String cookieShopCart = CookieUtils.getCookieValue(request, COOKIE_SHOP_CART, true);

        /**
         * 如果redis和cookie中购物车都为空
         */
        if (StringUtils.isBlank(redisShopCart) && StringUtils.isBlank(cookieShopCart)) {
            return;
        }

        /**
         * 如果cookie中购物车为空，redis不为空
         */
        if (StringUtils.isBlank(cookieShopCart)) {
            CookieUtils.setCookie(request, response, COOKIE_SHOP_CART, redisShopCart, true);
            return;
        }

        /**
         * 如果cookie中购物车为空，redis中购物车不为空
         */
        if (StringUtils.isBlank(redisShopCart)) {
            redisTemplate.opsForValue().set(REDIS_KEY_SHOP_CART + ":" + userId, cookieShopCart);
            return;
        }

        /**
         * 如果cookie和redis购物车都不为空，则合并
         */

        Map<String, ShopcartBO> redisShopCartMap = JSON.parseArray(redisShopCart, ShopcartBO.class)
                .stream().collect(Collectors.toMap(ShopcartBO::getSpecId, x -> x));
        Map<String, ShopcartBO> cookieShopCartMap = JSON.parseArray(cookieShopCart, ShopcartBO.class)
                .stream().collect(Collectors.toMap(ShopcartBO::getSpecId, x -> x));

        redisShopCartMap.putAll(cookieShopCartMap);
        String result = JSON.toJSONString(redisShopCartMap.entrySet().stream().map(x->x.getValue()).collect(Collectors.toList()));
        redisTemplate.opsForValue().set(REDIS_KEY_SHOP_CART + ":" + userId, result);
        CookieUtils.setCookie(request, response, COOKIE_SHOP_CART, result, true);


    }
}
