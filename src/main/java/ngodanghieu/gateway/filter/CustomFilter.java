package ngodanghieu.gateway.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ngodanghieu.gateway.model.response.ResponseConstant;
import ngodanghieu.gateway.model.response.ResponseDTO;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@Configuration
public class CustomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response  = (HttpServletResponse) servletResponse;
        String token = httpRequest.getHeader(FilterConstant.HEADER_AUTHENTICATION);
        ApiVerifyToken apiVerifyToken = new ApiVerifyToken(token);
        Map<String, Object> result = apiVerifyToken.verifyToken();

        if (result != null){
            httpRequest.setAttribute("user_name",result.get("user_name"));
            filterChain.doFilter(httpRequest,servletResponse);
        }else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getOutputStream().write(restResponseBytes(ResponseConstant.ERROR_USER_NOT_ROLE));
            return;
        }
    }

    @Override
    public void destroy() {

    }

    private byte[] restResponseBytes(ResponseDTO responseDto) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(responseDto);
        return serialized.getBytes();
    }

}
