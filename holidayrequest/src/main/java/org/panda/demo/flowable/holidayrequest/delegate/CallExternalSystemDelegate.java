package org.panda.demo.flowable.holidayrequest.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class CallExternalSystemDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Process in callExternalSystem for"+ execution.getVariable("employee"));
    }
}
