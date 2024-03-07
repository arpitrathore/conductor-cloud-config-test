package com.arpitrathore.test;

import com.arpitrathore.test.workflow.Input;
import com.arpitrathore.test.workflow.WorkflowsRegistrar;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = {"io.orkes", "com.arpitrathore.test"})
public class Application {

  @Value("${foo}")
  private String foo;

  @Autowired
  private WorkflowsRegistrar workflowsRegistrar;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/foo")
  public ResponseEntity<Map<String, Object>> getFoo() {
    return ResponseEntity.ok(Map.of("valueFromConfigServer", foo));
  }

  @PostMapping(value = "/submit", produces = "application/json")
  public ResponseEntity<Map<String, Object>> submitWorkflow(@RequestBody Input input) {
    final Map<String, Object> response = workflowsRegistrar.submitWorkflow(input);
    return ResponseEntity.ok(response);
  }

}
