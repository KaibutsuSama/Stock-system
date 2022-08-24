package com.kaibutsusama.stock.system.user.controller;

import com.kaibutsusama.stock.system.common.exception.ComponentException;
import com.kaibutsusama.stock.system.common.exception.constants.ApplicationErrorCodeEnum;
import com.kaibutsusama.stock.system.entity.user.TradeUser;
import com.kaibutsusama.stock.system.entity.user.TradeUserFile;
import com.kaibutsusama.stock.system.starter.ceph.CephSwiftOperator;
import com.kaibutsusama.stock.system.user.service.IStockUserFileService;
import com.kaibutsusama.stock.system.user.service.IStockUserService;
import com.kaibutsusama.system.common.web.ApiRespResult;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 19:47
 */
@RestController()
@RequestMapping("/user")
@Log4j2
public class StockUserController extends BaseController {

    @Autowired
    private IStockUserService stockUserService;

    @Autowired
    private IStockUserFileService stockUserFileService;

    @Autowired
    private CephSwiftOperator cephSwiftOperator;

    /**
     * 用户登陆接口
     * @param userNo
     * @param userPwd
     * @return
     */
    @RequestMapping("/userLogin")
    public ApiRespResult userLogin(@RequestParam("userNo")String userNo, @RequestParam("userPwd") String userPwd) {

        ApiRespResult  result = null;
        try {
            // 用户登陆逻辑处理
            TradeUser tradeUser = stockUserService.userLogin(userNo, userPwd);
            result = ApiRespResult.success(tradeUser);
        }catch(ComponentException e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.error(e.getErrorCodeEnum());
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            result = ApiRespResult.sysError(e.getMessage());
        }

        return result;

    }

    /**
     * 用户身份证上传的接口
     * @param file
     * @return
     */
    @PostMapping("/uploadIdCard")
    public ApiRespResult uploadIdCard(@RequestParam("file") MultipartFile file) {
        ApiRespResult result = null;
        try {
            // 1. 获取用户的ID
            Long userId = getUserId();
            // 2.保存用户上传的文件
            String userFileId = stockUserFileService.uploadUserIdCard(userId, file);
            result = ApiRespResult.success(userFileId);
        }catch (ComponentException e) {
            log.error(e.getMessage(),e);
            result = ApiRespResult.error(e.getErrorCodeEnum());
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            result = ApiRespResult.sysError(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/downloadFile", method = {RequestMethod.GET, RequestMethod.POST} )
    public  ApiRespResult downloadFile(@NotBlank(message = "文件ID不能为空！") String fileId) throws Exception {

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();

        // 获取用户文件
        TradeUserFile tradeUserFile = stockUserFileService.getTradeUserFile(fileId);
        if (null == tradeUserFile) {
            return ApiRespResult.error(ApplicationErrorCodeEnum.USER_FILE_NOT_FOUND);
        }
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            // 获取文件流
            InputStream inputStream = cephSwiftOperator.retrieveObject(fileId);
            if(null == tradeUserFile.getFileType()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
            }
            // 设置文件响应类型
            response.setContentType(tradeUserFile.getFileType());
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + tradeUserFile.getFilename());
            // 流式缓冲下载处理
            byte[] buffer = new byte[1024];
            bis = new BufferedInputStream(inputStream);
            os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            os.flush();
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (bis != null) {
                try {
                    // 关闭输入流
                    bis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return ApiRespResult.sysError("下载失败！");
    }
}
