package com.xjhqre.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjhqre.entity.Student;

/**
 * <p>
 * StudentMapper
 * <p>
 *
 * @author xjhqre
 * @since 9月 26, 2023
 */
@Mapper
@TableName("student_his")
public interface StudentHisMapper extends BaseMapper<Student> {}
