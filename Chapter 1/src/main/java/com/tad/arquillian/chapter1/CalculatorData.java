package com.tad.arquillian.chapter1;

/**
 * @author JohnAment
 * 
 */
public class CalculatorData {

    private int[] data;
    private int   result;

    public CalculatorData(int... data) {
        this.data = data;
    }

    public int[] getData() {
        return data;
    }

    public int getCalculatedResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
