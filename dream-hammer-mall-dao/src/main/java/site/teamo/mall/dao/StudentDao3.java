package site.teamo.mall.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Student;
import site.teamo.mall.dao.provider.StudentDao2Provider;

@Mapper
@Component
public interface StudentDao3 {

    Student selectOne(String studentId);

    Integer insertOne(Student student);

    Integer updateOne(Student student);

    Integer deleteOne(String id);
}
