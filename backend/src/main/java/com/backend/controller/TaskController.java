package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.TaskModel;
import com.backend.request.TaskRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dev/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    // List all Tasks
    @GetMapping("/listall")
    public ResponseEntity<List<TaskModel>> listAll() {
        return ResponseEntity.ok(taskService.listAllTask());
    }

<<<<<<< HEAD
    // create tasks - assign to collectors
    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createTask(@RequestBody TaskRequest taskRequest,
            @RequestHeader String token) throws CustomException {
        taskService.createTask(taskRequest.getCollectorEmail(), taskRequest.getMachineId());
        return ResponseEntity.ok(new GeneralResponse("Tasks created successfully!"));
    }

    @GetMapping("/mytasks")
    public ResponseEntity<?> listMyTask(@RequestHeader String token) {

        return ResponseEntity.ok("");
    }
    // collectors to view tasks where colletctors id = self
    // collectors to mark as delivered
    // machine to mark as collected
    // delete task
=======
    //

    //create task - assign to collectors

    //collectors to view tasks assigned to them

    //collectors to mark as delivered


>>>>>>> master
}
