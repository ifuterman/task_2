package com.yourcodereview.iosif.task_2;

import com.yourcodereview.iosif.task_2.protocol.ShortLinkRequest;
import com.yourcodereview.iosif.task_2.repository.ResourceRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
//@WebAppConfiguration
public class RestControllerTest{
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ResourceRepository repository;

    @Test
    public void generateShortLinkTest() throws Exception{
        ShortLinkRequest request = new ShortLinkRequest("www.google.com");
        ResultActions actions = mockMvc.perform(post("/generate").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(request))).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.link", Matchers.containsString("/l/")));
    }

    @Test
    public void redirectFromShortLinkTest() throws Exception{
        ResultActions actions = mockMvc.perform(get("/l/1")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void statsForId1Test() throws Exception{
        ResultActions actions = mockMvc.perform(get("/stats/l/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
    @Test
    public void statsForAllTest() throws Exception{
        ResultActions actions = mockMvc.perform(get("/stats").param("page", "0")
                        .param("count", "5")).andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }
}
