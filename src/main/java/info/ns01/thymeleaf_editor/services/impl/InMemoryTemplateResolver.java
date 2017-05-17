package info.ns01.thymeleaf_editor.services.impl;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.AlwaysValidTemplateResolutionValidity;
import org.thymeleaf.templateresolver.ITemplateResolutionValidity;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;

import java.nio.charset.StandardCharsets;

public class InMemoryTemplateResolver implements ITemplateResolver {
    
    private static final String NAME = "MemoryTemplateResolver";
    
    private static final int ORDER = 1;
    
    private String templateMode;
    
    public String getTemplateMode() {
        return templateMode;
    }
    
    public void setTemplateMode(String templateMode) {
        this.templateMode = templateMode;
    }
    
    public InMemoryTemplateResolver(String templateMode) {
        this.templateMode = templateMode;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public Integer getOrder() {
        return ORDER;
    }
    
    @Override
    public TemplateResolution resolveTemplate(TemplateProcessingParameters templateProcessingParameters) {
        
        String templateName = templateProcessingParameters.getTemplateName();
        
        String resourceName = templateProcessingParameters.getTemplateName();
    
        IResourceResolver resourceResolver = new StringResourceResolver();
        
        String encoding = StandardCharsets.UTF_8.name();
    
        ITemplateResolutionValidity templateResolutionValidity = new AlwaysValidTemplateResolutionValidity();
        
        return new TemplateResolution(templateName, resourceName, resourceResolver, encoding, templateMode,
                                      templateResolutionValidity);
    }
    
    @Override
    public void initialize() {
    
    }
}
