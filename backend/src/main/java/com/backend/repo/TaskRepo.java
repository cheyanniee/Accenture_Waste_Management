package com.backend.repo;

import com.backend.model.TaskModel;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepo extends JpaRepository<TaskModel, Long> {
  @Query("SELECT tasks from TaskModel tasks WHERE collector_id=?1")
  List<TaskModel> getTaskByCollectorId(Long collectorId);

  @Query("SELECT task from TaskModel task WHERE id=?1")
  Optional<TaskModel> getTaskById(Long taskId);

  @Query("SELECT task from TaskModel task WHERE machine_id=?1")
  List<TaskModel> getTaskByMachineId(int machineId);

  @Query("SELECT task from TaskModel task WHERE machine_id=?1 AND collected_time IS NULL")
  TaskModel getTaskNotCollected(int machineId);

  @Modifying
  @Transactional
  @Query("UPDATE TaskModel SET delivered_time=?1 WHERE id=?2 AND collector_id=?3")
  Integer updateDeliveredTaskByCollectorId(ZonedDateTime deliveredTime, Long taskId, Long collectorId);

  @Modifying
  @Transactional
  @Query("UPDATE TaskModel SET collected_time=?1 WHERE id=?2")
  Integer updateCollectedTime(ZonedDateTime collectedTime, Long taskId);
}
