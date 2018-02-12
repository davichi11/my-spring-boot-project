package io.renren

import io.renren.common.utils.GenUtils
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2018/2/12-10:46
 */
fun main(args: Array<String>) {
    val path = GenUtils.classpath
    val p: Path = Paths.get(path)
    print(p.parent.parent.toUri().path)
}
