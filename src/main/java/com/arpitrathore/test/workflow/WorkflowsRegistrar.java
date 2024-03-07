package com.arpitrathore.test.workflow;

import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import com.netflix.conductor.sdk.workflow.def.ConductorWorkflow;
import com.netflix.conductor.sdk.workflow.def.tasks.SimpleTask;
import com.netflix.conductor.sdk.workflow.executor.WorkflowExecutor;

import io.orkes.conductor.client.WorkflowClient;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowsRegistrar {

  private final WorkflowExecutor workflowExecutor;

  private final WorkflowClient workflowClient;

  public WorkflowsRegistrar(WorkflowExecutor workflowExecutor, WorkflowClient workflowClient) {
    this.workflowExecutor = workflowExecutor;
    this.workflowClient = workflowClient;
  }

  @PostConstruct
  void register() {
    registerWorkflow();
  }

  void registerWorkflow() {
    ConductorWorkflow<Input> workflow = new ConductorWorkflow<>(workflowExecutor);
    workflow.setDescription("Test workflow");
    workflow.setOwnerEmail("arpitrathore@pm.me");
    workflow.setName("test_workflow");
    workflow.setVersion(1);

    SimpleTask task1 = new SimpleTask("task1", "task1");
    task1.input("someId", "${workflow.input.someId}");

    SimpleTask task2 = new SimpleTask("task2", "task2");
    task2.input("someId", "${workflow.input.someId}");

    workflow.add(task1);
    workflow.add(task2);

    workflow.registerWorkflow(true, true);
  }

  public Map<String, Object> submitWorkflow(Input input) {

    StartWorkflowRequest request = new StartWorkflowRequest();
    request.setName("test_workflow");
    Map<String, Object> inputData = new HashMap<>();
    inputData.put("someId", input.someId());
    request.setInput(inputData);

    String workflowId = workflowClient.startWorkflow(request);
    System.out.println("Workflow id: " + workflowId);

    return Map.of("workflowId", workflowId);
  }

}
