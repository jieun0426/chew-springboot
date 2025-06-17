package com.example.chew.entity;

import com.example.chew.Review.ReviewId;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "review_3")
public class ReviewEntity {
    // fields
    @EmbeddedId
    private ReviewId id;

    @Column
    private String content;

    @Column
    private Double stars;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storecode", referencedColumnName = "storecode", insertable = false, updatable = false)
    private StoreEntity store;
}