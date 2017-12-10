package example.springjwt.configurations.security.jwt;

import example.springjwt.configurations.security.jwt.models.JwtServerResponseInfo;
import example.springjwt.configurations.security.jwt.models.JwtUserSubmitInfo;
import example.springjwt.configurations.security.jwt.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<JwtServerResponseInfo> createAuthenticationToken(
            @RequestBody JwtUserSubmitInfo userSubmitInfo) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userSubmitInfo.getUsername(),
                        userSubmitInfo.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(userSubmitInfo.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtServerResponseInfo(token));
    }

}
