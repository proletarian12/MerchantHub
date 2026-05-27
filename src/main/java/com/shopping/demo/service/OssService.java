package com.shopping.demo.service;

import com.aliyun.sts20150401.Client;
import com.aliyun.sts20150401.models.AssumeRoleRequest;
import com.aliyun.sts20150401.models.AssumeRoleResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OssService {

    @Autowired
    private com.aliyun.credentials.Client credentialClient;

    // 从配置文件读取
    private static final String REGION_ID = "cn-hangzhou";
    private static final String ROLE_ARN = "acs:ram::123456789012:role/oss-upload-role";
    private static final String BUCKET_NAME = "your-bucket-name";
    private static final String ENDPOINT = "oss-cn-hangzhou.aliyuncs.com";
    private static final String UPLOAD_DIR = "products/";
    private static final Long DURATION_SECONDS = 900L;

    /**
     * 生成 OSS 临时上传凭证（新版 STS SDK V2）
     */
    public Map<String, Object> generateOssToken() {
        try {
            // 1. 初始化 STS Client
            Config config = new Config()
                    .setCredential(credentialClient)
                    .setRegionId(REGION_ID);
            Client stsClient = new Client(config);

            // 2. 构造权限策略：只允许上传到指定目录
            String policy = "{"
                    + "  \"Version\": \"1\","
                    + "  \"Statement\": [{"
                    + "    \"Effect\": \"Allow\","
                    + "    \"Action\": [\"oss:PutObject\"],"
                    + "    \"Resource\": [\"acs:oss:*:*:" + BUCKET_NAME + "/" + UPLOAD_DIR + "*\"]"
                    + "  }]"
                    + "}";

            // 3. 构造请求
            AssumeRoleRequest request = new AssumeRoleRequest()
                    .setRoleArn(ROLE_ARN)
                    .setRoleSessionName("product-upload-" + System.currentTimeMillis())
                    .setPolicy(policy)
                    .setDurationSeconds(DURATION_SECONDS);

            // 4. 发送请求
            RuntimeOptions runtime = new RuntimeOptions();
            AssumeRoleResponse response = stsClient.assumeRoleWithOptions(request, runtime);

            // 5. 返回结果给前端
            Map<String, Object> result = new HashMap<>();
            result.put("accessKeyId", response.getBody().getCredentials().getAccessKeyId());
            result.put("accessKeySecret", response.getBody().getCredentials().getAccessKeySecret());
            result.put("securityToken", response.getBody().getCredentials().getSecurityToken());
            result.put("expiration", response.getBody().getCredentials().getExpiration());
            result.put("bucketName", BUCKET_NAME);
            result.put("endpoint", ENDPOINT);
            result.put("uploadDir", UPLOAD_DIR);

            return result;

        } catch (Exception e) {
            throw new RuntimeException("获取 OSS 上传凭证失败", e);
        }
    }
}