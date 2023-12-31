package org.taerock.apiserver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.taerock.apiserver.domain.Todo;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.TodoDTO;
import org.taerock.apiserver.repository.TodoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	@Override
	public Long register(TodoDTO dto) {
		Todo todo = dtoToEntity(dto);
		Todo result = todoRepository.save(todo);
		return result.getTno();
	}

	@Override
	public void modify(TodoDTO dto) {

		Optional<Todo> result = todoRepository.findById(dto.getTno());

		Todo todo = result.orElseThrow();

		todo.changeTitle(dto.getTitle());
		todo.changeContent(dto.getContent());
		todo.changeComplete(dto.isComplete());
		todo.changeDueDate(dto.getDueDate());

		todoRepository.save(todo);

	}

	@Override
	public void remove(Long tno) {
		todoRepository.deleteById(tno);
	}

	@Override
	public PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO) {

		// JPA
		Page<Todo> result = todoRepository.search1(pageRequestDTO);

		// Todo list => TodoDTO list
		List<TodoDTO> dtoList =  result
				.get()
				.map(todo -> entityToDTO(todo)).collect(Collectors.toList());

		PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
			.dtoList(dtoList)
			.pageRequestDTO(pageRequestDTO)
			.total(result.getTotalElements())
			.build();

		return responseDTO;
	}
}
