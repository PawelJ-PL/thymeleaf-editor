package info.ns01.thymeleaf_editor.services.impl;

import info.ns01.thymeleaf_editor.services.TemplateEnginesRegistry;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemplateEnginesRegistryImpl implements TemplateEnginesRegistry {
    
    private ConcurrentHashMap<String, TemplateEngine> registry = new ConcurrentHashMap<>();
    
    @Override
    public TemplateEngine getTemplateEngineForMode(String mode) {
        if (!registry.containsKey(mode)) {
            registerTemplateEngineFor(mode);
        }
        return registry.get(mode);
    }
    
    @Override
    public boolean registerTemplateEngineFor(String mode) {
        if (registry.containsKey(mode)) {
            return false;
        }
        
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(new InMemoryTemplateResolver(mode));
        registry.put(mode, engine);
        return true;
    }
}
