package org.taerock.apiserver.repository.search;

import org.springframework.data.domain.Page;
import org.taerock.apiserver.domain.Todo;

public interface TodoSearch {

	Page<Todo> search1();

}
