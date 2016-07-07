package com.alexrios;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpMethod.POST;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(FianceeApplication.class)
@WebIntegrationTest
public class TestTest {

    @Autowired
    private Fiancee fiancee;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    DummyController controller;

    @Test(expected = TimeoutException.class)
    public void deve_integrador_responder_callback() throws Exception {
        restTemplate.getForObject("http://localhost:9090/greeting", Greeting.class);
        fiancee.withURL("/callback")
               .withMethod(POST)
               .waitFor(1, SECONDS);
    }

}
