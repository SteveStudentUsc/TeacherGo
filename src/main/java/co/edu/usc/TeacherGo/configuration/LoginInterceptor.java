package co.edu.usc.TeacherGo.configuration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // Debug para hacer pruebas:
        System.out.println("Interceptando URI: " + uri);

        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            // Redirige solo si no es una página pública
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}
