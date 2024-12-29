package com.dl.officialsite.wish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @ClassName WishRepository
 * @Author jackchen
 * @Date 2024/12/25 16:57
 * @Description WishRepository
 **/
public interface WishRepository extends JpaRepository<Wish, Long>,
    JpaSpecificationExecutor<Wish> {

}
