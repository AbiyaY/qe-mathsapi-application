package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.Integers;
import com.qeproject.mathsapi.models.Operator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MathsService {

    private List<Integers> storedIntegers = new ArrayList<>();

    private List<Operator> storedOperator = new ArrayList<>();
    public synchronized void storeIntegers(Integers integers) {
        storedIntegers.clear();
        storedIntegers.add(integers);
        notifyAll();
    }

    public synchronized Double storeOperator(Operator operator) {
        storedOperator.clear();
        storedOperator.add(operator);

        while (storedIntegers.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) { /* NCR */}
        }
        return calculate();
    }

    public Double calculate(){
        Integers numbers = storedIntegers.get(0);
        Operator operator = storedOperator.get(0);

        String transformedOperator = operator.getOperator().toLowerCase();
        
        Double result = null;

        switch(transformedOperator) {
            case "add":
                result = numbers.getNumber1().doubleValue() + numbers.getNumber2().doubleValue();
                break;

            case "subtract":
                result = numbers.getNumber1().doubleValue() - numbers.getNumber2().doubleValue();
                break;

            case "multiply":
                result = numbers.getNumber1().doubleValue() * numbers.getNumber2().doubleValue();
                break;

            case "divide":
                result = numbers.getNumber1().doubleValue() / numbers.getNumber2().doubleValue();
                break;
//            default:
//                add exception here
        }

        return result;

    }
}
