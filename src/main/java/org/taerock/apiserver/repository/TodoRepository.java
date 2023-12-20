package org.taerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taerock.apiserver.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
