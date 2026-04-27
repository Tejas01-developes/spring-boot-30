package project1.project.tokens;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import org.apache.catalina.SessionIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

import java.util.Date;





@Service
public class tokens {
    @Value("${access.secret}")
    private String accesskey;

    @Value("${refresh.secret}")
    private String refreshkey;

    private SecretKey getaccesskey(){
        return Keys.hmacShaKeyFor(accesskey.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getrefreshkey(){
        return Keys.hmacShaKeyFor(refreshkey.getBytes(StandardCharsets.UTF_8));
    }



    public String accesstoken(String userid) {
        return Jwts
                .builder()
                .setSubject(userid)
                .signWith(SignatureAlgorithm.HS256, accesskey.getBytes())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 + 60 * 1000))
                .compact();
    }


    public String refreshtoken(String userid) {
        return Jwts
                .builder()
                .setSubject(userid)
                .signWith(SignatureAlgorithm.HS256, refreshkey.getBytes())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 + 60 * 1000))
                .compact();
    }


    public String decodeaccesstoken(String token) {
        return Jwts
                .parser()
                .verifyWith(getaccesskey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public String decoderefreshtoken(String token) {
        return Jwts
                .parser()
                .verifyWith(getrefreshkey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }


    public boolean verifyaccesstoken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getaccesskey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean verifyrefreshtoken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getaccesskey()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
    }

}