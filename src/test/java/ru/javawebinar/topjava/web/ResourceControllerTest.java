package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Алексей on 29.01.2017.
 */
public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testCSS() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(header().string("Content-Type", "text/css;charset=UTF-8")
                );
    }
}
