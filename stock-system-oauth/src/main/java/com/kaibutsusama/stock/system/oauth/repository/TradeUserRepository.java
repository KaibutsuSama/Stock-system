package com.kaibutsusama.stock.system.oauth.repository;

import com.kaibutsusama.stock.system.entity.user.TradeUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:32
 */
@Repository("tradeUserRepository")
public interface TradeUserRepository extends PagingAndSortingRepository<TradeUser, String>, JpaSpecificationExecutor<TradeUser> {

    /**
     * 根据用户账号获取用户对象
     * @param userNo
     * @return
     */
    public TradeUser findByUserNo(String userNo);
}