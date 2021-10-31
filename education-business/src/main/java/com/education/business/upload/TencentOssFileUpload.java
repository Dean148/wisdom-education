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
    }

    public static void main(String[] args) {

        String secretId = "AKIDSjlW1GEvLUs3xax4tRijX4LL5XioiNmw";
        String secretKey = "wc3D8oEPKzPZVBblfKjtnTt3xc1A3oJY";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


       String bucket = "test-1253719016"; //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
// 设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try{
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
            System.out.println(bucketResult.getLocation());
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
    }


    @Override
    void doCreateBucket(String name) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(name);
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        cosClient.createBucket(createBucketRequest);
    }
}
