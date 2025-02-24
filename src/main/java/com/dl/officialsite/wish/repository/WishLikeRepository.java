package com.dl.officialsite.wish.repository;

import com.dl.officialsite.wish.domain.WishLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName WishLikeRepository
 * @Author jackchen
 * @Date 2025/2/24 21:42
 * @Description WishLikeRepository
 **/
public interface WishLikeRepository extends JpaRepository<WishLike, Long>,
    JpaSpecificationExecutor<WishLike> {

    @Query(value = "select * from wish_like where member_id = ?1 and wish_id = ?2", nativeQuery =
        true)
    Optional<WishLike> findByMemberIdAndWishId(Long memberId, Long id);
}
