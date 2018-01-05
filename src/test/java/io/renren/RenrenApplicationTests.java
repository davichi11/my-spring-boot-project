package io.renren;

import com.google.common.collect.Maps;
import io.renren.modules.sys.service.SysUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RenrenApplicationTests {
    @Autowired
    SysUserService userService;
    @Autowired
    WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private MockHttpSession mockHttpSession;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext(), UUID.randomUUID().toString());
    }

    @Test
    public void contextLoads() {
        userService.queryList(Maps.newHashMap()).forEach(System.out::println);

    }

    @Test
    public void test() throws Exception {
        mockMvc.perform(get("/api/test").session(mockHttpSession)).andExpect(status().isOk()).andDo(print());
    }



}
