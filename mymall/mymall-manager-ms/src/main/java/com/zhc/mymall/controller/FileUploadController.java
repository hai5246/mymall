package com.zhc.mymall.controller;

import com.zhc.mymall.pojo.RespBean;
import io.minio.MinioClient;
import io.minio.policy.PolicyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileUploadController {
    /*
    # MinIO对象存储相关配置
    minio:
      endpoint:  http://127.0.0.1:9000 #MinIO服务所在地址
      bucketName: mall #存储桶名称
      accessKey: minioadmin #访问的key
      secretKey: minioadmin #访问的秘钥
     */
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @PostMapping("/uploadFile")
    public RespBean uploadFile(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        try {
            //先创建一个minio的文件上传客户端
            MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
            //创建文件存储桶
            boolean isExist = minioClient.bucketExists(bucketName);
            if (!isExist){
                minioClient.makeBucket(bucketName);
                minioClient.setBucketPolicy(bucketName,"*.*", PolicyType.READ_ONLY);
            }
            //设置存储对象的名称
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String filename = file.getOriginalFilename();
            String objectName = sdf.format(new Date()) + "/" + filename;

            //把存储对象出上传至存储桶中
            minioClient.putObject(bucketName, objectName, file.getInputStream(), file.getContentType());

            //文件的访问地址
            String objectUrl = minioClient.getObjectUrl(bucketName, objectName);

            System.out.println(objectUrl);

            return RespBean.ok(objectUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("文件上传失败");
        }
    }
}
