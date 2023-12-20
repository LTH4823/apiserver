package org.taerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "tbl_todo")
public class Todo {

	// primary Key 비교는 = 나 hashcode를 사용하기 떄문에 int 같은 기본 자료형은 쓰지 않습니다.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tno;

	@Column(length = 500, nullable = false)
	private  String title;

	private  String content;

	private boolean complete;

	private LocalDate dueDate;


}
