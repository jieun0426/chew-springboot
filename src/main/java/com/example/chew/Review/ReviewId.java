package com.example.chew.Review;

import java.io.Serializable;
import java.util.Objects;

public class ReviewId implements Serializable {
    private String id;
    private int storecode;

    public ReviewId() {}

    public ReviewId(String id, int storecode) {
        this.id = id;
        this.storecode = storecode;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getStorecode() { return storecode; }
    public void setStorecode(int storecode) { this.storecode = storecode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewId)) return false;
        ReviewId that = (ReviewId) o;
        return storecode == that.storecode && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, storecode);
    }
}
