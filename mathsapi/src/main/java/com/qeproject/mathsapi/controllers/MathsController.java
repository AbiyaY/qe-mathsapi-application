package com.qeproject.mathsapi.controllers;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import com.qeproject.mathsapi.services.MathsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mathservice")
public class MathsController {

    @Autowired
    private final MathsService mathsService;

    public MathsController(MathsService mathsService)
    {
        this.mathsService = mathsService;
    }

    @PostMapping("/integers")
    public ResponseEntity<Void> storeIntegers(@RequestBody NumbersObject numbersObject)
    {
        mathsService.storeIntegers(numbersObject);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/operator")
    public ResponseEntity<Double> storeOperatorAndCalculate(@RequestBody OperatorEnum operatorEnum){

        Double result = mathsService.storeOperator(operatorEnum);

        return new ResponseEntity<Double>(result, HttpStatus.CREATED);
    }

}
