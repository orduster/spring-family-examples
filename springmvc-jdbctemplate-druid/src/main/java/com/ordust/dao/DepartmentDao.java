package com.ordust.dao;

import com.ordust.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*保存操作*/
    public int save(Department department) {
        String sql = "INSERT INTO department(id,dept_name) VALUES(NULL,?)";
        return jdbcTemplate.update(sql, department.getDepartmentName());
    }

    /*得到全部*/
    public List<Department> getAll() {
        String sql = "SELECT id,dept_name departmentName FROM department";
        BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        return jdbcTemplate.query(sql, rowMapper);
    }

    /*根据id获取*/
    public Department get(Integer id) {
        String sql = "SELECT id,dept_name departmentName FROM department WHERE id=?";
        BeanPropertyRowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
