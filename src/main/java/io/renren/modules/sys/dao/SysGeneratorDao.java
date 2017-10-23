package io.renren.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午3:32:04
 */
@Mapper
public interface SysGeneratorDao {
    /**
     * 查询所有表的结构
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> queryList(Map<String, Object> map);

    /**
     * 查询总记录数
     *
     * @param map
     * @return
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 查询单个表的结构
     *
     * @param tableName
     * @return
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 查询单个表的列结构
     *
     * @param tableName
     * @return
     */
    List<Map<String, String>> queryColumns(String tableName);
}
