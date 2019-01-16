package com.ordust.dao;

import com.ordust.domain.Employee;
import com.ordust.rowmapper.EmployeeRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /*更新数据*/
    public int update(Employee employee) {
        String sql = "UPDATE employee SET email=?,gender=?,dept_id=? WHERE id=?";
        return jdbcTemplate.update(sql, employee.getEmail(), employee.getGender(), employee.getDepartment().getId(), employee.getId());
    }

    /*保存操作*/
    public int save(Employee employee) {
        String sql = "INSERT INTO employee(id,last_name,email,gender,dept_id) VALUES(NULL,?,?,?,?)";
        return jdbcTemplate.update(sql, employee.getLastName(), employee.getEmail(), employee.getGender(), employee.getDepartment().getId());
    }

    /*得到全部数据 */
    public List<Employee> getAll() {
        String sql = "SELECT emp.id,emp.last_name ,emp.email,emp.gender,dep.id dept_id,dep.dept_name FROM employee emp JOIN department dep ON(emp.dept_id=dep.id)";
        return jdbcTemplate.query(sql, new EmployeeRowMapper());
    }

    /*根据id查询数据*/
    public Employee get(Integer id) {
        String sql = "SELECT emp.id,emp.last_name ,emp.email,emp.gender,dep.id dept_id,dep.dept_name FROM employee emp JOIN department dep ON(emp.dept_id=dep.id AND emp.id=?)";
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), id);
    }

    /*根据id删除数据*/
    public int delete(Integer id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

}
