package com.springboot.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.music.domain.Consumer;
import com.springboot.music.service.impl.ConsumerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Controller
@CrossOrigin
public class ConsumerController {

    @Autowired
    private ConsumerServiceImpl consumerService;

    @Configuration
    public class MyPicConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/avatarImages/**").addResourceLocations(
                    "file:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "server" + System.getProperty("file.separator") + "avatarImages" + System.getProperty("file.separator")
            );
        }
    }

    //    添加用户
    @ResponseBody
    @RequestMapping(value = "/user/add", method = RequestMethod.POST)
    public Object addUser(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username").trim();
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();
        String avatar = req.getParameter("avatar").trim();

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
        Consumer consumer = new Consumer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        if (phone_num == "") {
            consumer.setPhoneNum(null);
        } else {
            consumer.setPhoneNum(phone_num);
        }

        if (email == "") {
            consumer.setEmail(null);
        } else {
            consumer.setEmail(email);
        }
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setAvatar(avatar);
        consumer.setCreateTime(new Date());
        consumer.setUpdateTime(new Date());

        boolean repetition = consumerService.existUsername(username);
        if (repetition) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败,该用户名已注册");
            return jsonObject;
        }
        boolean res = consumerService.addUser(consumer);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "注册成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "注册失败");
            return jsonObject;
        }
    }

    //    判断是否登录成功
    @ResponseBody
    @RequestMapping(value = "/user/login/status", method = RequestMethod.POST)
    public Object loginStatus(HttpServletRequest req, HttpSession session) {

        JSONObject jsonObject = new JSONObject();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
//        System.out.println(username+"  "+password);
        boolean res = consumerService.verifyPassword(username, password);

        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功");
            jsonObject.put("userMsg", consumerService.loginStatus(username));
            session.setAttribute("username", username);
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }

    }

    //    返回所有用户
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object allUser() {
        return consumerService.allUser();
    }

    //    返回指定ID的用户
    @RequestMapping(value = "/user/detail", method = RequestMethod.GET)
    public Object userOfId(HttpServletRequest req) {
        String id = req.getParameter("id");
        Consumer com = consumerService.selectByPrimaryKey(Integer.parseInt(id));
        return com;
    }

    //    删除用户
    @RequestMapping(value = "/user/delete", method = RequestMethod.GET)
    public Object deleteUser(HttpServletRequest req) {
        String id = req.getParameter("id");
        return consumerService.deleteUser(Integer.parseInt(id));
    }

    //    更新用户信息
    @ResponseBody
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public Object updateUserMsg(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = new Consumer();
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        System.out.println(req.getParameter("password"));
        String password = req.getParameter("password").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();
//        String avatar = req.getParameter("avatar").trim();
//        System.out.println(username+"  "+password+"  "+sex+"   "+phone_num+"     "+email+"      "+birth+"       "+introduction+"      "+location);

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setPassword(password);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phone_num);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
//        consumer.setAvatar(avatar);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateUserMsg(consumer);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

    //    更新用户信息
    @ResponseBody
    @RequestMapping(value = "user/updateInfo", method = RequestMethod.POST)
    public Object updateUserInfo(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = new Consumer();
        String id = req.getParameter("id").trim();
        String username = req.getParameter("username").trim();
        String sex = req.getParameter("sex").trim();
        String phone_num = req.getParameter("phone_num").trim();
        String email = req.getParameter("email").trim();
        String birth = req.getParameter("birth").trim();
        String introduction = req.getParameter("introduction").trim();
        String location = req.getParameter("location").trim();

        if (username.equals("") || username == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        consumer.setId(Integer.parseInt(id));
        consumer.setUsername(username);
        consumer.setSex(new Byte(sex));
        consumer.setPhoneNum(phone_num);
        consumer.setEmail(email);
        consumer.setBirth(myBirth);
        consumer.setIntroduction(introduction);
        consumer.setLocation(location);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateUserMsg(consumer);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

    //    更新用户密码
    @ResponseBody
    @RequestMapping(value = "user/updatePassword", method = RequestMethod.POST)
    public Object updateUserPassword(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Consumer consumer = new Consumer();
        String id = req.getParameter("id").trim();
        String password = req.getParameter("password").trim();
        consumer.setId(Integer.parseInt(id));
        consumer.setPassword(password);
        consumer.setUpdateTime(new Date());

        boolean res = consumerService.updateUserMsg(consumer);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

    //    更新用户头像
    @ResponseBody
    @RequestMapping(value = "/user/avatar/update", method = RequestMethod.POST)
    public Object updateUserPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id") int id) {
        JSONObject jsonObject = new JSONObject();

        if (avatarFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "server" + System.getProperty("file.separator") + "avatarImages";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatarPath = "/avatarImages/" + fileName;
        try {
            avatarFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setId(id);
            consumer.setAvatar(storeAvatarPath);
            boolean res = consumerService.updateUserAvatar(consumer);
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("avatar", storeAvatarPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        } catch (IOException e) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
            return jsonObject;
        } finally {
            return jsonObject;
        }
    }
}
