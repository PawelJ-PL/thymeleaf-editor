package info.ns01.thymeleaf_editor.services;

import info.ns01.thymeleaf_editor.services.impl.TemplateEnginesRegistryImpl;
import org.junit.Before;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateEnginesRegistryTest {
    
    private TemplateEnginesRegistry templateEnginesRegistry;
    
    @Before
    public void setUp() {
        templateEnginesRegistry = new TemplateEnginesRegistryImpl();
    }
    
    @Test
    public void shouldReturnTrueWhenCreatedEntry() {
        //given
        templateEnginesRegistry.registerTemplateEngineFor("XML");
        
        //when
        boolean result = templateEnginesRegistry.registerTemplateEngineFor("HTML5");
        
        //then
        assertThat(result).isTrue();
    }
    
    @Test
    public void shouldReturnTrueWhenEntryAlreadyExists() {
        //given
        templateEnginesRegistry.registerTemplateEngineFor("HTML5");
        
        //when
        boolean result = templateEnginesRegistry.registerTemplateEngineFor("HTML5");
        
        //then
        assertThat(result).isFalse();
    }
    
    @Test
    public void shouldReturnNewInstanceWhenRequestedFirstTime() {
        //when
        TemplateEngine templateEngine = templateEnginesRegistry.getTemplateEngineForMode("HTML5");
        
        //then
        assertThat(templateEngine).isInstanceOf(TemplateEngine.class);
    }
    
    @Test
    public void shouldReturnExistingInstance() {
        //given
        templateEnginesRegistry.registerTemplateEngineFor("HTML5");
        TemplateEngine firstInstance = templateEnginesRegistry.getTemplateEngineForMode("HTML5");
        
        
        //when
        TemplateEngine templateEngine = templateEnginesRegistry.getTemplateEngineForMode("HTML5");
        
        //then
        assertThat(templateEngine==firstInstance).isTrue();
    }
    
}
