package io.renren.common.exception

/**
 * 自定义异常
 *
 * @author ChunLiang Hu
 * @email davichi2009@gmail.com
 * @date 2016年10月27日 下午10:11:27
 */
class RRException : RuntimeException {

    var msg: String? = null
    var code = 500

    constructor(msg: String) : super(msg) {
        this.msg = msg
    }

    constructor(msg: String, e: Throwable) : super(msg, e) {
        this.msg = msg
    }

    constructor(msg: String, code: Int) : super(msg) {
        this.msg = msg
        this.code = code
    }

    constructor(msg: String, code: Int, e: Throwable) : super(msg, e) {
        this.msg = msg
        this.code = code
    }

    companion object {
        private val serialVersionUID = 1L
    }


}
