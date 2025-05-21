package co.edu.usc.TeacherGo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index"; // Thymeleaf buscará templates/index.html
    }
}
