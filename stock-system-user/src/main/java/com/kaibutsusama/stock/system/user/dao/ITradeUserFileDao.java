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
     * 新增用户文件
     * @param record
     * @return
     */
    int insert(TradeUserFile record);

    /**
     * 更新用户文件
     * @param record
     * @return
     */
    int update(TradeUserFile record);

    /**
     * 根据用户ID获取对象
     * @param userId
     * @return
     */
    List<TradeUserFile> getByUserId(Long userId);

    /**
     * 根据文件标识ID获取对象
     * @param fileId
     * @return
     */
    TradeUserFile getByUserIdAndBizType(@Param("userId") Long userId, @Param("bizType")Integer bizType);

    /**
     * 根据文件ID获取对象
     * @param userId
     * @param bizType
     * @return
     */
    TradeUserFile getByFileId(@Param("fileId") Long fileId);


}
