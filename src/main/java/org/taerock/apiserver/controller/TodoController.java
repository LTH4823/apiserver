package org.taerock.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.taerock.apiserver.dto.PageRequestDTO;
import org.taerock.apiserver.dto.PageResponseDTO;
import org.taerock.apiserver.dto.TodoDTO;
import org.taerock.apiserver.service.TodoService;

import java.util.Map;

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

	@PostMapping("/")
	public Map<String, Long>register(@RequestBody TodoDTO dto){

		log.info("todoDTO: " + dto);

		Long tno = todoService.register(dto);

		return Map.of("TNO", tno);
	}

	@PutMapping("/{tno}")
	public Map<String, String>modify(@PathVariable("tno") Long tno, @RequestBody TodoDTO dto){

		dto.setTno(tno);

		todoService.modify(dto);

		return Map.of("RESULT", "SUCCESS");
	}


	@DeleteMapping("/{tno}")
	public Map<String, String>remove(@PathVariable("tno") Long tno){

		todoService.remove(tno);

		return Map.of("RESULT", "SUCCESS");
	}
}
