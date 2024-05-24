package co.com.bancolombia.jwt;

import co.com.bancolombia.model.profile.exception.BusinessException;
import co.com.bancolombia.model.profile.exception.message.ErrorMessage;
import co.com.bancolombia.model.profile.gateways.LoginGateway;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import javax.crypto.SecretKey;
import java.util.logging.Logger;

@Component
public class JwtProvider implements LoginGateway {
    private static final Logger LOGGER = Logger.getLogger(JwtProvider.class.getName());
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Mono<String> validateToken(String token) {
        return Mono.just(validate(token));
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
