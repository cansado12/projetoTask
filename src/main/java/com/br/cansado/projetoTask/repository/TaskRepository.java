package com.br.cansado.projetoTask.repository;

import com.br.cansado.projetoTask.model.Status;
import com.br.cansado.projetoTask.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(Status status, Pageable pageable);
}