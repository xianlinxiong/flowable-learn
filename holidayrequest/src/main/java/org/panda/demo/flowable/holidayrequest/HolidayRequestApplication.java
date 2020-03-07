package org.panda.demo.flowable.holidayrequest;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HolidayRequestApplication {

    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration()
//        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("panda")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday-request.bpmn20.xml")
                .deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
        System.out.println("Found processes definition: "+ processDefinition.getName());

        Scanner scanner = new Scanner(System.in);

        Map<String, Object> virables = generateParam(scanner);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", virables);

        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList =taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("U have "+taskList.size() + " tasks to complete");
        for(int i =0; i < taskList.size(); i++){
            System.out.println(i+") "+ taskList.get(i).getName());
        }
        System.out.println("Which task would U like to complete");
        Integer taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = taskList.get(taskIndex);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());

        System.out.println(processVariables.get("employee")+ " wants "+processVariables.get("numOfHolidays") + "holidays for "
                +processVariables.get("description") + ". Do U approve this?");

        boolean approved = scanner.nextLine().equalsIgnoreCase("y");
        Map<String, Object> variables = new HashMap<>();
        variables.put("approved",  approved);
        taskService.complete(task.getId(), variables);


        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for(HistoricActivityInstance history : historicActivityInstances){
            System.out.println(history.getActivityId() + " took " + history.getDurationInMillis() + " millSeconds.");
        }

        System.exit(0);
    }

    private static Map<String, Object> generateParam(Scanner scanner){
        Map<String, Object> params = new HashMap<>();
        System.out.println("Who R U ?");
        params.put("employee", scanner.nextLine());
        System.out.println("How many holidays do you want?");
        params.put("numOfHolidays", Integer.parseInt(scanner.nextLine()));
        System.out.println("Why do U need them?");
        params.put("description", scanner.nextLine());
        return params;
    }
}
