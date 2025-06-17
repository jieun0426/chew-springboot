package com.example.chew.membermanage.service;

import com.example.chew.entity.MemberEntity;
import com.example.chew.membermanage.repository.MemberManageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberManageServiceImp implements MemberManageService {
    @Autowired
    MemberManageRepository memberManageRepository;

    @Override
    public int delete(String id) {
        memberManageRepository.deleteById(id);
        return 1;
    }

    @Override
    public int update(MemberEntity dto) {
        memberManageRepository.updateMember(dto.getId(), dto.getName(), dto.getPhone(), dto.getBirth());
        return 1;
    }

    @Override
    public Page<MemberEntity> allMemberList(int page) {
        return memberManageRepository.findAll(PageRequest.of(page, 10));
    }

    @Override
    public List<MemberEntity> searchById(String searchValue) {
        return memberManageRepository.findByIdContaining(searchValue);
    }

    @Override
    public List<MemberEntity> searchByName(String searchValue) {
        return memberManageRepository.findByNameContaining(searchValue);
    }

    @Override
    public MemberEntity selectOne(String id) {
        return memberManageRepository.findById(id).orElse(null);
    }

}
