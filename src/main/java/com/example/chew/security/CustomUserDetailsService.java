package com.example.chew.security;

import com.example.chew.JoinLogin.repository.MemberRepository;
import com.example.chew.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    //회원정보를 담은 인터페이스를 상속받음
    private final MemberRepository memberRepository;
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String id) {//회원정보를 담는
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        //GrantedAuthority: 현재 사용자의 권한 admin or user를 role로 표시(role=역할)
        log.info("현재 로그인 중 id : "+id);
        MemberEntity memberEntity = memberRepository.findOneById(id);
        //findOneById의 정보를 가져와서 user에 담음
        if (memberEntity != null) {
            // SimpleGrantedAuthority는 GrantedAuthority를 상속받은 클래스
            // 관리자 판단 (id가 'admin'이거나 role 필드 확인)
            if (id.equals("admin")) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                // role를 USER로 설정(역할을 user로 설정)
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new User(memberEntity.getId(), memberEntity.getPw(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("can not find User : " + id);
        }
    }
}
