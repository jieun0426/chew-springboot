package com.example.chew.dto;

import com.example.chew.entity.FAQEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FAQDTO {
    int num;
    String id;
    String title;
    String content;
    LocalDate wdate;
    String state;
    int groups;
    int step;
    int secret;

    public FAQEntity q_entity(){
        return FAQEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .state("미답변")
                .step(0)
                .secret(secret)
                .build();
    }

    public FAQEntity a_entity() {
        return FAQEntity.builder()
                .id(id)
                .title(title)
                .content(content)
                .state("답변")
                .groups(num)
                .step(1)
                .secret(secret)
                .build();
    }
}
