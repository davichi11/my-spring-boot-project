package io.renren;

import com.google.common.collect.Maps;
import io.renren.modules.sys.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenrenApplicationTests {
    @Autowired
    SysUserService userService;

    @Test
    public void contextLoads() {
        userService.queryList(Maps.newHashMap()).forEach(System.out::println);

    }

}
