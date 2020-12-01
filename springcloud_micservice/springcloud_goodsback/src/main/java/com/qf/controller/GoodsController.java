package com.qf.controller;

import com.qf.entity.GoodsEntity;
import com.qf.entity.GoodsSecondKillEntity;
import com.qf.entity.ResultData;
import com.qf.feign.GoodsFeign;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 后台商品管理控制层
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    //设置图片的上传的路径存在本地磁盘中
    String uploadPath="C:/worker/imgs";
    //注入远程调用的微服务
    @Autowired
    private GoodsFeign goodsFeign;

    //商品展示参与分页
    //查询参与分页
    @RequestMapping("/list")
    public String list(Model model){

        List<GoodsEntity> goodsEntityList = goodsFeign.goodsList();
        model.addAttribute("list",goodsEntityList);
        return "goodslist";
    }


    /**
     * 添加商品的服务
     * @param goodsEntity
     * @param goodsSecondKillEntity
     * @return
     */
    @RequestMapping("/insert")
    public String insert(GoodsEntity goodsEntity, GoodsSecondKillEntity goodsSecondKillEntity){

        goodsEntity.setGoodsSecondKillEntity(goodsSecondKillEntity);
        //进行添加的操作
        goodsFeign.insertGoods(goodsEntity);

        //重定向返回页面 绝对路径
        return "redirect:http://localhost/back/goods/list";

    }

    /**
     * 根据商品id删除商品
     * @param gid
     * @return
     */
    @RequestMapping("/delete")
    public String delete(Integer gid){
        goodsFeign.deleteById(gid);
        return "redirect:http://localhost/back/goods/list";
    }

    /**
     * 上传图片的操作
     * @return
     */
    @RequestMapping("/uploader")
    @ResponseBody
    public ResultData<String> uploader(MultipartFile file){

        System.out.println("触发了图片上传:"+file.getOriginalFilename());
        //为上传的图片重命名
        String fileName = UUID.randomUUID().toString();
        String path=uploadPath+"/"+fileName;

        //进行读写的操作
        try(InputStream inputStream = file.getInputStream();
            FileOutputStream outputStream=new FileOutputStream(path);
        ) {
            //进行拷贝的操作
            IOUtils.copy(inputStream,outputStream);
            //上传成功
            return new ResultData<String>().setCode(ResultData.ResultCodeList.OK).setData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultData<String>().setCode(ResultData.ResultCodeList.ERROR);
    }

    /**
     * 图片回显
     * @param imgPath
     * @param response
     */
    @RequestMapping("/showimg")
    @ResponseBody
    public void  showingImages(String imgPath, HttpServletResponse response){
        //根据图片的路径读取本地文件
        try (FileInputStream fileInputStream=new FileInputStream(imgPath);
             //serlet输出流
             ServletOutputStream outputStream=response.getOutputStream();
        ){
            //文件的拷贝
            IOUtils.copy(fileInputStream,outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
