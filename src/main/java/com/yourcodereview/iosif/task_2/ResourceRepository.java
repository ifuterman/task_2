package com.yourcodereview.iosif.task_2;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
    List<Resource> findByLink(@Param("link") String link);
    List<Resource> findByOriginal(@Param("original") String original);
}
