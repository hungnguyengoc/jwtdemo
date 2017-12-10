package example.springjwt.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/protect", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> protectedAPI() {
        return new ResponseEntity<String>("hl", HttpStatus.OK);
    }
}
