package com.backend.repo;

import com.backend.model.TaskModel;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepo extends JpaRepository<TaskModel, Long> {
  @Query("SELECT tasks from TaskModel tasks WHERE collector_id=?1")
  List<TaskModel> getTaskByCollectorId(Long collectorId);
}
