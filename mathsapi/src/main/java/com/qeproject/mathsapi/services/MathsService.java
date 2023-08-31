package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.Integers;
import com.qeproject.mathsapi.models.Operator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MathsService {

    private ArrayList<Integers> storedIntegers = new ArrayList<>();

    private ArrayList<Operator> storedOperator = new ArrayList<>();
    public synchronized void storeIntegers(Integers integers) {
        storedIntegers.clear();
        storedIntegers.add(integers);
    }

    public synchronized Integer storeOperator(Operator operator) {
        storedOperator.clear();
        storedOperator.add(operator);

        while (storedIntegers.get(0) == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread Interrupted");
            }
        }
        return calculate();
    }

    public Integer calculate(){
        Integers numbers = storedIntegers.get(0);
        Operator operator = storedOperator.get(0);

        String transformedOperator = operator.getOperator().toLowerCase();
        
        Integer result = null;
        
        switch(transformedOperator) {
            case "add":
                result = numbers.getNumber1() + numbers.getNumber2();
                break;

            case "subtract":
                result = numbers.getNumber1() - numbers.getNumber2();
                break;

            case "multiply":
                result = numbers.getNumber1() * numbers.getNumber2();
                break;

            case "divide":
                result = numbers.getNumber1() / numbers.getNumber2();
                break;
//            default:
//                add exception here
        }
        
        return result;

    }
}
