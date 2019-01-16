package com.ordust.controller;

import com.ordust.dao.DepartmentDao;
import com.ordust.dao.EmployeeDao;
import com.ordust.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @ModelAttribute
    public void getEmployee(@RequestParam(value = "id", required = false) Integer id,
                            Map<String, Object> map) {
        if (id != null) {
            map.put("employee", employeeDao.get(id));
        }
    }

    /*提交修改，设置 lastName 不能修改，所以使用 @ModelAttribute 达成目的*/
    @RequestMapping(value = "/emp", method = RequestMethod.PUT)
    public String update(Employee employee) {
        employeeDao.update(employee);
        return "redirect:/emps";
    }

    /*来到更新界面*/
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    public String toUpdatePage(@PathVariable("id") Integer id, Map<String, Object> map) {
        /*回显的数据*/
        map.put("employee", employeeDao.get(id));
        map.put("departments", departmentDao.getAll());
        return "input";
    }

    /*删除数据*/
    @RequestMapping(value = "/emp/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }

    /*添加数据*/
    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    public String add(Employee employee) {
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /*来到添加页面*/
    @RequestMapping(value = "emp", method = RequestMethod.GET)
    public String toAddPage(Map<String, Object> map) {
        map.put("departments", departmentDao.getAll());
        map.put("employee", new Employee());
        return "input";
    }

    /*列表页面*/
    @RequestMapping("/emps")
    public String list(Map<String, Object> map) {
        List<Employee> all = employeeDao.getAll();
        map.put("employees", all);
        return "list";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
