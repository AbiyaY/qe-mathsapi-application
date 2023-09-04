package com.qeproject.mathsapi.models;

public class OperatorObject {

    // Variables
    private String operator;

    // Empty Constructor
    public OperatorObject(){

    }

    //Constructor
    public OperatorObject(String operator) {
        this.operator = operator;
    }

    // Getters and Setters
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
