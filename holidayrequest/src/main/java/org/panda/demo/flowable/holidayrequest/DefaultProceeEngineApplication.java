package org.panda.demo.flowable.holidayrequest;


import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;

public class DefaultProceeEngineApplication {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine.getName());
    }
}
