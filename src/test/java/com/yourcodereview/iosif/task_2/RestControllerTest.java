package com.yourcodereview.iosif.task_2;

import com.yourcodereview.iosif.task_2.protocol.ShortLinkRequest;
import com.yourcodereview.iosif.task_2.repository.ResourceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .content(TestUtil.convertObjectToJsonBytes(request))).andExpect(status().isOk());
    }
}
