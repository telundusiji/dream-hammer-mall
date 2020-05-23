package site.teamo.mall.dao;

import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Student;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

@Component
public interface StudentDao4 extends Mapper<Student>, MySqlMapper<Student> {
    /**
     * 通用Mapper中有很多基础的crud方法，基本查询就可以使用通用Mapper中的方法
     * 当你有复杂的逻辑业务sql时，也可以自定义方法，如下就是自定义了一个演示方法
     */
    Student mySelectOne(String id);
}
