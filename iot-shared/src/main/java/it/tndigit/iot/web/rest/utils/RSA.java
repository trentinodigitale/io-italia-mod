//package it.tndigit.iot.web.rest.utils;
//
//import it.tndigit.iot.it.tndigit.iot.service.client.ClientHttpAuthService;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//
//import java.security.GeneralSecurityException;
//import java.security.KeyFactory;
//import java.security.interfaces.RSAPublicKey;
//import java.security.spec.X509EncodedKeySpec;
//
//import java.util.Optional;
//
//
//@Service
//public class RSA {
//
//    @Autowired
//    private ClientHttpAuthService clientHttpAuthService;
//
//
//    @Cacheable(value = "authPublicKey")
//    public String getPublicKeyService() {
//        Optional< String > publicKey=  clientHttpAuthService.getPublicKey();
//        if (publicKey.isPresent()) {
//            return publicKey.get();
//        } else {
//            return null;
//        }
//    }
//
//
//    public RSAPublicKey getPublicKeyFromString(String key) throws GeneralSecurityException {
//        String publicKeyPEM = key;
//        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
//        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
//        byte[] encoded = Base64.decodeBase64(publicKeyPEM);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
//    }
//
//    protected void setClientHttpAuthService(ClientHttpAuthService clientHttpAuthService) {
//        this.clientHttpAuthService = clientHttpAuthService;
//    }
//
//
//}
