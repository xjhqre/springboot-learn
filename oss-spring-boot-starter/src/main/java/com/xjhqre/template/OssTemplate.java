package com.xjhqre.template;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * <p>
 * OssTemplate
 * <p>
 *
 * @author xjhqre
 * @since 9月 11, 2023
 */
public interface OssTemplate {

    /**
     * 创建bucket
     * 
     * @param bucketName
     *            bucket名称
     */
    void createBucket(String bucketName);

    /**
     * 获取所有的bucket
     * 
     */
    List<Bucket> getAllBuckets();

    /**
     * 通过bucket名称删除bucket
     * 
     * @param bucketName
     *            桶名称
     */
    void removeBucket(String bucketName);

    /**
     * 上传文件
     * 
     * @param bucketName
     *            bucket名称
     * @param objectName
     *            文件名称
     * @param stream
     *            文件流
     * @param contextType
     *            文件类型
     */
    void putObject(String bucketName, String objectName, InputStream stream, String contextType) throws Exception;

    /**
     * 上传文件
     * 
     * @param bucketName
     *            bucket名称
     * @param objectName
     *            文件名称
     * @param stream
     *            文件流
     */
    void putObject(String bucketName, String objectName, InputStream stream) throws Exception;

    /**
     * 获取文件
     * 
     * @param bucketName
     *            bucket名称
     * @param objectName
     *            文件名称
     * @return S3Object
     */
    S3Object getObject(String bucketName, String objectName);

    /**
     * 获取对象的url
     * 
     * @param bucketName
     *            bucket名称
     * @param objectName
     *            文件名称
     * @param expires
     *            过期时间
     * @return 无
     */
    String getObjectURL(String bucketName, String objectName, Integer expires);

    /**
     * 通过bucketName和objectName删除对象
     * 
     * @param bucketName
     *            bucket名称
     * @param objectName
     *            文件名称
     */
    void removeObject(String bucketName, String objectName) throws Exception;

    /**
     * 根据文件前置查询文件
     * 
     * @param bucketName
     *            bucket名称
     * @param prefix
     *            前缀
     * @param recursive
     *            是否递归查询
     * @return S3ObjectSummary 列表
     */
    List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive);
}
