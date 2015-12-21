package co.katoonyaka.web.admin.interceptors;

import co.katoonyaka.services.ConfigService;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private static final String AUTH_SESSION_KEY = "user.auth";
    private static final String CODE_PARAM = "code";
    private static final String STATE_PARAM = "state";

    @Autowired
    private ConfigService configService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (configService.isDevMode()) {
            log.info("running in dev mode");
            return true;
        }

        HttpSession session = request.getSession(true);
        Boolean authFlag = (Boolean) session.getAttribute(AUTH_SESSION_KEY);
        if (authFlag != null) {
            if (!authFlag) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }

            return true;
        }

        log.info("no auth in session");

        String code = request.getParameter(CODE_PARAM);
        String state = request.getParameter(STATE_PARAM);
        if (code == null || state == null) {
            log.info("first round, sending request to google");

            GoogleAuthHelper helper = getGoogleAuthHelper();
            session.setAttribute(STATE_PARAM, helper.getStateToken());
            response.sendRedirect(helper.buildLoginUrl());

        } else if (state.equals(session.getAttribute(STATE_PARAM))) {
            log.info("received correct state token");

            GoogleAuthHelper helper = getGoogleAuthHelper();
            String userInfoJson = helper.getUserInfoJson(code);
            String email = JsonPath.read(userInfoJson, "$.email");
            log.info("trying to login with {}", email);

            session.setAttribute(AUTH_SESSION_KEY, configService.getAdminUsers().contains(email.toLowerCase()));
            response.sendRedirect("/admin");

        } else {
            log.warn("state is not correct");
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }

        return false;
    }

    private GoogleAuthHelper getGoogleAuthHelper() {
        return new GoogleAuthHelper(
                configService.<String>getConfigValue("google.auth.clientId"),
                configService.<String>getConfigValue("google.auth.clientSecret")
        );
    }
}
