package site.teamo.mall.dao.provider;

import org.apache.ibatis.jdbc.SQL;
import site.teamo.mall.bean.Student;

public class StudentDao2Provider {
    public String selectOne(String id) {
        String sql = new SQL() {{
            SELECT("id,name,age");
            FROM("student");
            WHERE("id = #{id}");
        }}.toString();
        return sql;
    }

    public String insertOne(Student student) {
        String sql = new SQL() {{
            INSERT_INTO("student");
            if (student == null) {
                throw new RuntimeException("插入数据不能为空");
            }
            VALUES("id","#{id}");
            VALUES("name", "#{name}");
            //添加逻辑判断，如果age不合法，就设置默认15岁
            if (student.getAge() == null || student.getAge() > 100 || student.getAge() < 0) {
                VALUES("age","15");
            }else {
                VALUES("age", "#{age}");
            }
        }}.toString();
        return sql;
    }

    public String updateOne(Student student) {
        String sql = new SQL() {{
            UPDATE("student");
            if (student == null) {
                throw new RuntimeException("更新数据不能为空");
            }
            if (student.getName() != null) {
                SET("name=#{name}");
            }
            if (student.getAge() == null || student.getAge() > 100 || student.getAge() < 0) {
                throw new RuntimeException("学生年龄不能小于0大于100");
            }
            SET("age=#{age}");
            WHERE("id=#{id}");
        }}.toString();
        return sql;
    }

    public String deleteOne(String id) {
        String sql = new SQL() {{
            DELETE_FROM("student");
            WHERE("id=#{id}");
        }}.toString();
        return sql;
    }
}
