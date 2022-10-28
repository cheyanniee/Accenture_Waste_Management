package com.backend.configuration;

import com.backend.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    PeopleService peopleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        String currentURL = String.valueOf(request.getRequestURL());

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        if (currentURL.endsWith("people/login") || currentURL.endsWith("people/register")) {
            return true;
        }

        String token = request.getHeader("token");
//        String userId = request.getHeader("userId");

        if (token == null || token.isBlank()) {
            throw new CustomException("No token");
        }

        try {
            Long peopleId = peopleService.getIdByToken(token);
            return peopleService.validateToken(token, peopleId);
        } catch (NumberFormatException e) {
            throw new CustomException("Wrong People Id format");
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
