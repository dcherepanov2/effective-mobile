package com.example.effective.mobile.sm.api.security;

import com.example.effective.mobile.sm.api.data.Role;
import com.example.effective.mobile.sm.api.data.User;
import com.example.effective.mobile.sm.api.data.UserContact;
import com.example.effective.mobile.sm.api.repo.UserContactRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    @Value("${jwt.token.expired}")
    private String timeExpired;

    private final UserContactRepo userContactRepo;

    @Autowired
    public JwtService(UserContactRepo userContactRepo) {
        this.userContactRepo = userContactRepo;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        Instant expirationTime = Instant.now().plusSeconds(Long.parseLong(timeExpired));
        Date expirationDate = Date.from(expirationTime);
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token, User user){
        final String username = extractUsername(token);
        List<UserContact> userContacts = userContactRepo.findAllByUserId(user);//TODO: костыль, доработать
        boolean validName = userContacts.stream().anyMatch(x -> x.getContact().equals(username));
        boolean isStatusEnabled = isStatusEnabledAndNotAccountNonLocked(user);
        return validName && isStatusEnabled;
    }

    private boolean isStatusEnabledAndNotAccountNonLocked(UserDetails userDetails){
        return userDetails.isEnabled() && !userDetails.isAccountNonLocked();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                   .setAllowedClockSkewSeconds(Long.parseLong(timeExpired))
                   .setSigningKey(getSignInKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName())
                ).collect(Collectors.toList());
    }
}
