package com.qeproject.mathsapi.controllers;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
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
    public void storeIntegers(@RequestBody NumbersObject numbersObject){
        mathsService.storeIntegers(numbersObject);
    }

    @PostMapping("/operator")
    public Double storeOperatorAndCalculate(@RequestBody OperatorEnum operatorEnum){
        System.out.println(operatorEnum);
        return mathsService.storeOperator(operatorEnum);
    }

}
