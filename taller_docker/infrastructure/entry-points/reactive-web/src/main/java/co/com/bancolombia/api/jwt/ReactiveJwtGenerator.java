package co.com.bancolombia.api.jwt;

import co.com.bancolombia.api.dto.ResponseJwtDTO;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;

import java.util.Date;

@UtilityClass
public class ReactiveJwtGenerator {

    public static Mono<ResponseJwtDTO> getResponseJwtDTO(String username) {
        return generateJwt(username)
                .map(jwt -> ResponseJwtDTO.builder().jwt(jwt).build());
    }

    public static Mono<String> generateJwt(String username) {

        String base64Key = "0rXy9Cf1a70yyPs4ryF7A8y3pMFMq5sRjsekQ546Bnw=";

        System.out.println("Secret Key (Base64):-" + base64Key);
        return Mono.just(Jwts.builder()
                .setSubject(username)
                .setIssuer("ingesis.uniquindio.edu.co")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS256, base64Key)
                .compact());

    }

    public static Mono<String> isTokenValid(String token, String username) {
        String secretKey = "0rXy9Cf1a70yyPs4ryF7A8y3pMFMq5sRjsekQ546Bnw=";
        Date currentDate = new Date();

        return Mono.just(token)
                .flatMap(t -> {

                    try {
                        Claims claims = Jwts.parser()
                                .setSigningKey(secretKey)
                                .build()
                                .parseClaimsJws(token)
                                .getBody();

                        return Mono.just(claims);
                    } catch (SignatureException e) {
                        return Mono.error(new BusinessException(BusinessErrorMessage.TOKEN_INVALIDO));
                    }
                })
                .filter(claims -> !claims.getExpiration().before(currentDate))
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.TOKEN_VENCIDO)))
                .filter(claims -> claims.getIssuer().equals("ingesis.uniquindio.edu.co") && claims.getSubject().equals(username))
                .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.TOKEN_USERNAME_INVALID)))
                .then(Mono.just(username))
                .onErrorResume(SignatureException.class, e -> Mono.error(new BusinessException(BusinessErrorMessage.TOKEN_INVALIDO)));


    }
}
