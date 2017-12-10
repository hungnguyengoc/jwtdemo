package example.springjwt.configurations.security.jwt;

import example.springjwt.configurations.security.jwt.utils.JwtConstants;
import example.springjwt.configurations.security.jwt.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtils tokenUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain
            filterChain) throws ServletException, IOException {
        final String requestHeader = request.getHeader(JwtConstants.TOKEN_HEADER);

        String username = null;
        String authToken = null;

        if (requestHeader != null && requestHeader.startsWith(JwtConstants.TOKEN_PREFIX)) {
            authToken = requestHeader.substring(JwtConstants.TOKEN_PREFIX.length());
            try {
                username = tokenUtils.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            }
        } else {
            logger.warn("couldn't find header string, will ignore the header");
        }

        logger.info("checking authentication for user " + username);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (tokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
