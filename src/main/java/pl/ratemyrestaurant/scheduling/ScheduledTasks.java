package pl.ratemyrestaurant.scheduling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.ratemyrestaurant.repository.UserTokenRepository;
import pl.ratemyrestaurant.type.TokenStatus;


@Component
public class ScheduledTasks {
    private static Logger logger = LogManager.getLogger(ScheduledTasks.class);

    private UserTokenRepository repository;

    @Autowired
    public ScheduledTasks(UserTokenRepository userTokenRepository){
        this.repository = userTokenRepository;
    }

    /**
     * Remove expired tokens in 1 minute intervals
     */
    @Scheduled(initialDelay = 10000, fixedDelay = 60000) // 1 minute intervals
    public void removeExpiredTokens(){
        logger.debug("Removing expired tokens from db");
        try {
            repository.deleteAllByStatus(TokenStatus.EXPIRED_TIME.getTokenStatus());
            logger.debug("Expired tokens removal success");
        } catch (Exception e){
            logger.debug("Exception thrown while deleting expired tokens from db");
            logger.catching(e);
        }
    }

    //TODO switch to token remove action if token expired (time diff between create and Constant Timeout is higher than Constants.Max_Token_Expiration_Time)
    //TODO implement token refresh endpoint to client near-expired token could be refreshed (updated creation date/fresh token generation)
//    @Scheduled(fixedRate = Constants.TOKEN_EXPIRE_CHECKER_FIXED_RATE, initialDelay = 10000) // every 30s check if
//    public void expireTokens() throws InterruptedException {
//
//
//        List<Integer> userIdList = repository.getUserIdByTokenStatus(TokenStatus.ACTIVE.getTokenStatus());
//        LocalDateTime expiationDate = LocalDateTime.now().minus(Constants.CACHE_TIMEOUT_IN_SEC, ChronoUnit.SECONDS);
//        for (Integer uid : userIdList) {
//            List<UserToken> userToken = repository.getTokenToExpire(uid, Timestamp.valueOf(expiationDate));
//            for (UserToken token : userToken) {
//                try {
//                    repository.delete(token);
//                    logger.debug("Removing expired token: "+token + " of user with id: "+ uid);
//                } catch (Exception e) {
//                    logger.error("Error when removing expire queue.", e);
//                }
//            }
//        }
//    }
}
