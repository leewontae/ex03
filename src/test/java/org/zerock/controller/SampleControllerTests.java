package org.zerock.controller;

import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.zerock.domain.Ticket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:webapp/WEB-INF/applicationContext.xml","file:webapp/WEB-INF/dispatcher-servlet.xml"})
@Log4j
public class SampleControllerTests {

    @Setter(onMethod_={@Autowired})
    private WebApplicationContext ctx;

    private MockMvc mockMvc;
    @Before
    public void Setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testConvert() throws  Exception{

        Ticket ticket = new Ticket();
        ticket.setTno(123);
        ticket.setOwner("Admin");
        ticket.setGrade("AAA");

        String jsonSTr = new Gson().toJson(ticket);

        log.info(jsonSTr);

        mockMvc.perform(post("/sample/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonSTr))
                .andExpect(status().is(200));

    }

}
