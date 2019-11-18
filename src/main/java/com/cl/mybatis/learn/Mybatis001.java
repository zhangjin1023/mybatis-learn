package com.cl.mybatis.learn;

import com.cl.mybatis.learn.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: chengli
 * @Date: 2018/11/24 12:00
 */
public class Mybatis001 {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println(mapper.selectById(1));
            // 同一个sqlSession内，会走一级缓存
            System.out.println(mapper.selectById(1));
        } finally {
            session.close();
        }

        SqlSession session2 = sqlSessionFactory.openSession();
        try {
            UserMapper mapper2 = session2.getMapper(UserMapper.class);
            // 上一个sqlSession提交之后，查询结果会进入二级缓存，下一个sqlSession即可获取到二级缓存
            System.out.println(mapper2.selectById(1));
        } finally {
            session2.close();
        }
    }
}
