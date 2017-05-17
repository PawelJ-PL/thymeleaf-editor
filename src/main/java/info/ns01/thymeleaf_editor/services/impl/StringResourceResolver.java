package info.ns01.thymeleaf_editor.services.impl;

import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringResourceResolver implements IResourceResolver {
    
    private final String NAME = "StringResourceResolver";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
        return new ByteArrayInputStream(resourceName.getBytes());
    }
}
