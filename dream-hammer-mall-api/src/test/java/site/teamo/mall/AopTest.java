package site.teamo.mall;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import site.teamo.mall.bean.bo.UserBo;
import site.teamo.mall.common.util.MallJSONResult;
import site.teamo.mall.controller.PassportController;
import site.teamo.mall.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@MapperScan(basePackages = {"site.teamo.mall.dao"})
public class AopTest {

    @Autowired
    private UserService userService;

    @Test
    public void aopTest() throws Exception {
        userService.queryUsernameIsExist("mall");
        userService.queryUserForLogin("test","12345");
    }

}
