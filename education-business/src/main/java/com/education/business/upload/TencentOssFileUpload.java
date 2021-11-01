package com.education.business.upload;

import com.education.common.config.OssProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.util.List;


/**
 * 腾讯oss 上传
 * @author zengjintao
 * @create_at 2021/10/31 14:52
 * @since version 1.0.3
 */
public class TencentOssFileUpload extends BaseFileUpload {

    private COSClient cosClient;

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
    }

    @Override
    void doCreateBucket(String name) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(name);
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        cosClient.createBucket(createBucketRequest);
    }

    @Override
    public UploadResult putObject(String filePath, String fileName) {
        cosClient.shutdown();
        return null;
    }

    @Override
    public UploadResult putObject(String bucket, String filePath, String fileName) {
        return null;
    }
}
