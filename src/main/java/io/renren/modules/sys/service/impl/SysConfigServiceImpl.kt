package io.renren.modules.sys.service.impl

import com.google.gson.Gson
import io.renren.common.exception.RRException
import io.renren.modules.sys.dao.SysConfigDao
import io.renren.modules.sys.entity.SysConfigEntity
import io.renren.modules.sys.service.SysConfigService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("sysConfigService")
class SysConfigServiceImpl @Autowired constructor(private val sysConfigDao: SysConfigDao): SysConfigService {
    /**
     * 根据key，获取配置的value值
     *
     * @param key          key
     * @param defaultValue 缺省值
     * @return
     */
    override fun getValue(key: String, defaultValue: String): String {
        val value = sysConfigDao.queryByKey(key)
        return if (value.isBlank()) {
            defaultValue
        } else value
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun save(config: SysConfigEntity) {
        sysConfigDao.save(config)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun update(config: SysConfigEntity) {
        sysConfigDao.update(config)
    }

    override fun updateValueByKey(key: String, value: String) {
        sysConfigDao.updateValueByKey(key, value)
    }

    @Transactional(rollbackFor = [(Exception::class)])
    @Throws(Exception::class)
    override fun deleteBatch(ids: Array<Long>) {
        sysConfigDao.deleteBatch(ids)
    }

    override fun queryList(map: Map<String, Any>): List<SysConfigEntity> {
        return sysConfigDao.queryList(map)
    }

    override fun queryTotal(map: Map<String, Any>): Int {
        return sysConfigDao.queryTotal(map)
    }

    override fun queryObject(id: Long?): SysConfigEntity {
        return sysConfigDao.queryObject(id!!)
    }


    override fun <T> getConfigObject(key: String, clazz: Class<T>): T {
        val value = getValue(key,"")
        if (value.isNotBlank()) {
            return Gson().fromJson(value, clazz)
        }

        try {
            return clazz.newInstance()
        } catch (e: Exception) {
            throw RRException("获取参数失败")
        }

    }
}
