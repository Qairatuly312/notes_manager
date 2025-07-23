package spring.notes;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {

    /* for checking of validity of JWT token using the /user site */

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    @GetMapping("/user")
    public String userAccess() {
        return "User Content.";
    }
}
