package com.guestu.jbpm.api;

import com.guestu.jbpm.dto.CompleteTaskDTO;
import com.guestu.jbpm.util.KieUtil;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jbpm")
public class JbpmController {
    private KieServicesClient kieServicesClient;

public JbpmController(){
    KieUtil kieUtil =new KieUtil();
    this.kieServicesClient= kieUtil.getKieServicesClient();
}
    @GetMapping("/taskList")
    public List<TaskSummary> getTaskList(@RequestParam String user,@RequestParam Integer page){

    UserTaskServicesClient userTaskServicesClient=kieServicesClient.getServicesClient(UserTaskServicesClient.class);
        List<String> status= new ArrayList<String>();
  /*      status.add("Completed");
        status.add("Error");
        status.add("Exited");
        status.add("Failed");
        status.add("Obsolete");
        status.add("Suspended");*/
        status.add("Created");
        status.add("InProgress");
        status.add("Ready");
        status.add("Reserved");
        List<TaskSummary> taskSummaryList= userTaskServicesClient.findTasksOwned("userid",status,0,10);

      //  List<TaskSummary> taskSummaryList=userTaskServicesClient.findTasks(user,0,10);

      return taskSummaryList;
    }


    @GetMapping("/taskForm")
    public String getForms(@RequestParam String containerId,@RequestParam String processId){
        UIServicesClient uiServicesClient=this.kieServicesClient.getServicesClient(org.kie.server.client.UIServicesClient.class);
        String processform=uiServicesClient.getProcessForm(containerId,processId);
        return processform;
    }

    @GetMapping("/imageInstance")
    public String getImageInstance(@RequestParam String containerId,@RequestParam Long processInstanceId){
        UIServicesClient uiServicesClient= this.kieServicesClient.getServicesClient(org.kie.server.client.UIServicesClient.class);
        String process_image=  uiServicesClient.getProcessInstanceImage(containerId,processInstanceId);

        return process_image;
    }

    @GetMapping("/imageProcess")
    public String getImageProcess(@RequestParam String containerId,@RequestParam String processId){
        UIServicesClient uiServicesClient= this.kieServicesClient.getServicesClient(org.kie.server.client.UIServicesClient.class);
        String process_image=  uiServicesClient.getProcessImage(containerId,processId);
        return process_image;
    }

    @GetMapping("/processList")
    public List<ProcessDefinition>  getProcessList(@RequestParam Long page){
        CaseServicesClient caseServicesClient=this.kieServicesClient.getServicesClient(CaseServicesClient.class);
        List<ProcessDefinition> processes = caseServicesClient.findProcesses(0, 10);
      return processes;
    }

    @GetMapping("/taskDetails")
    public Map<String,Object>  getTaskDetails(@RequestParam String containerId,@RequestParam Long processInstanceId){
        UserTaskServicesClient userTaskServicesClient=kieServicesClient.getServicesClient(UserTaskServicesClient.class);
        //TaskInstance taskById = userTaskServicesClient.findTaskById();
        Map<String,Object> params= userTaskServicesClient.getTaskInputContentByTaskId(containerId,processInstanceId);

        return params;
    }

    @GetMapping("/startProcess")
    public Long startProcess(@RequestParam String containerId,@RequestParam String processId){
        ProcessServicesClient processServicesClient=kieServicesClient.getServicesClient(ProcessServicesClient.class);
        Map<String,Object> variables=new HashMap<>();
        Long result=processServicesClient.startProcess(containerId,processId,variables);
        return result;
    }


    @PostMapping("/completeTask")
    public void completeTask(@RequestBody CompleteTaskDTO completeTaskDTO){
        UserTaskServicesClient userTaskServicesClient=kieServicesClient.getServicesClient(org.kie.server.client.UserTaskServicesClient.class);
        this.startTask(completeTaskDTO);
        userTaskServicesClient.completeTask(
                                            completeTaskDTO.getContainerId(),completeTaskDTO.getTaskid(),
                                            completeTaskDTO.getUserId(),completeTaskDTO.getVariables()
                                            );

        System.out.println(completeTaskDTO);
    }

    @PostMapping("/startTask")
    public void startTask(@RequestBody CompleteTaskDTO completeTaskDTO){
        UserTaskServicesClient userTaskServicesClient=kieServicesClient.getServicesClient(org.kie.server.client.UserTaskServicesClient.class);
        userTaskServicesClient.startTask(completeTaskDTO.getContainerId(), completeTaskDTO.getTaskid(), completeTaskDTO.getUserId());

    }

    /* Todo
    * ** Add complete
    *  ** Add delete
    *  ** Comments features
    *  ** Files features
    * */

}
