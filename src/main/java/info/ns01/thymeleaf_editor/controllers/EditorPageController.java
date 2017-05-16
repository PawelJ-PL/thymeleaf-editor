package info.ns01.thymeleaf_editor.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editor")
public class EditorPageController {
    
    @RequestMapping("")
    public String editorForm() {
        System.out.println("XXXXXXXXXXXXXXX" + 10/0);
        return "base";
    }
    
}
