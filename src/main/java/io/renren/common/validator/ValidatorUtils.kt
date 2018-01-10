package io.renren.common.validator

import io.renren.common.exception.RRException
import javax.validation.Validation
import javax.validation.Validator

/**
 * hibernate-validator校验工具类
 *
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2017-03-15 10:50
 */
object ValidatorUtils {
    private var validator: Validator? = null

    init {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    /**
     * 校验对象
     *
     * @param `object` 待校验对象
     * @param groups 待校验的组
     * @throws RRException 校验不通过，则报RRException异常
     */
    @Throws(RRException::class)
    fun validateEntity(any: Any, vararg groups: Class<*>) {
        val constraintViolations = validator!!.validate(any, *groups)
        if (!constraintViolations.isEmpty()) {
            val constraint = constraintViolations.iterator().next()
            throw RRException(constraint.message)
        }
    }
}
