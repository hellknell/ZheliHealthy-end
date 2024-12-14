package com.Yu.his.minio.util;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/10/27 17:31
 */

@Configuration
@ConfigurationProperties(prefix = "minio")
@Slf4j
@Getter
@Setter
public class MinioUtil {
    private String endPoint;
    private String accessKey;
    private String secretKey;
    private String bucket;
    private MinioClient minioClient;

    @PostConstruct
    public void init() {
        log.info(endPoint);
        this.minioClient = new MinioClient.Builder().endpoint(endPoint).credentials(accessKey, secretKey).build();

    }

    public String uploadImage(String path, MultipartFile file) throws IOException {

        try {
            this.minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(path).stream(file.getInputStream(), -1, 5 * 1024 * 1024).build());
            log.info("向{}发送了文件", path);
            return path;
        } catch (Exception e) {
            log.error("保存文件失败");
        }
        return null;
    }

    public void updateExcel(String path, MultipartFile file) {
        String mime = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        try {
            this.minioClient.putObject(PutObjectArgs.builder().bucket(bucket).object(path).contentType(mime).stream(file.getInputStream(), -1, 20 * 1024 * 1024).build());
            log.info("向{}发送文件成功", path);
        } catch (Exception e) {
            log.error("上传excel文档失败");
        }
    }

    public InputStream downloadExcel(String path) {
        try {
            GetObjectArgs args = GetObjectArgs.builder().bucket(bucket).object(path).build();
            return minioClient.getObject(args);
        } catch (Exception e) {
            log.error("下载excel文件失败");
        }
        return null;
    }

    public void deleteFiles(String path) throws Exception {
        try {
            this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucket).object(path).build());
        } catch (Exception e) {
            log.error("下载excel文件失败");
            throw new Exception("文件删除失败");
        }


    }
}
