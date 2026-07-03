package com.dietplanner.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Cek sederhana: kalau session tidak punya "userId", lempar ke /login.
 * Halaman /login, /register, dan aset statis dikecualikan lewat WebConfig.
 */
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("userId") != null) {
            return true;
        }

        response.sendRedirect("/login");
        return false;
    }
}
