package com.springboot.music.service.impl;

import com.springboot.music.dao.AdminDao;
import com.springboot.music.domain.Admin;
import com.springboot.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao adminDao;


    @Override
    public boolean veritypasswd(String name, String password) {
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(password);
        return adminDao.verifyPassword(admin)>0?true:false;
    }
}
