package org.taerock.apiserver.repository.search;

import org.springframework.data.domain.Page;
import org.taerock.apiserver.domain.Todo;
import org.taerock.apiserver.dto.PageRequestDTO;

public interface TodoSearch {

	Page<Todo> search1(PageRequestDTO pageRequestDTO);

}
