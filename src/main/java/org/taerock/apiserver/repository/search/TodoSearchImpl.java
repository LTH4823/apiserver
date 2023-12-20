package org.taerock.apiserver.repository.search;

import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.taerock.apiserver.domain.QTodo;
import org.taerock.apiserver.domain.Todo;

@Log4j2
public class TodoSearchImpl extends QuerydslRepositorySupport implements TodoSearch{

	public TodoSearchImpl() {
		super(Todo.class);
	}

	@Override
	public Page<Todo> search1() {

		log.info("search1.................");

		QTodo todo = QTodo.todo;

		JPQLQuery<Todo> query = from(todo);

		query.where(todo.title.contains("1"));

		query.fetch();

		return null;
	}
}
