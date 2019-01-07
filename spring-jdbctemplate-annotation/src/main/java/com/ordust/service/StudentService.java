package com.ordust.service;

import com.ordust.dao.StudentDao;
import com.ordust.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentDao dao;

    public int testUpdate(Student student) {
        return dao.testUpdate(student);
    }

    public int[] testBatchUpdate(List<Object[]> batchArgs) {
        return dao.testBatchUpdate(batchArgs);
    }

    public Student testQueryForObject(int id) {
        return dao.testQueryForObject(id);
    }


    public List<Student> testQueryForList(Integer id) {
        return dao.testQueryForList(id);
    }


    public Integer testQueryForObject2() {
        return dao.testQueryForObject2();
    }
}
