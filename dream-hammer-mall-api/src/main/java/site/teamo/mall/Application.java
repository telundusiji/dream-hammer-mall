package site.teamo.mall;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = {"site.teamo.mall","org.n3r.idworker"})
@MapperScan(basePackages = {"site.teamo.mall.dao"})

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
