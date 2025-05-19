package traineeship_app.config;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import traineeship_app.domainmodel.User;

/*
 * Determines the url that is appropriate for the logged user based on his role
 */
@Configuration
public class CustomSecuritySuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	 	@Override
	    protected void handle(
	    		HttpServletRequest request, 
	    		HttpServletResponse response, 
	    		Authentication authentication)
	    throws java.io.IOException {
	        String targetUrl = determineTargetUrl(authentication);
	        if(response.isCommitted()) return;
	        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	        redirectStrategy.sendRedirect(request, response, targetUrl);
	    }

	    protected String determineTargetUrl(Authentication authentication){
	        String url = "/auth/login?error=true";
	        
	        User user = (User) authentication.getPrincipal(); // το User implements UserDetails

	        // Αν δεν έχει συμπληρώσει προφίλ ➜ redirect σε /details
	        if (!user.isProfileCompleted()) {
	            return switch (user.getRole()) {
	                case USER_ST-> "/student/studentdetails?username=" + user.getUsername();
	                case USER_CO -> "/company/companydetails?username=" + user.getUsername();
	                case USER_PR -> "/professor/professordetails?username=" + user.getUsername();
	                default -> url;
	            };
	        }
     
	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        List<String> roles = new ArrayList<String>();
	        
	        for(GrantedAuthority a : authorities){
	            roles.add(a.getAuthority());
	        }
	        
	      
	        if(roles.contains("USER_ST")) {
	            url = "/student/dashboard"; // ZAS added /user/ here
	        }else if(roles.contains("USER_CO")) {
	            url = "/company/dashboard"; // ZAS added /user/ here
	        }else if(roles.contains("USER_PR")) {
	            url = "/professor/dashboard"; // ZAS added /user/ here
	        }else if(roles.contains("USER_COM")) {
	            url = "/committee/dashboard"; // ZAS added /user/ here
	        }
	        
	        return url;
	    }
}
