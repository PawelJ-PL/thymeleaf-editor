package info.ns01.thymeleaf_editor.utils;

import org.thymeleaf.TemplateEngine;

public class Utils {
    
    public static String getThymeleafVersion() {
        return TemplateEngine.class.getPackage().getSpecificationVersion();
    }
    
}
