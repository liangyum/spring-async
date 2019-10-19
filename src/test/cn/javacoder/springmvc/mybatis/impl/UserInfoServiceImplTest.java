package cn.javacoder.springmvc.mybatis.impl;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.javacoder.springmvc.mybatis.dao.UserInfoDao;
import cn.javacoder.springmvc.mybatis.entity.User;
import cn.javacoder.springmvc.mybatis.service.UserInfoService;

/**
 * Created by aijun.fu@mtime.com
 * Date:2016/8/22
 * Time:19:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:etc/spring/spring-servlet.xml","classpath:etc/spring/spring-mybatis.xml"})
public class UserInfoServiceImplTest {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void testAsyncWithReturn() throws Exception {
        Future<String> future = userInfoDao.doAsyncAndReturn();
        System.err.println("主操作：" + Thread.currentThread().getName());
        System.err.println("异步返回结果：" + future.get());
        // 不让主进程过早结束
        TimeUnit.SECONDS.sleep(5);
        
//      输出结果：
//        主操作：main
//        睡眠3秒开始1571498430856
//        睡眠3秒结束1571498433856
//        异步操作：annotationExecutor-1
//        异步返回结果：annotationExecutor-1
    }
    @Test
    public void testAsyncWithoutReturn() throws Exception {
        userInfoDao.doAsync();
        System.err.println("主操作：" + Thread.currentThread().getName());
        // 不让主进程过早结束
        TimeUnit.SECONDS.sleep(5);
        
//      输出结果：
//        主操作：main
//        睡眠3秒开始1571498228001
//        睡眠3秒结束1571498231001
//        异步操作：annotationExecutor-1
    }
    @Test
    public void testSelectUserById() throws Exception {
        User user = userInfoService.selectUserById(1);
        System.err.println(user);
    }
    @Test
    public void testSelectUserByName() throws Exception {
        User user = new User();
        user.setUserName("a");
        List<User> users = userInfoDao.selectUserByName(user);
        System.err.println(users);
    }
    @Test
    public void testSelectSlaveUserByName() throws Exception {
        User user = new User();
        user.setUserName("a");
        List<User> users = userInfoDao.selectSlaveUserByName(user);
        System.err.println(users);
    }

    @Test
    public void testInsertUser() throws Exception {
        User user = new User();
        user.setUserName("mtime");
        user.setPassword("mtime123456");
        String result = "";
        try{
            userInfoService.insertUser(user);
            result = " OK!";
        }catch(Exception e){
            result = "Roll Back";
        }
        System.out.println(result);
    }
}