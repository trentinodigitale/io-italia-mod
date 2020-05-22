package it.tndigit.iot.web.rest.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import it.tndigit.iot.service.client.ClientHttpAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.GeneralSecurityException;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    public static String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9e8MQYGlEwgD5UeDAndo\n" +
            "VnSypBApdPzm1/h7DLy8Lx935A4yzmDgwIyGcYPdIoTsAPCQ8eDzCFyMhC1WZlRu\n" +
            "sDEy/8Ryp90LYsLhkKNw3rUbPLVvYN35SR8qxgG3Nm0+VwPLfnykKD8DG61dJKXs\n" +
            "gCyqCYR+4ZuxvAGyosHB7dZOYBnCpszo6fOwMm/94ypHhHU+FOdxOamPNp0KbgTK\n" +
            "t/+HTsJtdmhQPk5twfp9T7VCJR0r8EJEFRSQKqDqpYz32CmNxqnUsVKqpmLMkFhc\n" +
            "AIQ6uIy004BFcUbhbybL0Pbcg8L2GYSQ0ZB6tmCReFn4REiuRSKYjWu9AdNVOSch\n" +
            "pQIDAQAB\n" +
            "-----END PUBLIC KEY-----\n";


    private String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOnsiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfVVNFUiJ9XSwiZGV0YWlscyI6eyJzdWIiOiIxMDY3MTc2NjAzNjExOTc3MDEwMzMiLCJuYW1lIjoiVG9rZW4gVGVzdCIsImdpdmVuX25hbWUiOiJNaXJrbyIsImZhbWlseV9uYW1lIjoiUGlhbmV0dGkiLCJlbWFpbCI6InRlc3RAdG5kaWdpdC5pdCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJsb2NhbGUiOiJpdCIsImhkIjoidG5kaWdpdC5pdCJ9LCJhdXRoZW50aWNhdGVkIjp0cnVlLCJwcmluY2lwYWwiOiJNaXJrbyBQaWFuZXR0aSIsImNyZWRlbnRpYWxzIjoiTi9BIiwibmFtZSI6Ik1pcmtvIFBpYW5ldHRpIn0sInJvbGVzIjpbXSwiaXNFbmFibGVkIjp0cnVlLCJleHAiOjE5ODAzMjc2NjIsImlhdCI6eyJ5ZWFyIjoyMTIwLCJtb250aCI6IkpBTlVBUlkiLCJjaHJvbm9sb2d5Ijp7ImNhbGVuZGFyVHlwZSI6Imlzbzg2MDEiLCJpZCI6IklTTyJ9LCJlcmEiOiJDRSIsImRheU9mWWVhciI6MjksImRheU9mV2VlayI6IldFRE5FU0RBWSIsImxlYXBZZWFyIjp0cnVlLCJkYXlPZk1vbnRoIjoyOSwibW9udGhWYWx1ZSI6MX0sImVNYWlsIjoidGVzdEB0bmRpZ2l0Lml0In0.v3GQJSx8V4e8kJdjrYGXx8BGOURKlDUDOCoXVzGe_QtuZ4uEGuWYPYoG8J2F71hQtoCAq1gUxUZQIhwkNBnD8XDjpN2-XjmpK6wMejmA7wLUGsOKrm62gS1BpfPNInZZMrz31_Wlh55qGwvOsfHg42Xk77Maw8B9siMVu-_rmgK4i_Gavgl-XvrXH-2QpFhJVX9E_9ktjqSndoNwy-Wmah0dJBXE_vkBSjjELtENNmUCtDXi4vO8CGrtk3zvHlW4UM7XnUiP3qtPJih2YgY_9xbdA8hB0Al3cZO2KYI9xmvsOJe4mRiC5uUKRf7mz7vJSUlo_DO1mqmmDLbnyFzuSg";

    @MockBean
    ClientHttpAuthService clientHttpAuthService;

    //@Autowired
    RSA rsa;

    @BeforeEach
    public void beforeClass() throws Exception {

        rsa = new RSA();
        rsa.setClientHttpAuthService(clientHttpAuthService);
    }


    @Test
    void getPublicKeyService() {
        try {
            RSAPublicKey rsaPublicKey = rsa.getPublicKeyFromString(PUBLIC_KEY);
            Claims claims = Jwts.parser()
                    .setSigningKey(rsaPublicKey)
                    .parseClaimsJws(TOKEN)
                    .getBody();
            assertEquals(claims.get("eMail").toString(),"test@tndigit.it");

        } catch (GeneralSecurityException ex) {


        }
    }

}