package com.ordust.dao;


import com.ordust.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //标识为持久层组件
public class StudentDao {

    @Autowired //自动装配jdbcTemplate
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加、修改、删除一个student对象
     * 此处只实现 修改
     *
     * @param student 学生实体类
     * @return 返回操作结果
     */
    public int testUpdate(Student student) {
        String sql = "update student set name = ?, age = ? where id = ?";
        return jdbcTemplate.update(sql, student.getStudentName(), student.getAge(), student.getId());
    }

    /**
     * 执行批量更新：批量的 INSERT, UPDATE, DELETE
     *
     * @param batchArgs Object[] 的 list 集合
     * @return 返回操作结果
     */
    public int[] testBatchUpdate(List<Object[]> batchArgs) {
        String sql = "INSERT INTO student VALUES(null,?,?,?)";
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 从数据库中读取一条记录，得到对应的一个对象
     * 注意使用的方法为 queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     *
     * @param id 需要查找的id
     * @return 返回student对象
     */
    public Student testQueryForObject(int id) {
        /**
         * 1、使用 sql 中的列的别名完成列名和类的属性名的映射 如下面sql语句：name studentName
         * 2、JdbcTemplate 不支持级联属性，此处 teacher.id 值为空
         */
        String sql = "SELECT id, name studentName , age, teacher_id AS `teacher.id` FROM student WHERE id=?";
        BeanPropertyRowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    /**
     * 得到实体类集合
     * 注意调用的不是 queryForList 方法
     *
     * @param id 参数
     * @return student 集合
     */
    public List<Student> testQueryForList(Integer id) {
        String sql = "SELECT id, name studentName, age FROM student WHERE id > ?";
        BeanPropertyRowMapper<Student> rowMapper = new BeanPropertyRowMapper<Student>(Student.class);
        return jdbcTemplate.query(sql, rowMapper, id);
    }

    /**
     * 获取单个列的值，或做统计查询
     * 使用 queryForObject(String sql, Class<T> requiredType) 方法
     */
    public Integer testQueryForObject2() {
        String sql = "select  count(*) from student";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

}
