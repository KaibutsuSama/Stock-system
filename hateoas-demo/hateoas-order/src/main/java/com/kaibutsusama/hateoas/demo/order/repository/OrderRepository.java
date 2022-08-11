package com.kaibutsusama.hateoas.demo.order.repository;

import com.kaibutsusama.hateoas.demo.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 9:51
 */
@RepositoryRestResource(path="/order")
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    /**
     * 根据用户查找订单信息
     * @param user
     * @return
     */
    public List<OrderEntity> findByUser(@Param("user") String user);
}
