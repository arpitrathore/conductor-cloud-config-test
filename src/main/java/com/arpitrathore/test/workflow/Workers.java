package com.arpitrathore.test.workflow;

import com.netflix.conductor.sdk.workflow.task.WorkerTask;
import org.springframework.stereotype.Component;

/**
 * @author arathore
 */
@Component
public class Workers {

  @WorkerTask(value = "task1", threadCount = 5, pollingInterval = 200)
  public void task1(Input input) {
    System.out.println("Begin Task 1");
    System.out.println("Received id in task1. Id: " + input.someId());
    System.out.println("END Task 1");
  }

  @WorkerTask(value = "task2", threadCount = 5, pollingInterval = 200)
  public void task2(Input input) {
    System.out.println("Begin Task 2");
    System.out.println("Received id in task2. Id: " + input.someId());
    System.out.println("END Task 2");
  }
}
