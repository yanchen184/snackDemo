package com.yc.snackoverflow.reposity;

import com.yc.snackoverflow.data.ColorCustomResponseData;
import com.yc.snackoverflow.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsColorDao extends JpaRepository<Color, Long> {

    @Query(value = "SELECT  CONCAT(c.code , c.zh_name) AS codeAndZhName " +
            ", CONCAT(c.code , c.en_name) AS codeAndEnName " +
            "FROM color c", nativeQuery = true)
    List<ColorCustomResponseData> findColor();

    @Query(value = "SELECT c FROM Color c WHERE c.id IN (:ids)")
    List<Color> getColorByIds(List<Long> ids);
}
