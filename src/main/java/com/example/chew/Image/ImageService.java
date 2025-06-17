package com.example.chew.Image;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {

    List<ImageDTO> getImagesByStoreCode(int storecode);
}
