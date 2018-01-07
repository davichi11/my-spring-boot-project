package io.renren.common.aspect

import com.google.gson.Gson
import io.renren.common.annotation.SysLog
import io.renren.common.utils.HttpContextUtils
import io.renren.common.utils.IPUtils
import io.renren.modules.sys.entity.SysLogEntity
import io.renren.modules.sys.entity.SysUserEntity
import io.renren.modules.sys.service.SysLogService
import org.apache.shiro.SecurityUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime


/**
 * 系统日志，切面处理类
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Aspect
@Component
class SysLogAspect {
    private val log = LoggerFactory.getLogger(SysLogAspect::class.java)
    @Autowired
    private val sysLogService: SysLogService? = null

    @Pointcut("@annotation(io.renren.common.annotation.SysLog)")
    fun logPointCut() {

    }

    @Before("logPointCut()")
    fun saveSysLog(joinPoint: JoinPoint) {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method

        val sysLog = SysLogEntity()
        val syslog = method.getAnnotation(SysLog::class.java)
        if (syslog != null) {
            //注解上的描述
            sysLog.operation = syslog.value
        }

        //请求的方法名
        val className = joinPoint.target.javaClass.name
        val methodName = signature.name
        sysLog.method = "$className.$methodName()"

        //请求的参数
        val args = joinPoint.args
        val params = Gson().toJson(args[0])
        sysLog.params = params

        //获取request
        val request = HttpContextUtils.httpServletRequest
        //设置IP地址
        sysLog.ip = IPUtils.getIpAddr(request)

        //用户名
        val username = (SecurityUtils.getSubject().principal as SysUserEntity).username
        sysLog.username = username

        sysLog.createDate = LocalDateTime.now()
        //保存系统日志
        try {
            sysLogService!!.save(sysLog)
        } catch (e: Exception) {
            log.error("保存日志异常", e)
        }

    }

}
