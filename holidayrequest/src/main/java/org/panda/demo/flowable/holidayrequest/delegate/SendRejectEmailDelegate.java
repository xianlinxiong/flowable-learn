package org.panda.demo.flowable.holidayrequest.delegate;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class SendRejectEmailDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Process send reject email in service task for "+ execution.getVariable("employee"));
    }
}
