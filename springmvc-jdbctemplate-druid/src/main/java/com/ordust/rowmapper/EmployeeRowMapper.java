package com.ordust.rowmapper;

import com.ordust.domain.Department;
import com.ordust.domain.Employee;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 实现自定义的 RowMapper
 */
public class EmployeeRowMapper implements RowMapper<Employee>{
    @Nullable
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setLastName(rs.getString("last_name"));
        employee.setEmail(rs.getString("email"));
        employee.setGender(rs.getInt("gender"));
        Department department = new Department(rs.getInt("dept_id"), rs.getString("dept_name"));
        employee.setDepartment(department);
        return employee;
    }
}
