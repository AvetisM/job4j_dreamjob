package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private static final Set<String> SET_OF_URI = new HashSet();

    public AuthFilter() {
        SET_OF_URI.add("loginPage");
        SET_OF_URI.add("login");
        SET_OF_URI.add("formAddUser");
        SET_OF_URI.add("registration");
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (setContainsUri(req.getRequestURI())) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean setContainsUri(String uri) {
        return SET_OF_URI.stream().anyMatch(currentUri -> uri.endsWith(currentUri));
    }
}