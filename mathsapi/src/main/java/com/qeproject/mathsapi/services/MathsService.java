package com.qeproject.mathsapi.services;

import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import org.springframework.stereotype.Service;

@Service
public class MathsService {

    private NumbersObject numberRepo;
    private OperatorEnum operator;
  
    public synchronized void storeIntegers(NumbersObject numbersObject) {
        this.numberRepo = numbersObject;
        notifyAll();
    }

    public synchronized Double storeOperator(OperatorEnum operatorEnum) {
        this.operator = operatorEnum;

        while (numberRepo == null) {
            try {
                wait();
            } catch (InterruptedException e) { /* NCR */}
        }
        return calculate();
    }

    public Double calculate(){

        Double result = null;

        switch (this.operator) {
            case ADD:
                result = numberRepo.getNumber1().doubleValue() + numberRepo.getNumber2().doubleValue();
                break;

            case SUBTRACT:
                result = numberRepo.getNumber1().doubleValue() - numberRepo.getNumber2().doubleValue();
                break;

            case MULTIPLY:
                result = numberRepo.getNumber1().doubleValue() * numberRepo.getNumber2().doubleValue();
                break;

            case DIVIDE:
                result = numberRepo.getNumber1().doubleValue() / numberRepo.getNumber2().doubleValue();
                break;

//            default:
//                throw new IllegalStateException("Operator '" + this.operator + "' is not supported");
        }

        return result;

    }
}
