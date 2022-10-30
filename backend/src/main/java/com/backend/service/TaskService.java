package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.TaskModel;
import com.backend.model.PeopleModel.Role;
import com.backend.repo.MachineRepo;
import com.backend.repo.PeopleRepo;
import com.backend.repo.TaskRepo;
import com.backend.request.TaskRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;

    @Autowired
    PeopleRepo peopleRepo;

    @Autowired
    PeopleService peopleService;

    @Autowired
    MachineRepo machineRepo;

    private static final String NO_RIGHTS = "User do not have enough access rights to perform this operation!";

    // Listing all Tasks
    public List<TaskModel> listAllTask() {
        return taskRepo.findAll();
    }

    // Creating tasks and assigning collector to machine
    public boolean createTask(String collectorEmail, Integer machineId, PeopleModel admin) throws CustomException {
        MachineModel machine = machineRepo.getMachineById(machineId)
                .orElseThrow(() -> new CustomException("Machine not Found!"));
        TaskModel newTask = TaskModel.builder()
                .assignedTime(ZonedDateTime.now(ZoneId.of("Asia/Singapore")))
                .collector(getCollectorByEmail(collectorEmail))
                .admin(admin)
                .machine(machine)
                .build();
        taskRepo.save(newTask);
        return true;
    }

    public List<TaskModel> listTasksByCollectorId(Long collectorId, String token)
            throws NumberFormatException, CustomException {
        PeopleModel user = peopleService.findPeople(peopleService.getIdByToken(token))
                .orElseThrow(() -> new CustomException("User not found!"));

        if (user.getRole() == Role.collector)
            return taskRepo.getTaskByCollectorId(user.getId());
        if (user.getRole() == Role.admin)
            return taskRepo.getTaskByCollectorId(collectorId);

        throw new CustomException(NO_RIGHTS);
    }

    private PeopleModel getCollectorByEmail(String collectorEmail) throws CustomException {
        PeopleModel collector = peopleRepo.getPeopleByEmail(collectorEmail.toLowerCase())
                .orElseThrow(() -> new CustomException("Collector not found!"));
        if (collector.getRole() != Role.collector)
            throw new CustomException("Email provided does not have enough privilege rights");
        return collector;
    }

    public PeopleModel getAdminByToken(String token) throws CustomException {
        PeopleModel admin = peopleService.findPeople(peopleService.getIdByToken(token))
                .orElseThrow(() -> new CustomException("Admin not found!"));
        if (admin.getRole() != Role.admin)
            throw new CustomException(NO_RIGHTS);

        return admin;
    }

    public boolean deleteTask(Long taskId) throws CustomException {
        TaskModel task = taskRepo.getTaskById(taskId).orElseThrow(() -> new CustomException("Task not found"));
        taskRepo.delete(task);
        return true;
    }

    public boolean updateDeliveredTaskByCollectorId(Long taskId, String token) throws CustomException {
        // if update failed, throws exception
        if (taskRepo.updateDeliveredTaskByCollectorId(ZonedDateTime.now(ZoneId.of("Asia/Singapore")), taskId,
                peopleService.getIdByToken(token)) == 0)
            throw new CustomException("No task delivered");

        return true;
    }

    public boolean updateCollectedTime(Long taskId, String token) throws CustomException {

        if (taskRepo.updateCollectedTime(ZonedDateTime.now(ZoneId.of("Asia/Singapore")), taskId) == 0)
            throw new CustomException("Task update collected fails!");
        return true;
    }

    private TaskModel getTaskById(Long taskId) throws CustomException {
        return taskRepo.getTaskById(taskId).orElseThrow(() -> new CustomException("No Task found!"));
    }

    public boolean manualUpdate(PeopleModel admin, TaskRequest taskRequest) throws CustomException {
        TaskModel task = getTaskById(taskRequest.getTaskId());
        if (taskRequest.getAssignedTime() != null)
            task.setAssignedTime(taskRequest.getAssignedTime());
        if (taskRequest.getCollectedTime() != null)
            task.setCollectedTime(taskRequest.getCollectedTime());
        if (taskRequest.getDeliveredTime() != null)
            task.setDeliveredTime(taskRequest.getDeliveredTime());
        taskRepo.save(task);
        return true;
    }
}
