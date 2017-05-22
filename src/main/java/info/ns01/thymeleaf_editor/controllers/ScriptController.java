package info.ns01.thymeleaf_editor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/scripts")
public class ScriptController {
    
    @RequestMapping(value = "/render.js", method = RequestMethod.GET)
    public String renderPageScript() {
        
        return "script/js/render";
        
    }
    
}
