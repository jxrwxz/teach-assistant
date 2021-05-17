package jxrwxz.teachassistant;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //System.out.println("preHandle");
        String uri = request.getRequestURI();
        if (uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("jpg")||uri.endsWith("svg")){
            return true ;
        }

        HttpSession session = request.getSession();
        if ("authorized"!=session.getAttribute("token")) {  //校验登录标记
            request.getRequestDispatcher("/login").forward(request, response);  //对于未登录的用户跳转到登录页面
            return false;
        }
        return true;
    }
}
