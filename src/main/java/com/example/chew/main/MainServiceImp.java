package com.example.chew.main;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImp implements MainService {

    @Autowired
    MainRepository mainRepository;

    @Override
    public List<MainInterface> getTop3StoresByArea(String area) {
        return mainRepository.findTop3ByArea(area);
    }

    @Override
    public List<MainInterface> getTop3StoresByreview() {
        return mainRepository.findTop3Byreview();
    }
}
