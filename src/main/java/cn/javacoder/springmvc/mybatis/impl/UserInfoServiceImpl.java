package cn.javacoder.springmvc.mybatis.impl;

import cn.javacoder.springmvc.mybatis.dao.UserInfoDao;
import cn.javacoder.springmvc.mybatis.entity.User;
import cn.javacoder.springmvc.mybatis.service.UserInfoService;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by aijun.fu@mtime.com
 * Date:2016/8/17
 * Time:15:50
 */

@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public User selectUserById(int userId) {
        return userInfoDao.selectUserById(userId);
    }

    @Override
    public void insertUser(User user) {
        userInfoDao.insertUser(user);
    }

    @Async
    @Override
    public void doAsync() {
        try {
            System.err.println("睡眠3秒开始" + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(3);
            System.err.println("睡眠3秒结束" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("异步操作：" + Thread.currentThread().getName());
    }
}
