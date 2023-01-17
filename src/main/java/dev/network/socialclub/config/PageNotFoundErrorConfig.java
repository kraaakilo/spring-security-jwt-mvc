package dev.network.socialclub.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@Controller
public class PageNotFoundErrorConfig implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest httpServletRequest) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int i = Integer.parseInt(status.toString());
        if (i == 404) {
            return "errors/404";
        } else if (i == 500) {
            return "errors/500";
        }
        return "errors/unknown";
    }
}
