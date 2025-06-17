package com.example.chew.Likes;

import com.example.chew.Detail.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LikesServiceImp implements LikesService {

    @Autowired
    LikesRepository likesRepository;
    @Autowired
    DetailRepository detailRepository;


    @Override
    @Transactional
    public boolean toggleLike(String memberId, Integer storecode) {
        boolean exists = likesRepository.existsByMemberIdAndStorecode(memberId, storecode);
        if (exists) {
            likesRepository.deleteByMemberIdAndStorecode(memberId, storecode);
            likesRepository.flush();
            detailRepository.decreaseLikes(storecode);
            return false;
        } else {
            likesRepository.save(new LikesEntity(memberId, storecode, LocalDateTime.now()));
            likesRepository.flush();
            detailRepository.increaseLikes(storecode);
            return true;
        }
    }

    @Override
    public int getStoreLikes(Integer storecode) {
        return detailRepository.findById(storecode)
                .map(store -> {
                    Integer likes = store.getStorelikes();
                    return likes != null ? likes : 0;
                })
                .orElse(0);

    }

    @Override
    public boolean isLiked(String memberId, Integer storecode) {
        return likesRepository.existsByMemberIdAndStorecode(memberId, storecode);

    }
}
