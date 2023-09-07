package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MathsService {

    private List<NumbersObject> storedIntegers = new ArrayList<>();

    private List<OperatorObject> storedOperator = new ArrayList<>();
    public synchronized void storeIntegers(NumbersObject numbersObject) {
        storedIntegers.clear();
        storedIntegers.add(numbersObject);
        notifyAll();
    }

    public synchronized Double storeOperator(OperatorObject operatorObject) {
        storedOperator.clear();
        storedOperator.add(operatorObject);

        while (storedIntegers.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) { /* NCR */}
        }
        return calculate();
    }

    public Double calculate(){
        NumbersObject numbers = storedIntegers.get(0);
        OperatorObject operatorObject = storedOperator.get(0);

        String transformedOperator = operatorObject.getOperator().toLowerCase();
        
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
