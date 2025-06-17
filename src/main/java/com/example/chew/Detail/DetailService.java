package com.example.chew.Detail;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DetailService {

    int countAllRecords();

    List<DetailDTO> getPagedStores(int start, int end);

    int countSearchRecords(String keyword);

    List<DetailDTO> searchStores(String keyword, int start, int end);

    DetailDTO getStoreDetail(int storecode);
}
