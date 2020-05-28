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
import site.teamo.mall.controller.HelloWorldController;
import site.teamo.mall.controller.PassportController;
import site.teamo.mall.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@MapperScan(basePackages = {"site.teamo.mall.dao"})
public class PassportControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private PassportController passportController;

    @Autowired
    private UserService userService;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(passportController).build();
    }

    @Test
    public void usernameIsExist() throws Exception {
        String result = new String(
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/passport/usernameIsExist?username=hao")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsByteArray());
        Assert.assertEquals(MallJSONResult.ok(), JSON.parseObject(result, MallJSONResult.class));
    }

    @Test
    public void regist() throws Exception {
        String result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/passport/regist")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(
                                UserBo.builder()
                                        .username("mall")
                                        .password("dreamhammer")
                                        .confirmPassword("dreamhammer")
                                        .build()))
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();
        System.out.println(result);
    }

    @Test
    public void test() throws Exception {
        userService.queryUsernameIsExist("mall");
        userService.queryUserForLogin("test","12345");
    }

}
