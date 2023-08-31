package com.qeproject.mathsapi.models;

public class Operator {

    // Variables
    private String operator;

    // Empty Constructor
    public Operator(){

    }

    //Constructor
    public Operator(String operator) {
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
