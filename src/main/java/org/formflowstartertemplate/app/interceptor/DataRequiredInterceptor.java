package org.formflowstartertemplate.app.interceptor;

import formflow.library.data.SubmissionRepositoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class DataRequiredInterceptor implements HandlerInterceptor {
    public static final String PATH_FORMAT = "/flow/{flow}/{screen}";
    //Required Data in this case is the session
    private final SubmissionRepositoryService submissionRepositoryService;
    public DataRequiredInterceptor(SubmissionRepositoryService submissionRepositoryService){
        this.submissionRepositoryService = submissionRepositoryService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            var parsedUrl = new AntPathMatcher().extractUriTemplateVariables(PATH_FORMAT, request.getRequestURI());
            String redirect_url = "/";
            var screen = parsedUrl.get("screen");
            var flow = parsedUrl.get("flow");
            var session = request.getSession(false);
            // TODO may have to figure out if we are on the first page in the flow, as we don't have a session yet?
            if (session == null && flow.equals("ubi") && screen.equals("howThisWorks")) {
                return true;
            }
            else{
                if (session == null && flow.equals("ubi")){
                    response.sendRedirect(redirect_url);
                    return false;
                }else{
                    return true;
                }
            }
        } catch (IllegalStateException e) {
            return true;
        }
    }
}
