package org.taerock.apiserver.service;

import org.springframework.transaction.annotation.Transactional;
import org.taerock.apiserver.domain.Member;
import org.taerock.apiserver.dto.MemberDTO;

import java.util.stream.Collectors;

@Transactional
public interface MemberService {

    MemberDTO getKakaoMember(String accessToken);

    default MemberDTO entityToDTO(Member member) {
        MemberDTO dto = new MemberDTO(
                member.getEmail(),
                member.getPw(),
                member.getNickname(),
                member.isSocial(),
                member.getMemberRoleList().stream().map(memberRole -> memberRole.name()).collect(Collectors.toList()));
        return dto;
    }

}
