package org.taerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.taerock.apiserver.domain.Todo;
import org.taerock.apiserver.dto.TodoDTO;
import org.taerock.apiserver.repository.TodoRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

	private final TodoRepository todoRepository;

	@Override
	public TodoDTO get(Long tno) {

		Optional<Todo> result = todoRepository.findById(tno);

		Todo todo = result.orElseThrow();

		return entityToDTO(todo);
	}
}
