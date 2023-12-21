package org.taerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.TodoDTO;
import org.taerock.apiserver.service.TodoService;

@RestController
@Log4j2
@RequiredArgsConstructor
// api 명칭 잡을 시 단어를 복수형을 권장
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService todoService;

	//
	@GetMapping("/{tno}")
	public TodoDTO get(@PathVariable("tno") Long tno){
		return todoService.get(tno);
	}

	@GetMapping("/list")
	public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){

		log.info("list..........." + pageRequestDTO);

		return todoService.getList(pageRequestDTO);
	}

}
