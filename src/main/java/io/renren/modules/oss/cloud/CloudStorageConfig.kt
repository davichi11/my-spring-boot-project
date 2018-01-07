package io.renren.modules.oss.cloud

import io.renren.common.validator.group.AliyunGroup
import io.renren.common.validator.group.QcloudGroup
import io.renren.common.validator.group.QiniuGroup
import lombok.Data
import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.constraints.Range
import org.hibernate.validator.constraints.URL

import javax.validation.constraints.NotNull
import java.io.Serializable

/**
 * 云存储配置信息
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-25 16:12
 */
class CloudStorageConfig : Serializable {

    //类型 1：七牛  2：阿里云  3：腾讯云
    @Range(min = 1, max = 3, message = "类型错误")
    var type: Int? = null
        set(type) {
            field = this.type
        }

    //七牛绑定的域名
    @NotBlank(message = "七牛绑定的域名不能为空", groups = arrayOf(QiniuGroup::class))
    @URL(message = "七牛绑定的域名格式不正确", groups = arrayOf(QiniuGroup::class))
    var qiniuDomain: String? = null
        set(qiniuDomain) {
            field = this.qiniuDomain
        }

    /**
     * 七牛路径前缀
     */
    var qiniuPrefix: String? = null
        set(qiniuPrefix) {
            field = this.qiniuPrefix
        }
    //七牛ACCESS_KEY
    @NotBlank(message = "七牛AccessKey不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuAccessKey: String? = null
        set(qiniuAccessKey) {
            field = this.qiniuAccessKey
        }
    //七牛SECRET_KEY
    @NotBlank(message = "七牛SecretKey不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuSecretKey: String? = null
        set(qiniuSecretKey) {
            field = this.qiniuSecretKey
        }
    //七牛存储空间名
    @NotBlank(message = "七牛空间名不能为空", groups = arrayOf(QiniuGroup::class))
    var qiniuBucketName: String? = null
        set(qiniuBucketName) {
            field = this.qiniuBucketName
        }

    //阿里云绑定的域名
    @NotBlank(message = "阿里云绑定的域名不能为空", groups = arrayOf(AliyunGroup::class))
    @URL(message = "阿里云绑定的域名格式不正确", groups = arrayOf(AliyunGroup::class))
    var aliyunDomain: String? = null
        set(aliyunDomain) {
            field = this.aliyunDomain
        }
    /**
     * 阿里云路径前缀
     */
    var aliyunPrefix: String? = null
        set(aliyunPrefix) {
            field = this.aliyunPrefix
        }
    //阿里云EndPoint
    @NotBlank(message = "阿里云EndPoint不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunEndPoint: String? = null
        set(aliyunEndPoint) {
            field = this.aliyunEndPoint
        }
    //阿里云AccessKeyId
    @NotBlank(message = "阿里云AccessKeyId不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunAccessKeyId: String? = null
        set(aliyunAccessKeyId) {
            field = this.aliyunAccessKeyId
        }
    //阿里云AccessKeySecret
    @NotBlank(message = "阿里云AccessKeySecret不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunAccessKeySecret: String? = null
        set(aliyunAccessKeySecret) {
            field = this.aliyunAccessKeySecret
        }
    //阿里云BucketName
    @NotBlank(message = "阿里云BucketName不能为空", groups = arrayOf(AliyunGroup::class))
    var aliyunBucketName: String? = null
        set(aliyunBucketName) {
            field = this.aliyunBucketName
        }

    //腾讯云绑定的域名
    @NotBlank(message = "腾讯云绑定的域名不能为空", groups = arrayOf(QcloudGroup::class))
    @URL(message = "腾讯云绑定的域名格式不正确", groups = arrayOf(QcloudGroup::class))
    var qcloudDomain: String? = null
        set(qcloudDomain) {
            field = this.qcloudDomain
        }

    /**
     * 腾讯云路径前缀
     */
    var qcloudPrefix: String? = null
        set(qcloudPrefix) {
            field = this.qcloudPrefix
        }
    //腾讯云AppId
    @NotNull(message = "腾讯云AppId不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudAppId: Int? = null
        set(qcloudAppId) {
            field = this.qcloudAppId
        }
    //腾讯云SecretId
    @NotBlank(message = "腾讯云SecretId不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudSecretId: String? = null
        set(qcloudSecretId) {
            field = this.qcloudSecretId
        }
    //腾讯云SecretKey
    @NotBlank(message = "腾讯云SecretKey不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudSecretKey: String? = null
        set(qcloudSecretKey) {
            field = this.qcloudSecretKey
        }
    //腾讯云BucketName
    @NotBlank(message = "腾讯云BucketName不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudBucketName: String? = null
        set(qcloudBucketName) {
            field = this.qcloudBucketName
        }
    //腾讯云COS所属地区
    @NotBlank(message = "所属地区不能为空", groups = arrayOf(QcloudGroup::class))
    var qcloudRegion: String? = null
        set(qcloudRegion) {
            field = this.qcloudRegion
        }

    companion object {
        private const val serialVersionUID = 1L
    }

}
