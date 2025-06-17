package com.example.chew.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@Table(name = "FAQ_3")
@SequenceGenerator(
        name = "faq",
        sequenceName = "faq_seq",
        initialValue = 10000,
        allocationSize = 1
)
public class FAQEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq")
    Integer num;
    @Column
    String id;
    @Column
    String title;
    @Column
    String content;
    @Column
    LocalDate wdate;
    @Column
    String state;
    @Column
    Integer groups;
    @Column
    Integer step;
    @Column
    Integer secret;

    @PrePersist
    public void onCreate() {
        this.wdate = LocalDate.now();
    }
}
