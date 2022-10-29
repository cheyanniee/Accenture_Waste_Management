package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.PeopleModel;
import com.backend.model.TaskModel;
import com.backend.model.PeopleModel.Role;
import com.backend.repo.PeopleRepo;
import com.backend.repo.TaskRepo;
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

    // Listing all Tasks
    public List<TaskModel> listAllTask() {
        return taskRepo.findAll();
    }

    // Creating tasks and assigning collector to machine
    public boolean createTask(String collectorEmail, Integer machineId) throws CustomException {
        TaskModel newTask = TaskModel.builder()
                .assignedTime(ZonedDateTime.now(ZoneId.of("Asia/Singapore")))
                .collector(getCollectorByEmail(collectorEmail))
                .machine(null)
                .build();
        taskRepo.save(newTask);
        return true;
    }

    private PeopleModel getCollectorByEmail(String collectorEmail) throws CustomException {
        PeopleModel collector = peopleRepo.getPeopleByEmail(collectorEmail.toLowerCase())
                .orElseThrow(() -> new CustomException("Collector not found!"));
        if (collector.getRole() != Role.collector)
            throw new CustomException("Email provided does not have enough privilege rights");
        return collector;
    }
}
