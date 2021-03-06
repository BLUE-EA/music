package com.springboot.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.music.domain.Singer;
import com.springboot.music.service.SingerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@Controller
@CrossOrigin
public class SingerController {

    @Resource
    private SingerService singerService;

    @Configuration
    public class PicConfig implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                    "file:"+System.getProperty("user.dir")  + System.getProperty("file.separator") + "server" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singerPic" + System.getProperty("file.separator")
            );
        }
    }


    //    返回所有歌手
    @RequestMapping(value = "/singer", method = RequestMethod.GET)
    public Object allSinger(){
        return singerService.allSinger();
    }

    //根据id检索歌手
    @RequestMapping(value = "/singer/detail", method = RequestMethod.GET)
    public Object singer(HttpServletRequest req){
        String id = req.getParameter("singerId");
        return singerService.selectByPrimaryKey(Integer.parseInt(id));
    }

    /*
     * 添加歌手
     */
    @ResponseBody
    @RequestMapping(value = "/singer/add", method = RequestMethod.POST)
    public Object addSinger(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String name = req.getParameter("name").trim();
        String sex = req.getParameter("sex").trim();
        String pic = req.getParameter("pic").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String introduction = req.getParameter("introduction").trim();

        Singer singer = new Singer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        }catch (Exception e){
            e.printStackTrace();
        }
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setPic(pic);
        singer.setBirth(myBirth);
        singer.setLocation(location);
        singer.setIntroduction(introduction);

        boolean res = singerService.insert(singer);
        if (res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功");
            return jsonObject;
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败");
            return jsonObject;
        }
    }

    //    更新歌手信息
    @ResponseBody
    @RequestMapping(value = "/singer/update", method = RequestMethod.POST)
    public Object updateSingerMsg(HttpServletRequest req){
        JSONObject jsonObject = new JSONObject();
        String id = req.getParameter("id").trim();
        String name = req.getParameter("name").trim();
        String sex = req.getParameter("sex").trim();
        String pic = req.getParameter("pic").trim();
        String birth = req.getParameter("birth").trim();
        String location = req.getParameter("location").trim();
        String introduction = req.getParameter("introduction").trim();

        Singer singer = new Singer();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date myBirth = new Date();
        try {
            myBirth = dateFormat.parse(birth);
        }catch (Exception e){
            e.printStackTrace();
        }
        singer.setId(Integer.parseInt(id));
        singer.setName(name);
        singer.setSex(new Byte(sex));
        singer.setPic(pic);
        singer.setBirth(myBirth);
        singer.setLocation(location);
        singer.setIntroduction(introduction);

        boolean res = singerService.updateByPrimaryKey(singer);
        if (res){
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
            return jsonObject;
        }else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败");
            return jsonObject;
        }
    }

    @RequestMapping("/singer/delete")
    public Object deleteSinger(HttpServletRequest request){
        String id = request.getParameter("id");
        JSONObject jsonObject = new JSONObject();
        boolean res = singerService.deleteByPrimaryKey(Integer.parseInt(id));
        if (res){
            jsonObject.put("code",1);
            jsonObject.put("msg", "删除成功");
            return jsonObject;
        }
        jsonObject.put("code",0);
        jsonObject.put("msg", "删除失败");
        return jsonObject;
    }

    //    更新歌手头像
    @ResponseBody
    @RequestMapping(value = "/singer/avatar/update", method = RequestMethod.POST)
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @RequestParam("id")int id){
        System.out.println("修改图片");
        JSONObject jsonObject = new JSONObject();

        if (avatarFile.isEmpty()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "文件上传失败！");
            return jsonObject;
        }
        String fileName = System.currentTimeMillis()+avatarFile.getOriginalFilename();//防止文件名重复
        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "server" + System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + "singerPic" ;
        File file1 = new File(filePath);
        if (!file1.exists()){
            file1.mkdir();//img/singerPic文件夹不存在会自动创建
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeAvatarPath = "/img/singerPic/"+fileName;
        try {
            avatarFile.transferTo(dest); //执行上传
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(storeAvatarPath);
            boolean res = singerService.updateSingerPic(singer);//修改数据库，将相对地址写入数据库
            if (res){
                jsonObject.put("code", 1);
                jsonObject.put("pic", storeAvatarPath);
                jsonObject.put("msg", "上传成功");
                return jsonObject;
            }else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "上传失败");
                return jsonObject;
            }
        }catch (IOException e){
            jsonObject.put("code", 0);
            jsonObject.put("msg", "上传失败" + e.getMessage());
            return jsonObject;
        }finally {
            return jsonObject;
        }
    }
}
