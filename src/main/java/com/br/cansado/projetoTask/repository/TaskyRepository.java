package com.br.cansado.projetoTask.repository;

import com.br.cansado.projetoTask.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskyRepository extends JpaRepository<Task, Long> {

}
