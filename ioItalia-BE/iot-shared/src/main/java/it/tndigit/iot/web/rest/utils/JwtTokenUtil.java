//package it.tndigit.iot.web.rest.utils;
//
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtTokenUtil {
//
//    private static final long serialVersionUID = -3301605591108950415L;
//
//    static final String CLAIM_KEY_AUTHORITIES = "roles";
//    static final String CLAIM_KEY_IS_ENABLED = "isEnabled";
//    //static final String CLAIM_KEY_EMAIL = "eMail";
//    static final String CLAIM_KEY_IDENTITY = "identity";
//
//    @Autowired
//    protected RSA rsa;
//
//    public String getUsernameFromToken(String token) {
//        String username = "";
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            if (claims!=null){
//                username = claims.get(CLAIM_KEY_IDENTITY).toString();
//            }
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
//    }
//
//    public JwtUser getUserDetails(String token){
//
//        if(token == null){
//            return null;
//        }
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            if (claims==null){
//                return null;
//            }
//
//            return new JwtUser(
//                    claims.get(CLAIM_KEY_IDENTITY).toString(),
//                    "",
//                    null,
//                    (Boolean) claims.get(CLAIM_KEY_IS_ENABLED)
//            );
//        }catch (ExpiredJwtException eJE){
//            throw eJE;
//        }
//        catch (Exception e) {
//            return null;
//        }
//
//    }
//
//
//    public Date getExpirationDateFromToken(String token) {
//        Date expiration = null;
//        try {
//            final Claims claims = getClaimsFromToken(token);
//            if (claims!=null){
//                expiration = claims.getExpiration();
//            }
//
//        } catch (Exception e) {
//            expiration = null;
//        }
//        return expiration;
//    }
//
//
//    private Claims getClaimsFromToken(String token){
//        Claims claims;
//        try {
//            String publicKey= rsa.getPublicKeyService();
//
//            claims = Jwts.parser()
//                    .setSigningKey(rsa.getPublicKeyFromString(publicKey))
//                    .parseClaimsJws(token)
//                    .getBody();
//        }catch (ExpiredJwtException eJE){
//            throw eJE;
//        }
//        catch (Exception e) {
//            claims = null;
//
//        }
//        return claims;
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        JwtUser user = (JwtUser) userDetails;
//        final String username = getUsernameFromToken(token);
//        return (
//                username.equals(user.getUsername())
//                        && !isTokenExpired(token));
//    }
//}