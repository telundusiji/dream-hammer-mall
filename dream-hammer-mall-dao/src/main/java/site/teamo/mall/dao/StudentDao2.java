package site.teamo.mall.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Student;
import site.teamo.mall.dao.provider.StudentDao2Provider;

@Component
@Mapper
public interface StudentDao2 {

    @SelectProvider(type = StudentDao2Provider.class, method = "selectOne")
    Student selectOne(String studentId);

    @InsertProvider(type = StudentDao2Provider.class,method = "insertOne")
    Integer insertOne(Student student);

    @UpdateProvider(type = StudentDao2Provider.class,method = "updateOne")
    Integer updateOne(Student student);

    @DeleteProvider(type = StudentDao2Provider.class,method = "deleteOne")
    Integer deleteOne(String id);
}
