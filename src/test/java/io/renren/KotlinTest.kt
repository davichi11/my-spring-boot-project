package io.renren

/**
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @Description TODO(描述)
 * @author ChunLiang Hu
 * @create 2017/12/19-13:53
 */
fun main(args: Array<String>) {
//    print(test(b = 10, a = "20"))
//    val a = 127
//    val b = 127
//    print(a == b)
//
//    print(test2())
    test3()
}

fun test(a: String, b: Int = 10): Int {
    return a.toInt() + b
}

fun test2(x: Int = 10, y: Int = 10): Int {
    return x + y
}

fun test3() {
    (0..10).forEach { println(it*2) }
}
