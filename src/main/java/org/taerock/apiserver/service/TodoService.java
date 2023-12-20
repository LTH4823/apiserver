package org.taerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.taerock.apiserver.domain.Todo;
import org.taerock.apiserver.dto.TodoDTO;

@Transactional
public interface TodoService {

	TodoDTO get(Long tno);

	// java.8 부터 default를 사용하여 인터페이스에서도 기능을 선언하여 실행 할 수 있습니다.
	// modelMapper나 자동으로 mapping 해주는 도구를 이용해도 되지만 단순하거나 간략한 구조일 때는 아래의 구조를 쓰시는게 추후 처리가 편합니다.
	default TodoDTO entityToDTO(Todo todo){
		return TodoDTO.builder()
						.tno(todo.getTno())
						.title(todo.getTitle())
						.content(todo.getContent())
						.complete(todo.isComplete())
						.dueDate(todo.getDueDate())
						.build();
	}

	default Todo dtoToEntity(TodoDTO todoDTO){
		return Todo.builder()
				.tno(todoDTO.getTno())
				.title(todoDTO.getTitle())
				.content(todoDTO.getContent())
				.complete(todoDTO.isComplete())
				.dueDate(todoDTO.getDueDate())
				.build();
	}

}
