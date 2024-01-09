package org.taerock.apiserver.security.filter;

import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.filter.OncePerRequestFilter;
import org.taerock.apiserver.util.JWTUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
// 모든 경우에 체크 필터를 동작하게 하는 filter -> OncePerRequestFilter
public class JWTCheckFilter extends OncePerRequestFilter {

    // 특정 경로 필터 미사용
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // true == not check

        String path = request.getRequestURI();

        log.info("check uri--------------" + path);

        // false == check
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("---------------------------");

        log.info("---------------------------");

        log.info("---------------------------");

       /*
       String authHeaderStr = request.getHeader("Authorization");

        // Bearer 7문자 // JWT 문자열

        String accessToken = authHeaderStr.substring(7);
        Map<String, Object> claims = JWTUtil.validateToken(accessToken);

        */

        log.info("-----------------JWTCheckFilter.................");
        String authHeaderStr = request.getHeader("Authorization");
        // 예외 처리 진행
        try {
            //Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);
            log.info("JWT claims: " + claims);
            filterChain.doFilter(request, response);
        }catch(Exception e){
            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }

        filterChain.doFilter(request, response);

    }

}
