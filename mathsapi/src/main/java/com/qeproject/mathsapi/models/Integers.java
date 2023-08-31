package com.qeproject.mathsapi.models;

public class Integers {

    // Variables
    private Integer number1;
    private Integer number2;

    // Empty Constructor
    public Integers(){

    }

    // Constructor
    public Integers(Integer number1, Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    //Getters and Setters
    public Integer getNumber1() {
        return number1;
    }

    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    public void setNumber2(Integer number2) {
        this.number2 = number2;
    }
}
