package com.example.chew.Image;

import com.example.chew.entity.StoreImageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageServiceImp implements ImageService{

    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<ImageDTO> getImagesByStoreCode(int storecode) {
        List<StoreImageEntity> images = imageRepository.findById_Storecode(storecode);

        return images.stream()
                .map(img -> new ImageDTO(
                        img.getId().getStorecode(),
                        img.getId().getImageFilename()))
                .collect(Collectors.toList());
    }

}
