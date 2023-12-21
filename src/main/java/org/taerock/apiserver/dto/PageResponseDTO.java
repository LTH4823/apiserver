package org.taerock.apiserver.dto;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResponseDTO<E> {

	private List<E> dtoList;

	//컬렉션은 기본자료형이 못들어가기 때문에 Integer 사용
	private List<Integer> pageNumList;

	private PageRequestDTO pageRequestDTO;

	private boolean prev, next;

	private int totalCount, prevPage, nextPage, current;

	public PageResponseDTO(List<E> dtoList, PageRequestDTO pageRequestDTO, long total){

		// 생성자 선언
		this.dtoList = dtoList;
		this.pageRequestDTO = pageRequestDTO;
		this.totalCount = (int)total;

		// 시작 끝 페이지 end
		int end = (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10;
		int start = end - 9;

		// 진짜 마지막 페이지
		int last = (int)(Math.ceil(totalCount/(double)pageRequestDTO.getSize()));

		// 진짜 마지막 부분 여부
		end = end > last ? last : end;

		// 시작 구분 여부
		this.prev = start > 1;

		// 다음 페이지
		this.next = totalCount > end * pageRequestDTO.getSize();

		// 페이지 목록
		this.pageNumList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());

		// 10개 전 페이지
		this.prevPage = prev ? start - 1 : 0;

		// 10개 후 페이지
		this.nextPage = next ? end + 1 : 0;

	}

}
