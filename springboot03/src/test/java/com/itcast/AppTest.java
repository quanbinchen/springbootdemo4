package com.itcast;

import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itcast.entity.User;
import com.itcast.repo.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AppTest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public void test1() throws JsonProcessingException {

        //从redis缓存中获得指定的数据
        String userListData = redisTemplate.boundValueOps("user.findAll").get();
        //如果redis中没有数据的话，就查询数据库
        if (null == userListData) {
            System.out.println("---------从数据库中查询start--------------");
            List<User> list = userRepo.findAll();
            for (User user : list) {
                System.out.println(user);
            }
            System.out.println("----------从数据库中查询end----------------------");
            //转换成json格式字符串
            ObjectMapper om = new ObjectMapper();
            userListData = om.writeValueAsString(list);
            //将数据存储到redis中，下次在查询直接从redis中获得数据，不用在查询数据库
            redisTemplate.boundValueOps("user.findAll").set(userListData);
        }else{
            System.out.println("-------------从redis缓存中得到数据------------------");
            System.out.println(userListData);
        }


    }

}
