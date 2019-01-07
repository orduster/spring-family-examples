package com.ordust;


import com.ordust.entity.Student;
import com.ordust.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * junit 事务默认会回滚，去掉 @Transactional 即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
public class StudentDemo {

    @Autowired
    private StudentService service;

    @Test
    public void testUpdate() {
        Student student = new Student();
        student.setStudentName("哈哈哈");
        student.setAge(22);
        student.setId(3);
        System.out.println(service.testUpdate(student));
    }

    @Test
    public void testBatchUpdate() {
        List<Object[]> batchArgs = new ArrayList<Object[]>();

        batchArgs.add(new Object[]{"2", 22, 2});
        batchArgs.add(new Object[]{"2", 22, 2});
        batchArgs.add(new Object[]{"2", 22, 2});

        int[] ints = service.testBatchUpdate(batchArgs);
        System.out.println(ints[1]);
    }

    @Test
    public void testQueryForObject() {
        System.out.println(service.testQueryForObject(1));
    }

    @Test
    public void testQueryForList() {
        List<Student> students = service.testQueryForList(1);
        for (Student student : students) {
            System.out.println(student);
        }
    }

    @Test
    public void testQueryForObject2() {
        System.out.println(service.testQueryForObject2());
    }

}
