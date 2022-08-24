package com.kaibutsusama.stock.system.user.service;

import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.entity.user.TradeUserFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/24 16:12
 */
public interface IStockUserFileService {
    /**
     * 上传用户的身份证文件信息
     * @param userId
     * @param file
     * @return
     */
    String uploadUserIdCard(Long userId, MultipartFile file) throws Exception;

    /**
     * 根据文件ID查找文件对象
     * @param fileId
     * @return
     */
    TradeUserFile getTradeUserFile(String fileId) throws ComponentException;
}
