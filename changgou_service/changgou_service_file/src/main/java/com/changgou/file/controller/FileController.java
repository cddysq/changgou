package com.changgou.file.controller;

import cn.hutool.core.util.StrUtil;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.file.util.FastDFSClient;
import com.changgou.file.util.FastDFSFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @Author: Haotian
 * @Date: 2020/1/26 19:54
 * @Description: 文件上传接口
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public Result<Object> uploadFile(MultipartFile file) {
        try {
            //判断文件是否存在
            if (file == null) {
                throw new RuntimeException( "文件不存在" );
            }
            //获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StrUtil.isEmpty( originalFilename )) {
                throw new RuntimeException( "文件不存在" );
            }
            //获取文件的扩展名称 abc.jpg jpg
            String extName = Objects.requireNonNull( originalFilename ).substring( originalFilename.lastIndexOf( "." ) + 1 );
            //获取文件内容
            byte[] content = file.getBytes();
            //创建文件上传的封装实体类
            FastDFSFile fastdfsfile = FastDFSFile.builder().name( originalFilename ).content( content ).ext( extName ).build();
            //基于工具类进行文件上传,并接受返回参数 String[]
            String[] uploadResult = FastDFSClient.upload( fastdfsfile );
            //封装返回结果
            String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];
            return Result.builder().flag( true ).code( StatusCode.OK ).message( "文件上传成功" ).data( url ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.builder().flag( false ).code( StatusCode.ERROR ).message( "文件上传失败" ).build();
        }
    }
}