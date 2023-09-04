package com.qeproject.mathsapi.controllers;

import com.qeproject.mathsapi.models.Integers;
import com.qeproject.mathsapi.models.Operator;
import com.qeproject.mathsapi.services.MathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mathservice")
public class MathsController {

    @Autowired
    private final MathsService mathsService;

    public MathsController(MathsService mathsService) {
        this.mathsService = mathsService;
    }

    @PostMapping("/integers")
    public void storeIntegers(@RequestBody Integers integers){
        mathsService.storeIntegers(integers);
    }

    @PostMapping("/operator")
    public Double storeOperatorAndCalculate(@RequestBody Operator operator){
        return mathsService.storeOperator(operator);
    }

}
