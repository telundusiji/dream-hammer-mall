package site.teamo.mall;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import site.teamo.mall.controller.HelloWorldController;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class HelloWorldTest {

    private MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
    }

    @Test
    public void testHelloWorld() throws Exception {
        String result = new String(
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/helloWorld")
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                        .andDo(print())
                        .andReturn()
                        .getResponse()
                        .getContentAsByteArray());
        Assert.assertEquals("hello world", result);
    }

}
