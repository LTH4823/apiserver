package org.taerock.apiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static lombok.Builder.*;

// 상속에서 사용됩니다.
@SuperBuilder
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

	@Default
	private int page = 1;

	@Default
	private int size = 10;


}
