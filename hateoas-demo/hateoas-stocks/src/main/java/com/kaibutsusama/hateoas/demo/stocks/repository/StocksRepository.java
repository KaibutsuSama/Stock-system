package com.kaibutsusama.hateoas.demo.stocks.repository;

import com.kaibutsusama.hateoas.demo.stocks.entity.StocksEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/11 8:55
 */
@RepositoryRestResource(path = "/stocks")
public interface StocksRepository extends JpaRepository<StocksEntity,Long> {

    /**
     * 根据股票名称查找对应的数据
     * @param list
     * @return
     */
    List<StocksEntity> findByNameInOrderById(@Param("list")List<String> list);

    /**
     * 根据名字查询股票信息
     * @param name
     * @return
     */
    public StocksEntity findByName(@Param("name")String name);
}
