import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xjhqre.MybatisPlusApp;
import com.xjhqre.entity.Student;
import com.xjhqre.mapper.StudentHisMapper;
import com.xjhqre.mapper.StudentMapper;

/**
 * <p>
 * MybatisPlusTest
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MybatisPlusApp.class})
public class MybatisPlusTest {

    @Resource
    StudentMapper studentMapper;
    @Resource
    StudentHisMapper studentHisMapper;

    @Test
    public void test2() {
        Student student = new Student();
        student.setId(3L);
        student.setName("cs2");
        student.setAge(181);
        studentHisMapper.insert(student);
    }

    @Test
    public void test1() {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getId, 1);
        Student student = studentMapper.selectOne(wrapper);
        student.setName("asdasd");
    }
}
