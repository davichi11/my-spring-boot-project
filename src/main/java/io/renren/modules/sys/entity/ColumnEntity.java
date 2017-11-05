package io.renren.modules.sys.entity;

import lombok.Data;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.entity
 * @Description 列的属性
 * @create 2017/8/13-21:05
 */
@Data
public class ColumnEntity {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 列名类型
     */
    private String dataType;
    /**
     * 列名备注
     */
    private String comments;
    /**
     * 属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String upAttrName;
    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String lowAttrName;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * auto_increment
     */
    private String extra;
}
