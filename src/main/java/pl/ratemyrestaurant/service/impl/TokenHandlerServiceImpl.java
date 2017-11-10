package pl.ratemyrestaurant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ratemyrestaurant.domain.UserToken;
import pl.ratemyrestaurant.exception.TokenException;
import pl.ratemyrestaurant.model.User;
import pl.ratemyrestaurant.repository.UserTokenRepository;
import pl.ratemyrestaurant.service.TokenHandlerService;
import pl.ratemyrestaurant.type.TokenStatus;
import pl.ratemyrestaurant.utils.CacheUtil;

import java.util.UUID;

@Service
@Transactional
public class TokenHandlerServiceImpl implements TokenHandlerService {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Override
    public User parseUserFromToken(String token) throws TokenException {
        Object user = CacheUtil.getFromCache(token);

        if(user == null){
            AsyncTokenExpire.expireToken(token);
            throw new TokenException(token, "TOKEN_NOT_FOUND_IN_CACHE", null);
        }
        else {
            UserToken userToken = userTokenRepository.getByToken(token);

            if(userToken ==null) {
                throw new TokenException(token, "TOKEN_USER_MISMATCH", null);
            } else if (userToken.getStatus() == TokenStatus.ACTIVE.getTokenStatus()) {
                return (User) user;
            } else {
                throw new TokenException(token, "TOKEN_IN_NOT_ACTIVE", null);
            }
        }
    }

    @Override
    public String calculateTokenForUser(User user) {
        return UUID.randomUUID().toString();
    }

    @Override
    public void insertToCache(String token, User user) {
        CacheUtil.addToCache(token, user);
    }
}
