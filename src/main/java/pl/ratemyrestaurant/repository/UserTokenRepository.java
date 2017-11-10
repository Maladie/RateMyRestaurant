package pl.ratemyrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.ratemyrestaurant.domain.UserToken;
import pl.ratemyrestaurant.type.TokenStatus;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    @Modifying
    @Query("update UserToken u set u.token = ?1, u.status = ?2 where u.id = ?3")
    Integer updateUserTokenStatus(String token, TokenStatus status, Integer id);

    UserToken getByToken(String token);

    @Query("select u from UserToken u where u.id = ?1 and u.status = 0 and u.createdDate < ?2")
    List<UserToken> getTokenToExpire(Integer id, Timestamp expirationDate);

    @Query("select u.id from UserToken u where u.status = ?1")
    List<Integer> getUserIdByTokenStatus(long switchStatus);

    @Modifying
    @Query("update UserToken u set u.status = 1 where u.id = ?1")
    int deactivateAllTokensByUser(int userId);

    void deleteAllByStatus(Long status);
}
