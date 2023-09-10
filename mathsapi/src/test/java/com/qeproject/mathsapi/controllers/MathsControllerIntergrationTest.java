package com.qeproject.mathsapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MathsControllerIntergrationTest
{
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void testCompleteFeature()
    {
        ResponseEntity<Void> response = restTemplate.postForEntity("/mathservice/integers",
                                                                   new NumbersObject(2, 3),
                                                                   Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<Double> resultResponse = restTemplate.postForEntity("/mathservice/operator",
                                                                           OperatorEnum.ADD,
                                                                           Double.class);
        assertThat(resultResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resultResponse.getBody()).isEqualTo(5.0);
    }
}
