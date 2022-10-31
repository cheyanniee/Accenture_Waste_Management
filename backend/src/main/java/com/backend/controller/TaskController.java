package com.backend.controller;

import com.backend.configuration.CustomException;
import com.backend.model.TaskModel;
import com.backend.request.TaskRequest;
import com.backend.response.GeneralResponse;
import com.backend.service.TaskService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // create tasks - assign to collectors
    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createTask(@RequestBody TaskRequest taskRequest,
            @RequestHeader String token) throws CustomException {
        taskService.createTask(
                taskRequest.getCollectorEmail(),
                taskRequest.getMachineId(),
                taskService.getAdminByToken(token));
        return ResponseEntity.ok(new GeneralResponse("Tasks created successfully!"));
    }

    @GetMapping("/collector/{collectorId}")
    public ResponseEntity<List<TaskModel>> listCollectorTasks(@RequestHeader String token,
            @PathVariable String collectorId) throws NumberFormatException, CustomException {
        return ResponseEntity.ok(taskService.listTasksByCollectorId(Long.valueOf(collectorId), token));
    }

    // collectors to view tasks where colletctors id = self
    @GetMapping("/collector")
    public ResponseEntity<List<TaskModel>> listMyTask(@RequestHeader String token)
            throws NumberFormatException, CustomException {
        return ResponseEntity.ok(taskService.listTasksByCollectorId(0L, token));
    }

    // collectors to mark as delivered
    @GetMapping("/collector/delivered/{taskId}")
    public ResponseEntity<GeneralResponse> tasksDelivered(@RequestHeader String token, @PathVariable String taskId)
            throws NumberFormatException, CustomException {
        taskService.updateDeliveredTaskByCollectorId(Long.valueOf(taskId), token);
        return ResponseEntity.ok(new GeneralResponse("Task delivered!"));
    }

    // machine to mark as collected
    @GetMapping("/machine/collected/{taskId}")
    public ResponseEntity<GeneralResponse> taskCollected(@RequestHeader String token, @PathVariable String taskId)
            throws NumberFormatException, CustomException {
        taskService.updateCollectedTime(Long.valueOf(taskId), token);
        return ResponseEntity.ok(new GeneralResponse("Task Collected"));
    }

    // delete task
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<GeneralResponse> deleteTask(@RequestHeader String token, @PathVariable String taskId)
            throws NumberFormatException, CustomException {
        taskService.getAdminByToken(token);
        taskService.deleteTask(Long.valueOf(taskId));
        return ResponseEntity.ok(new GeneralResponse("Task deleted!"));
    }

    // Admin update task
    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> updateTask(@RequestHeader String token,
            @RequestBody TaskRequest taskRequest) throws CustomException {
        taskService.manualUpdate(taskService.getAdminByToken(token), taskRequest);
        return ResponseEntity.ok(new GeneralResponse("Task update!"));
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<TaskModel>> listTaskByMachineId(@PathVariable String machineId) {
        return ResponseEntity.ok(taskService.getTaskbyMachineId(Integer.valueOf(machineId)));
    }
}
