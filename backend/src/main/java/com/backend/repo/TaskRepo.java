package com.backend.repo;

import com.backend.model.TaskModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepo extends JpaRepository<TaskModel, Long> {

}
