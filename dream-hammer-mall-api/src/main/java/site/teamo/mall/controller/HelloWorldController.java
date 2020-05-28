package site.teamo.mall.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HelloWorldController {

    @GetMapping("/helloWorld")
    public Object helloWorld(){
        return "hello world";
    }
}
