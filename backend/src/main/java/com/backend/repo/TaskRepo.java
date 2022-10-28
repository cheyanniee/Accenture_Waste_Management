package com.backend.repo;

import com.backend.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<TaskModel, Long> {
}
