package com.kaibutsusama.stock.system.user.dao;

import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.entity.user.TradeUserFile;
import com.kaibutsusama.stock.system.user.vo.CompanyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:48
 */
@Repository
public interface ITradeUserFileDao {
    /**
     * 新增交易账号的文件对象
     * @param record
     * @return
     */
    int insert(TradeUserFile record);

    /**
     * 根据用户ID获取所有文件信息
     * @param userId
     * @return
     */
    List<TradeUserFile> getByUserId(Long userId);


}
