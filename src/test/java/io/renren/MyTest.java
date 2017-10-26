package io.renren;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import io.renren.common.utils.GenUtils;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ChunLiang Hu
 * @Company
 * @Project renren-fast
 * @Package io.renren
 * @Description TODO(描述)
 * @create 2017/7/8-14:56
 */
public class MyTest {

    public static void main(String[] args) {
//        List<String> stringList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "7", "8", "9");
//        int sum = stringList.stream().filter(Objects::nonNull).distinct().mapToInt(NumberUtils::toInt).filter(i -> i % 2 == 0).sum();
//        System.out.println(sum);

        for (long i = 0; i < 50; i++) {
            System.out.println(fib(i));
        }
    }

    private boolean testString(int i) {
        return i > 0;
    }

    private static Long fib2(Long i) {
        if (i < 2) {
            return i;
        } else {
            return fib2(i - 1) + fib2(i - 2);
        }
    }

    @Test
    public void test1() {
        Integer x = new Integer(1);
        Integer y = x;
        System.out.println(Objects.equals(x, y));
        x += 0;
        System.out.println(x == y);
    }

    @Test
    public void testPath() {
        System.out.println(GenUtils.getClasspath());
    }

    @Test
    public void testStream() {
        List<Integer> ints = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(ints.stream().reduce((integer, integer2) -> integer + integer2).orElse(0));

    }

    private static Map<Long, Long> cache = new ConcurrentHashMap<>();

    private static Long fib(Long n) {
        if (n < 2) return n;
        return cache.computeIfAbsent(n, (key) ->
                fib(n - 1) + fib(n - 2)
        );
    }

    @Test
    public void testF() {
        System.out.println("3*0.1=" + 3 * 0.1);
        System.out.println("4*0.1=" + 4 * 0.1);
        System.out.println("0.1+0.2=" + (0.1 + 0.2));

        System.out.println(.1F * 3);
    }

    @Test
    public void testMultiset() {
        String strWorld = "a,b,c,d,b,b,c";
        String[] words = strWorld.split(",");

        List<String> wordList = new ArrayList<>();
        wordList.addAll(Arrays.asList(words));

        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);
        wordsMultiset.forEachEntry((s, value) -> System.out.println(MessageFormat.format("string={0},value={1}",s,value)));

//        wordsMultiset.forEach(s -> System.out.println("key=" + s + "||count=" + wordsMultiset.count(s)));

    }

}
