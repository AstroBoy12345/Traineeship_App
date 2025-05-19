package traineeship_app.controllers;



import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
public class HomepageController {

    @RequestMapping("/")
    public String homepage(){
        return "/homepage";
    }
}
