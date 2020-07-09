package site.teamo.mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

//@ApiIgnore
@RestController
public class HelloWorldController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/helloWorld")
    public Object helloWorld() {
        return "hello world";
    }

    @GetMapping("/redis/set")
    public Object set(@RequestParam String key, @RequestParam String value) {
        redisTemplate.opsForValue().set(key, value);
        return "ok";
    }

    @GetMapping("/redis/get")
    public Object get(@RequestParam String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @GetMapping("/redis/delete")
    public Object delete(@RequestParam String key) {
        return redisTemplate.delete(key);
    }
}
