package com.example.chew.Detail;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailServiceImp implements DetailService {

    @Autowired
    DetailRepository detailRepository;


    @Override
    public int countAllRecords() {
        return detailRepository.countAll();
    }

    @Override
    public List<DetailDTO> getPagedStores(int start, int end) {
        List<DetailEntity> pagedStores = detailRepository.findStoresWithPaging(start, end);
        return pagedStores.stream()
                .map(detail -> detail.toDTO())
                .collect(Collectors.toList());
    }

    @Override
    public int countSearchRecords(String keyword) {
        return detailRepository.countByKeyword(keyword);
    }

    @Override
    public List<DetailDTO> searchStores(String keyword, int start, int end) {
        List<DetailEntity> searchResults = detailRepository.searchByKeywordNative(keyword, start, end);
        return searchResults.stream()
                .map(DetailEntity::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DetailDTO getStoreDetail(int storecode) {
        DetailEntity entity = detailRepository.findByStorecode(storecode)
                .orElseThrow(() -> new NoSuchElementException("가게를 찾을 수 없습니다. code: " + storecode));
        return DetailDTO.fromEntity(entity);
    }


}
