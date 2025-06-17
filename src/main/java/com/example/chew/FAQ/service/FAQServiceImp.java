package com.example.chew.FAQ.service;

import com.example.chew.FAQ.repository.FAQRepository;
import com.example.chew.entity.FAQEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class FAQServiceImp implements FAQService {
    @Autowired
    FAQRepository faqRepository;

    @Override
    public Page<FAQEntity> myList(int page, String id) {
        return faqRepository.findById(id, PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "num")));
    }

    @Override
    public Page<FAQEntity> allList(int page) {
        return faqRepository.findByStep(0,PageRequest.of(page,5, Sort.by(Sort.Direction.DESC, "num")));
    }

    @Override
    public void insertQuestion(FAQEntity entity) {
        faqRepository.save(entity);
        entity.setGroups(entity.getNum());

        // groups 업데이트
        faqRepository.save(entity);
    }

    @Override
    public FAQEntity selectQuestion(String qnum) {
        return faqRepository.findQuestionByNum(qnum);
    }

    @Override
    public FAQEntity selectAnswer(String qnum) {
        return faqRepository.findAnswerByNum(qnum);
    }

    @Override
    public void insertAnswer(FAQEntity entity) {
        faqRepository.save(entity);
    }

    @Override
    public void updateQuestionState(int num) {
        faqRepository.updateQuestionState(num);
    }

    @Override
    @Transactional
    public void updateAnswer(FAQEntity entity) {
        faqRepository.updateAnswer(entity.getContent(), entity.getId(), entity.getGroups());
    }

    @Override
    public void deleteByNum(int num) {
        faqRepository.deleteById(num);
    }
}
