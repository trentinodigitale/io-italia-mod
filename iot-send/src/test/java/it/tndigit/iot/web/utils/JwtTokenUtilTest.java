//package it.tndigit.iot.web.utils;
//
//import it.tndigit.iot.common.UtilityDate;
//import it.tndigit.iot.repository.ServizioRepository;
//import it.tndigit.iot.it.tndigit.iot.service.client.ClientHttpAuthService;
//import it.tndigit.iot.web.rest.utils.JwtTokenUtil;
//import it.tndigit.iot.web.rest.utils.JwtUser;
//import it.tndigit.iot.web.rest.utils.RSA;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.time.LocalDate;
//import java.util.Date;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//class JwtTokenUtilTest {
//
//    private String TOKEN = "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJpb0FwcCIsImlkZW50aXR5IjoiQUFBQkJCQ0NDIiwicm9sZXMiOltdLCJpc0VuYWJsZWQiOnRydWUsImV4cCI6NDc0NDMwMzIwMCwiaWF0Ijp7InllYXIiOjIwMjAsIm1vbnRoIjoiTUFZIiwiY2hyb25vbG9neSI6eyJjYWxlbmRhclR5cGUiOiJpc284NjAxIiwiaWQiOiJJU08ifSwiZXJhIjoiQ0UiLCJkYXlPZlllYXIiOjEyNiwiZGF5T2ZXZWVrIjoiVFVFU0RBWSIsImxlYXBZZWFyIjp0cnVlLCJkYXlPZk1vbnRoIjo1LCJtb250aFZhbHVlIjo1fSwiZU1haWwiOiJ0ZXN0QHRuZGlnaXQuaXQifQ.I1nLLw9R0H5ERsiPGmnpCDC_LfdwmtLhqSGFEKVWk5xllGsLByulM5Vt3ZeBc6MGJL1zT5KpgFbapv1FsLPkDYtR3CKZeLDjKdFj79jyKgIORIAqwiGVOQV0rhzn9upwveXs8NcMsj-am6PmWEIHBdmCtFVlANck-NWQWcY_bC_BMTaMGtc4rUF_KyXNVUaJdTa-xLuOzoGLhOp0f05ULPJYufQ1f0SIyyOVxOwaV-7N36zus1M5WNV9yyorgxu63PEbInKqYngOsh7SWM63TqTpV2V1yj25IiWkRRhFLdmQ8JQ3r639sn9FLFnx2gcXJ9cOv8IVoHX0wytxN0itfQ";
//    private final String EMAIL = "test@tndigit.it";
//    private final String CODE = "AAABBBCCC";
//
//
//    public static String PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
//            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA9e8MQYGlEwgD5UeDAndo\n" +
//            "VnSypBApdPzm1/h7DLy8Lx935A4yzmDgwIyGcYPdIoTsAPCQ8eDzCFyMhC1WZlRu\n" +
//            "sDEy/8Ryp90LYsLhkKNw3rUbPLVvYN35SR8qxgG3Nm0+VwPLfnykKD8DG61dJKXs\n" +
//            "gCyqCYR+4ZuxvAGyosHB7dZOYBnCpszo6fOwMm/94ypHhHU+FOdxOamPNp0KbgTK\n" +
//            "t/+HTsJtdmhQPk5twfp9T7VCJR0r8EJEFRSQKqDqpYz32CmNxqnUsVKqpmLMkFhc\n" +
//            "AIQ6uIy004BFcUbhbybL0Pbcg8L2GYSQ0ZB6tmCReFn4REiuRSKYjWu9AdNVOSch\n" +
//            "pQIDAQAB\n" +
//            "-----END PUBLIC KEY-----\n";
//
//
//
//    @Autowired
//    RSA rsa;
//
//    @Autowired
//    JwtTokenUtil jwtTokenUtil;
//
//
//    @Autowired
//    ServizioRepository servizioRepository;
//
//    @MockBean
//    ClientHttpAuthService clientHttpAuthService;
//
//
//    @BeforeEach
//    void init(){
//        Mockito.when(clientHttpAuthService.getPublicKey()).thenReturn(Optional.of(PUBLIC_KEY));
//    }
//
//    @Test
//    void getUsernameFromToken() {
//        String username = jwtTokenUtil.getUsernameFromToken(TOKEN);
//        assertEquals(username,CODE);
//    }
//
//    @Test
//    void getUserDetails() {
//        JwtUser jwtUser = jwtTokenUtil.getUserDetails(TOKEN);
//        assertEquals(jwtUser.getUsername(),CODE);
//    }
//
//    @Test
//    void getExpirationDateFromToken() {
//        Date date = jwtTokenUtil.getExpirationDateFromToken(TOKEN);
//        LocalDate localDate = UtilityDate.localDateOf(date);
//        LocalDate localDateCalc = LocalDate.of(2120,5,5);
//        Assertions.assertTrue(localDate.compareTo(localDateCalc)==0,"Expired date NON corretta");
//    }
//
//}