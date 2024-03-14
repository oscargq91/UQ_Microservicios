package co.com.bancolombia.jwt;

import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.exception.BusinessException;
import co.com.bancolombia.model.user.exception.message.ErrorMessage;
import co.com.bancolombia.model.user.gateways.LoginGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtProvider implements LoginGateway {
    private static final Logger LOGGER = Logger.getLogger(JwtProvider.class.getName());

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expiration;
    @Value("${jwt.issuer}")
    private String issuer;

    @Override
    public Mono<String> getTokenJwt(User user) {
        return Mono.just(getUserDetails(user))
                .map(this::generateToken);
    }

    @Override
    public Mono<String> validateToken(String token) {
        return Mono.just(validate(token));
    }

    public UserDetails getUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(issuer)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .signWith(getKey(secret))
                .compact();
    }

    public String validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return getUsernameFromToken(token);
        } catch (ExpiredJwtException e) {
            LOGGER.severe("token expired");
            throw new BusinessException(ErrorMessage.TOKEN_VENCIDO);
        } catch (UnsupportedJwtException e) {
            LOGGER.severe("token unsupported");
            throw new BusinessException(ErrorMessage.TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException | SignatureException e) {
            LOGGER.severe("token malformed");
            throw new BusinessException(ErrorMessage.TOKEN_MALFORMED);
        } catch (IllegalArgumentException e) {
            LOGGER.severe("illegal args");
            throw new BusinessException(ErrorMessage.TOKEN_ILLEGAL_ARGS);

        }

    }

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            LOGGER.severe("Token expired");
            throw new BusinessException(ErrorMessage.TOKEN_VENCIDO);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            LOGGER.severe("Invalid token");
            throw new BusinessException(ErrorMessage.TOKEN_VALID_ERROR);
        }
    }

}
