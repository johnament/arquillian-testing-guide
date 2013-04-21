package com.tad.arquillian.chapter1;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

@Model
public class CalculatorController {

    @Inject
    private CalculatorService service;
    @Inject
    private CalculatorForm    form;

    /**
     * For the injected form, calculates the total of the input
     **/
    public void sum() {
        CalculatorData data = new CalculatorData(form.getX(), form.getY(),
                form.getZ());
        service.calculateSum(data);
        form.setSum(data.getCalculatedResult());
    }
}
