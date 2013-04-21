package com.tad.arquillian.chapter1;

/**
 * @author JohnAment
 * 
 */
public class CalculatorServiceImpl implements CalculatorService {

    public void calculateSum(CalculatorData cd) {
        int[] payload = cd.getData();
        int i = 0;
        for (int p : payload) {
            i += p;
        }
        cd.setResult(i);
    }

}
