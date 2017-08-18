package com.zl.contoller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zl.User;
import com.zl.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 */
@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/query/{page}/{pageSize}")
    public PageInfo query(@PathVariable Integer page, @PathVariable Integer pageSize) {
        if(page!= null && pageSize!= null){
            PageHelper.startPage(page, pageSize);
        }
        List<User> users = userMapper.list();
        logger.info(String.valueOf(users.size()));
        return new PageInfo(users);
    }
}

