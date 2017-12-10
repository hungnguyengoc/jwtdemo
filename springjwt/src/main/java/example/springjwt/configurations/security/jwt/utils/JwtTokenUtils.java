package example.springjwt.configurations.security.jwt.utils;

import example.springjwt.configurations.security.jwt.models.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtils {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public boolean validateToken(String authToken, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(authToken);
        final Date created = getIssuedAtDateFromToken(authToken);
        return (username.equals(user.getUsername()) && !isTokenExpired(authToken));
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String authToken) {
        return getClaimFromToken(authToken, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(JwtConstants.SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, userDetails.getUsername(), "");
    }

    private String generateToken(Map<String, Object> claims, String subject, String audience) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        LOGGER.info("doGenerateToken -- " + createdDate);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setAudience(audience)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET_KEY)
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + JwtConstants.TOKEN_EXPIRATION * 1000);
    }
}
