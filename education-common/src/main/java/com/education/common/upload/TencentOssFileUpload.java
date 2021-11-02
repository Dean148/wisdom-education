package com.education.common.upload;

import com.education.common.config.OssProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import java.io.File;
import java.io.InputStream;


/**
 * 腾讯oss 上传
 * @author zengjintao
 * @create_at 2021/10/31 14:52
 * @since version 1.0.3
 */
public class TencentOssFileUpload extends BaseFileUpload {

    private COSClient cosClient;

    private String ossHost;

    public TencentOssFileUpload(OssProperties ossProperties, String env, String applicationName) {
        super(ossProperties, env, applicationName);
        this.initCOSClient();
    }

    private void initCOSClient() {
        String secretId = ossProperties.getSecretId();
        String secretKey = ossProperties.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(ossProperties.getRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        this.cosClient = new COSClient(cred, clientConfig);
        boolean flag = cosClient.doesBucketExist(this.parentBucketName);
        if (!flag) {
            this.createBucket(parentBucketName);
        }
        this.ossHost = "https://" + parentBucketName;
    }

    @Override
    UploadResult doCreateBucket(String name) {
        try {
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(name);
            createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
            cosClient.createBucket(createBucketRequest);
            return new UploadResult();
        } finally {
            closeClient();
        }
    }

    private void closeClient() {
        if (cosClient != null) {
            cosClient.shutdown();
        }
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, File file) {
        return this.putObject(parentBucketName, filePath, fileName, file);
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, File file) {
        try {
            String fileKey = super.generateFileKey(filePath) + fileName;
            this.cosClient.putObject(bucket, fileKey, file);
            String fileUrl = ossHost + fileKey;
            return new UploadResult(fileUrl);
        } finally {
            this.closeClient();
        }
    }

    @Override
    public UploadResult putObject(String filePath, String fileName, InputStream inputStream) {
        return this.putObject(parentBucketName, filePath, fileName, inputStream);
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName, InputStream inputStream) {
        try {
            String fileKey = super.generateFileKey(filePath) + fileName;
            ObjectMetadata objectMetadata = new ObjectMetadata();
            this.cosClient.putObject(bucket, fileKey, inputStream, objectMetadata);
            String fileUrl = ossHost + fileKey;
            return new UploadResult(fileUrl);
        } finally {
            closeClient();
        }
    }


    @Override
    public void deleteObject(String filePath) {
        this.deleteObject(parentBucketName, filePath);
    }

    @Override
    public void deleteObject(String bucket, String filePath) {
        try {
            this.cosClient.deleteObject(bucket, filePath);
        } finally {
            closeClient();
        }
    }
}
