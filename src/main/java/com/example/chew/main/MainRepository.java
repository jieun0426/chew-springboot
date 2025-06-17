package com.example.chew.main;


import com.example.chew.main.entity.MainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository extends JpaRepository<MainEntity,Long> {

    @Query(value = "SELECT * FROM (\n" +
            "  SELECT inner_query.*, ROWNUM AS rn FROM (\n" +
            "    SELECT s.STORECODE, s.STORENAME, s.STOREIMAGE, s.STOREAREA, ROUND(AVG(r.STARS), 1) AS STARS\n" +
            "    FROM STORE_3 s\n" +
            "    JOIN REVIEW_3 r ON s.STORECODE = r.STORECODE\n" +
            "    WHERE s.STOREAREA = :area\n" +
            "    GROUP BY s.STORECODE, s.STORENAME, s.STOREIMAGE, s.STOREAREA\n" +
            "    ORDER BY STARS DESC\n" +
            "  ) inner_query\n" +
            "  WHERE ROWNUM <= 3\n" +
            ")\n",
            nativeQuery = true)
    List<MainInterface> findTop3ByArea(@Param("area") String area);


    @Query(value = """
                SELECT *
                FROM (
                    SELECT s.STORECODE AS storecode,
                           s.STORENAME AS storename,
                           s.STOREIMAGE AS storeimage,
                           ROUND(AVG(r.STARS), 1) AS stars
                    FROM STORE_3 s
                    JOIN REVIEW_3 r ON s.STORECODE = r.STORECODE
                    GROUP BY s.STORECODE, s.STORENAME, s.STOREIMAGE
                    ORDER BY stars DESC
                )
                WHERE ROWNUM <= 3
            """, nativeQuery = true)
    List<MainInterface> findTop3Byreview();
}
