package it.tndigit.iot.web.filter;

import io.jsonwebtoken.ExpiredJwtException;
import it.tndigit.auth.web.controller.GestioneAuthApi;
import it.tndigit.iot.domain.ServizioPO;
import it.tndigit.iot.repository.ServizioRepository;
import it.tndigit.iot.web.rest.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JwtAuthenticationTokenBeforeFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer";

    @Value("${jwt.header}")
    protected String tokenHeader;

    @Autowired
    protected GestioneAuthApi gestioneAuthApi;

    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    @Autowired
    protected ServizioRepository servizioRepository;


    @Value("${server.origin.auth}")
    String basePathAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {


        try{

            if (request.getRequestURI().contains("/api/v1/") && !request.getRequestURI().contains("servizio")){
                Optional< String > authToken = Optional.ofNullable(request.getHeader(this.tokenHeader))
                        .map(v -> v.replace(BEARER, "").trim())
                        .map(v -> v.replace("%20", "").trim())
                        .map(v -> v.replace("%2520", "").trim());

                if (authToken.isPresent()) {
                    UserDetails userDetails = jwtTokenUtil.getUserDetails(authToken.get());
                    if (jwtTokenUtil.validateToken(authToken.get(), userDetails)) {
                        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                        Optional< ServizioPO > servizioPOOptional = servizioRepository.findAllByCodiceIdentificativo(userDetails.getUsername());

                        if (servizioPOOptional.isPresent()){
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }else {
                        throw new InvalidTokenException("TOKEN NON VALIDO");
                    }
                } else {
                    throw new InvalidTokenException("TOKEN NON PRESENTE");
                }

            }

            chain.doFilter(request, response);


        }catch (ExpiredJwtException eJE){

            final String expiredMsg = eJE.getMessage();
            logger.warn(expiredMsg);

            final String msg = (expiredMsg != null) ? expiredMsg : "Unauthorized";
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, msg);

        }catch (InvalidTokenException iTe){
            response.setStatus(499);
            //chain.doFilter(request, response);
        }


    }
}