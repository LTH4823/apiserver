package org.taerock.apiserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taerock.apiserver.domain.Todo;
import org.taerock.apiserver.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

}
