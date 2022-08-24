package com.kaibutsusama.stock.system.user.service.impl;

import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.common.exception.constants.ApplicationErrorCodeEnum;
import com.kaibutsusama.stock.system.common.generator.GlobalIDGenerator;
import com.kaibutsusama.stock.system.common.utils.GlobalConstants;
import com.kaibutsusama.stock.system.entity.user.TradeUserFile;
import com.kaibutsusama.stock.system.starter.ceph.CephSwiftOperator;
import com.kaibutsusama.stock.system.user.dao.ITradeUserFileDao;
import com.kaibutsusama.stock.system.user.service.IStockUserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/24 15:57
 */
@Service
public class StockUserFileServiceImpl implements IStockUserFileService {

    @Autowired
    private CephSwiftOperator cephSwiftOperator;

    @Autowired
    private GlobalIDGenerator globalIDGenerator;

    @Autowired
    private ITradeUserFileDao tradeUserFileDao;

    /**
     * 上传用户文件
     * @return
     */
    @Override
    public String uploadUserIdCard(Long userId, MultipartFile file) throws Exception {

        if(null == file) {
            // 文件不能为空
            throw new ComponentException(ApplicationErrorCodeEnum.USER_FILE_NOT_FOUND);
        }

        // 获取唯一文件ID标识
        String remoteFileId = globalIDGenerator.nextStrId();

        // 上传文件至CEPH
        cephSwiftOperator.createObject(remoteFileId, file.getBytes());

        // 查找对应用户文件
        TradeUserFile dbTradeUserFile = tradeUserFileDao.getByUserIdAndBizType(userId, GlobalConstants.FILE_BIZ_TYPE_IDCARD);
        if(null == dbTradeUserFile){
            // 新增用户文件
            dbTradeUserFile = new TradeUserFile();
            dbTradeUserFile.setBizType(GlobalConstants.FILE_BIZ_TYPE_IDCARD);
            dbTradeUserFile.setFilename(file.getOriginalFilename());
            dbTradeUserFile.setFileType(file.getContentType());
            dbTradeUserFile.setFileId(remoteFileId);
            dbTradeUserFile.setUserId(userId);
            dbTradeUserFile.setCreateTime(new Date());
            tradeUserFileDao.insert(dbTradeUserFile);
        }else {

            // 清理原有文件对象
            cephSwiftOperator.deleteObject(dbTradeUserFile.getFileId());
            // 更新用户文件
            dbTradeUserFile.setFileId(remoteFileId);
            dbTradeUserFile.setFilename(file.getOriginalFilename());
            dbTradeUserFile.setFileType(file.getContentType());
            tradeUserFileDao.update(dbTradeUserFile);
        }

        return remoteFileId;
    }

    @Override
    public TradeUserFile getTradeUserFile(String fileId) throws ComponentException {
        TradeUserFile tradeUserFile = tradeUserFileDao.getByFileId(Long.valueOf(fileId));
        if(null == tradeUserFile) {
            throw new ComponentException(ApplicationErrorCodeEnum.USER_FILE_NOT_FOUND);
        }
        return tradeUserFile;
    }
}
