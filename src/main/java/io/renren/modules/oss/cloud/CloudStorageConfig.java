package io.renren.modules.oss.cloud;

import io.renren.common.validator.group.AliyunGroup;
import io.renren.common.validator.group.QcloudGroup;
import io.renren.common.validator.group.QiniuGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 16:12
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //类型 1：七牛  2：阿里云  3：腾讯云
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    //七牛绑定的域名
    @NotNull(message = "七牛绑定的域名不能为空", groups = QiniuGroup.class)
    @URL(message = "七牛绑定的域名格式不正确", groups = QiniuGroup.class)
    private String qiniuDomain;

    /**
     * 七牛路径前缀
     */
    private String qiniuPrefix;
    //七牛ACCESS_KEY
    @NotNull(message = "七牛AccessKey不能为空", groups = QiniuGroup.class)
    private String qiniuAccessKey;
    //七牛SECRET_KEY
    @NotNull(message = "七牛SecretKey不能为空", groups = QiniuGroup.class)
    private String qiniuSecretKey;
    //七牛存储空间名
    @NotNull(message = "七牛空间名不能为空", groups = QiniuGroup.class)
    private String qiniuBucketName;

    //阿里云绑定的域名
    @NotNull(message = "阿里云绑定的域名不能为空", groups = AliyunGroup.class)
    @URL(message = "阿里云绑定的域名格式不正确", groups = AliyunGroup.class)
    private String aliyunDomain;
    /**
     * 阿里云路径前缀
     */
    private String aliyunPrefix;
    //阿里云EndPoint
    @NotNull(message = "阿里云EndPoint不能为空", groups = AliyunGroup.class)
    private String aliyunEndPoint;
    //阿里云AccessKeyId
    @NotNull(message = "阿里云AccessKeyId不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeyId;
    //阿里云AccessKeySecret
    @NotNull(message = "阿里云AccessKeySecret不能为空", groups = AliyunGroup.class)
    private String aliyunAccessKeySecret;
    //阿里云BucketName
    @NotNull(message = "阿里云BucketName不能为空", groups = AliyunGroup.class)
    private String aliyunBucketName;

    //腾讯云绑定的域名
    @NotNull(message = "腾讯云绑定的域名不能为空", groups = QcloudGroup.class)
    @URL(message = "腾讯云绑定的域名格式不正确", groups = QcloudGroup.class)
    private String qcloudDomain;

    /**
     * 腾讯云路径前缀
     */
    private String qcloudPrefix;
    //腾讯云AppId
    @NotNull(message = "腾讯云AppId不能为空", groups = QcloudGroup.class)
    private Integer qcloudAppId;
    //腾讯云SecretId
    @NotNull(message = "腾讯云SecretId不能为空", groups = QcloudGroup.class)
    private String qcloudSecretId;
    //腾讯云SecretKey
    @NotNull(message = "腾讯云SecretKey不能为空", groups = QcloudGroup.class)
    private String qcloudSecretKey;
    //腾讯云BucketName
    @NotNull(message = "腾讯云BucketName不能为空", groups = QcloudGroup.class)
    private String qcloudBucketName;
    //腾讯云COS所属地区
    @NotNull(message = "所属地区不能为空", groups = QcloudGroup.class)
    private String qcloudRegion;

}
