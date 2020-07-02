package site.teamo.mall.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import site.teamo.mall.bean.Student;
import site.teamo.mall.dao.StudentDao1;
import site.teamo.mall.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao1 studentDao1;

    @Autowired
    private StudentService studentService;

    @Transactional(propagation = Propagation.REQUIRED,readOnly = true)
    @Override
    public void testMain() {
        TransactionStatus t = TransactionAspectSupport.currentTransactionStatus();
        System.out.println(TransactionAspectSupport.currentTransactionStatus());
insertA1();
        System.out.println(TransactionAspectSupport.currentTransactionStatus());
//        this.insertA1();
//        try {
////            studentService.testB();
//            testB();
//        }catch (Throwable e){
//
//        }

    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void testB() {
        this.insertB1();
        int a = 1/0;
        this.insertB2();
    }

    public void insertA1(){
        Student student = new Student();
        student.setName("a1");
        student.setId(1);
        student.setAge(10);
        studentDao1.insertOne(student);
    }

    public void insertB1(){
        Student student = new Student();
        student.setName("b1");
        student.setId(2);
        student.setAge(10);
        studentDao1.insertOne(student);
    }

    public void insertB2(){
        Student student = new Student();
        student.setName("b2");
        student.setId(3);
        student.setAge(10);
        studentDao1.insertOne(student);
    }
}
