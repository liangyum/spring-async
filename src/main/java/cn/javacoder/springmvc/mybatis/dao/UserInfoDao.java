package cn.javacoder.springmvc.mybatis.dao;

import cn.javacoder.springmvc.mybatis.entity.User;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aijun.fu@mtime.com
 * Date:2016/8/17
 * Time:15:48
 */

@Repository
public class UserInfoDao extends AbstractDao {

    public User selectUserById(int userId) {
        return sqlTpl.selectOne("cn.javacoder.springmvc.mybatis.entity.UserMapper.selectUserById",userId);
    }

    @Transactional
    public void insertUser(User user) {
        int i = sqlTpl.insert("cn.javacoder.springmvc.mybatis.entity.UserMapper.insertUser",user);
        throw new RuntimeException();
    }

    /**
     * @param user
     * @return
     */
    public List<User> selectUserByName(User user) {
        return sqlTpl.selectList("cn.javacoder.springmvc.mybatis.entity.UserMapper.selectUserByName",user);
    }
    
    public List<User> selectSlaveUserByName(User user) {
        return sqlTplSlave.selectList("cn.javacoder.springmvc.mybatis.entity.UserMapper.selectUserByName",user);
    }

    /**
     * -异步方法：无返回值
     */
    @Async
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

    /**
     * -异步方法：有返回值
     */
    @Async
    public Future<String> doAsyncAndReturn() {
        try {
            System.err.println("睡眠3秒开始" + System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(3);
            System.err.println("睡眠3秒结束" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("异步操作：" + Thread.currentThread().getName());
        
        return new AsyncResult<String>(Thread.currentThread().getName());
    }
}
