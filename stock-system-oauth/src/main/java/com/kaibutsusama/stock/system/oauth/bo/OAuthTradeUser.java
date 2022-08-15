package com.kaibutsusama.stock.system.oauth.bo;

import com.kaibutsusama.stock.system.entity.user.TradeUser;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

/**
 * @author https://github.com/KaibutsuSama
 * @version 1.0
 * @date 2022/8/15 17:35
 */
public class OAuthTradeUser extends User {

    private static final long serialVersionUUID = -1L;

    /**
     * 业务用户信息
     */
    private TradeUser tradeUser;

    public OAuthTradeUser(TradeUser tradeUser) {
        // OAUTH2认证用户信息构造处理
        super(tradeUser.getUserNo(), tradeUser.getUserPwd(), (tradeUser.getStatus() == 0 ? true : false),
                true, true, (tradeUser.getStatus() == 0 ? true : false), Collections.emptyList());
        this.tradeUser = tradeUser;
    }

    public TradeUser getTradeUser() {
        return tradeUser;
    }
}
