package com.backend.service;

import com.backend.model.TaskModel;
import com.backend.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    TaskRepo taskRepo;

    public List<TaskModel> listAllTask() {
        return taskRepo.findAll();
    }
}
