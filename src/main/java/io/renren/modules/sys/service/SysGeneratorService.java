package io.renren.modules.sys.service;

import java.util.List;
import java.util.Map;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren.modules.sys.service
 * @Description TODO(描述)
 * @create 2017/8/13-21:13
 */
public interface SysGeneratorService {
    /**
     * 查询列表
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
     * 查询表名
     *
     * @param tableName
     * @return
     */
    Map<String, String> queryTable(String tableName);

    /**
     * 查询列名
     *
     * @param tableName
     * @return
     */
    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 生成代码
     *
     * @param tableNames
     * @return
     */
    byte[] generatorCode(String[] tableNames);
}
