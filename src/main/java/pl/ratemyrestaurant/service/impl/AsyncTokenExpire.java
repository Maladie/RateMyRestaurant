package pl.ratemyrestaurant.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.repository.UserTokenRepository;
import pl.ratemyrestaurant.type.TokenStatus;
import pl.ratemyrestaurant.utils.CacheUtil;
import pl.ratemyrestaurant.utils.CheckingUtils;

@Async
public class AsyncTokenExpire {
    private static Logger logger = LogManager.getLogger(AsyncTokenExpire.class);
    @Autowired
    private static UserTokenRepository userTokenRepository;

    /**
     * Async expired token removal (cache + DB) if exist
     * @param expiredToken
     */
    public static void expireToken(String expiredToken){
        logger.info("Token Expire Message Received ---> " + expiredToken);
        User user = (User) CacheUtil.getFromCache(expiredToken);
        if(!CheckingUtils.isNullObject(user)){
            CacheUtil.removeFromCache(expiredToken);
        }

        if(!CheckingUtils.isNullOrEmpty(expiredToken))
            if (user != null) {
                userTokenRepository.updateUserTokenStatus(expiredToken, TokenStatus.EXPIRED_TIME, user.getId());
            }

        logger.info(expiredToken + " is expired.");
    }
}
