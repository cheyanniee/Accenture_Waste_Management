package com.backend.service;

import com.backend.configuration.CustomException;
import com.backend.model.MachineModel;
import com.backend.model.PeopleModel;
import com.backend.model.TaskModel;
import com.backend.model.PeopleModel.Role;
import com.backend.repo.MachineRepo;
import com.backend.repo.PeopleRepo;
import com.backend.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

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
            throw new CustomException("User do not have enough access rights to perform this operation!");

        return admin;
    }
}
