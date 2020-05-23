package site.teamo.mall.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import site.teamo.mall.bean.Student;

@Component
@Mapper
public interface StudentDao1 {

    @Select("select * from student where id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "age", column = "age")
    })
    Student selectOne(@Param("id") String studentId);

    /**
     * 当你的方法参数名和Sql参数名相同时，可以不使用@Param注解，
     * eg:
     *
     * @Select("select * from student where id = #{id}")
     * Student selectOne(String studentId);
     */


    @Insert("insert into student (id,name,age) values(#{id},#{name},#{age})")
    Integer insertOne(Student student);

    @Update("update student set name=#{name}, age=#{age} where id = #{id}")
    Integer updateOne(Student student);

    @Delete("delete from student where id = #{id}")
    Integer deleteOne(String id);
}
