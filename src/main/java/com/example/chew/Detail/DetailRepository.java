package com.example.chew.Detail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailRepository extends JpaRepository<DetailEntity, Integer> {

    @Query(value = "SELECT COUNT(*) FROM STORE_3", nativeQuery = true)
    int countAll();

    @Query(
            value = "SELECT * FROM (" +
                    " SELECT a.*, ROWNUM rnum FROM (" +
                    "   SELECT * FROM store_3 ORDER BY storecode" +
                    " ) a WHERE ROWNUM <= :end" +
                    ") WHERE rnum > :start",
            nativeQuery = true
    )
    List<DetailEntity> findStoresWithPaging(@Param("start") int start, @Param("end") int end);

    @Query(
            value = "SELECT COUNT(*) FROM store_3 " +
                    "WHERE storename LIKE '%' || :keyword || '%' " +
                    "   OR storeaddress LIKE '%' || :keyword || '%' " +
                    "   OR ( :keyword = '서울' AND storeaddress LIKE '%서울%' ) " +
                    "   OR ( :keyword = '경기' AND storeaddress LIKE '%경기도%' ) " +
                    "   OR ( :keyword = '인천' AND storeaddress LIKE '%인천%' )",
            nativeQuery = true
    )
    int countByKeyword(String keyword);

    @Query(
            value = "SELECT * FROM ( " +
                    " SELECT a.*, ROWNUM rnum FROM ( " +
                    "   SELECT * FROM store_3 " +
                    "   WHERE storename LIKE '%' || :keyword || '%' " +
                    "      OR storeaddress LIKE '%' || :keyword || '%' " +
                    "      OR ( :keyword = '서울' AND storeaddress LIKE '%서울%' ) " +
                    "      OR ( :keyword = '경기' AND storeaddress LIKE '%경기도%' ) " +
                    "      OR ( :keyword = '인천' AND storeaddress LIKE '%인천%' ) " +
                    "   ORDER BY storecode " +
                    " ) a WHERE ROWNUM <= :end " +
                    ") WHERE rnum > :start",
            nativeQuery = true
    )
    List<DetailEntity> searchByKeywordNative(@Param("keyword") String keyword,
                                             @Param("start") int start,
                                             @Param("end") int end);

    Optional<DetailEntity> findByStorecode(int storecode);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DetailEntity s SET s.storelikes = CASE WHEN s.storelikes > 0 THEN s.storelikes - 1 ELSE 0 END WHERE s.storecode = :storecode")

    void decreaseLikes(Integer storecode);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE DetailEntity s SET s.storelikes = s.storelikes + 1 WHERE s.storecode = :storecode")
    void increaseLikes(Integer storecode);
}
