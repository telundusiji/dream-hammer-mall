package site.teamo.mall;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.teamo.mall.bean.Student;
import site.teamo.mall.dao.StudentDao1;
import site.teamo.mall.dao.StudentDao2;
import site.teamo.mall.dao.StudentDao3;
import site.teamo.mall.dao.StudentDao4;
import site.teamo.mall.service.StudentService;
import site.teamo.mall.service.impl.StudentServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
//@EnableAutoConfiguration
//@MapperScan(basePackages = {"site.teamo.mall.dao"})
public class MybatisTest {

    @Autowired
    private StudentDao1 studentDao1;

    @Autowired
    private StudentDao2 studentDao2;

    @Autowired
    private StudentDao3 studentDao3;

    @Autowired
    private StudentDao4 studentDao4;

    @Autowired
    private StudentServiceImpl studentService;

    @Test
    public void test1(){
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        student.setAge(20);
        Assert.assertEquals(1L,studentDao1.insertOne(student)*1L);
        Assert.assertEquals(student,studentDao1.selectOne("1"));
        student.setName("李四");
        Assert.assertEquals(1L,studentDao1.updateOne(student)*1L);
        Assert.assertEquals(student,studentDao1.selectOne("1"));
        Assert.assertEquals(1L,studentDao1.deleteOne("1")*1L);
    }

    @Test
    public void test2(){
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        student.setAge(20);
        Assert.assertEquals(1L,studentDao2.insertOne(student)*1L);
        Assert.assertEquals(student,studentDao2.selectOne("1"));
        student.setName("李四");
        Assert.assertEquals(1L,studentDao2.updateOne(student)*1L);
        Assert.assertEquals(student,studentDao2.selectOne("1"));
        Assert.assertEquals(1L,studentDao2.deleteOne("1")*1L);
    }

    @Test
    public void test3(){
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        student.setAge(20);
        Assert.assertEquals(1L,studentDao3.insertOne(student)*1L);
        Assert.assertEquals(student,studentDao3.selectOne("1"));
        student.setName("李四");
        Assert.assertEquals(1L,studentDao3.updateOne(student)*1L);
        Assert.assertEquals(student,studentDao3.selectOne("1"));
        Assert.assertEquals(1L,studentDao3.deleteOne("1")*1L);
    }

    @Test
    public void test4(){
        Student student = new Student();
        student.setId(1);
        student.setName("张三");
        student.setAge(20);
        Assert.assertEquals(1L,studentDao4.insert(student)*1L);
        Assert.assertEquals(student,studentDao4.selectByPrimaryKey("1"));
        student.setName("李四");
        Assert.assertEquals(1L,studentDao4.updateByPrimaryKey(student)*1L);
        Assert.assertEquals(student,studentDao4.mySelectOne("1"));
        Assert.assertEquals(1L,studentDao4.deleteByPrimaryKey(1)*1L);
    }

    @Test
    public void test5(){
        studentService.testMain();
    }
}
