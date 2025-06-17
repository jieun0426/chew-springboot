package com.example.chew.main;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MainService {

    List<MainInterface> getTop3StoresByArea(String area);

    List<MainInterface> getTop3StoresByreview();
}
