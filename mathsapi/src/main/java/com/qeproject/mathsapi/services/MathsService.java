package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MathsService {

    private List<NumbersObject> storedIntegers = new ArrayList<>();

    private List<OperatorEnum> storedOperator = new ArrayList<>();
    public synchronized void storeIntegers(NumbersObject numbersObject) {
        storedIntegers.clear();
        storedIntegers.add(numbersObject);
        notifyAll();
    }

    public synchronized Double storeOperator(OperatorEnum operatorEnum) {
        storedOperator.clear();
        storedOperator.add(operatorEnum);

        while (storedIntegers.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) { /* NCR */}
        }
        return calculate();
    }

    public Double calculate(){
        NumbersObject numbers = storedIntegers.get(0);
        OperatorEnum operatorEnum = storedOperator.get(0);

        //String transformedOperator = operatorEnum.getOperator().toLowerCase();
        
        Double result = null;

        switch (operatorEnum) {
            case ADD:
                result = numbers.getNumber1().doubleValue() + numbers.getNumber2().doubleValue();
                break;

            case SUBTRACT:
                result = numbers.getNumber1().doubleValue() - numbers.getNumber2().doubleValue();
                break;

            case MULTIPLY:
                result = numbers.getNumber1().doubleValue() * numbers.getNumber2().doubleValue();
                break;

            case DIVIDE:
                result = numbers.getNumber1().doubleValue() / numbers.getNumber2().doubleValue();
                break;
//            default:
//                add exception here
        }

        return result;

    }
}
