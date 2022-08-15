package com.kaibutsusama.stock.system.oauth.config;

import com.kaibutsusama.stock.system.common.encrypt.EncryptUtil;
import com.kaibutsusama.stock.system.common.exception.ComponentException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:36
 */
@Service
@Log4j2
public class StockPasswordEncoder implements PasswordEncoder {

    /**
     * 编码处理
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }


    /**
     * 密码校验判断
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if(rawPassword != null && rawPassword.length() > 0){
            try {
                // 这里通过MD5及B64加密
                String password = EncryptUtil.encryptSigned(rawPassword.toString());
                boolean isMatch= encodedPassword.equals(password);
                if(!isMatch) {
                    log.warn("password 不一致！");
                }
                return isMatch;
            } catch (ComponentException e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

}
