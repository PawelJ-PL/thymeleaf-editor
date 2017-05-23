package info.ns01.thymeleaf_editor.utils;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.ITemplateModeHandler;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;

import java.util.Set;
import java.util.stream.Collectors;

public class Utils {
    
    public static String getThymeleafVersion() {
        return TemplateEngine.class.getPackage().getSpecificationVersion();
    }

    public static Set<String> getStandardTemplateModesNames() {
        return StandardTemplateModeHandlers.ALL_TEMPLATE_MODE_HANDLERS
                .stream()
                .map(ITemplateModeHandler::getTemplateModeName)
                .collect(Collectors.toSet());
    }
    
}
