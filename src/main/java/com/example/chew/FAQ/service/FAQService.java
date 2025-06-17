package com.example.chew.FAQ.service;

import com.example.chew.entity.FAQEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface FAQService {
    Page<FAQEntity> myList(int page, String id);

    Page<FAQEntity> allList(int page);

    void insertQuestion(FAQEntity entity);

    FAQEntity selectQuestion(String qnum);

    FAQEntity selectAnswer(String qnum);

    void insertAnswer(FAQEntity entity);

    void updateQuestionState(int num);

    void updateAnswer(FAQEntity entity);

    void deleteByNum(int num);
}
