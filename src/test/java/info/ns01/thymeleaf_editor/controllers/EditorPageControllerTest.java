package info.ns01.thymeleaf_editor.controllers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import info.ns01.thymeleaf_editor.exceptions.InvalidModelException;
import info.ns01.thymeleaf_editor.models.FlashMessage;
import info.ns01.thymeleaf_editor.models.TemplateForm;
import info.ns01.thymeleaf_editor.models.enums.FlashMessageType;
import info.ns01.thymeleaf_editor.services.ModelService;
import info.ns01.thymeleaf_editor.services.TemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.mockito.Mockito.when;
import static info.ns01.thymeleaf_editor.utils.Constants.STANDARD_FLASH_ATTRIBUTE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EditorPageControllerTest {

    @MockBean
    private TemplateService templateService;

    @MockBean
    private ModelService modelService;

    @Autowired
    private MockMvc mockMvc;

    private final String EDITOR_URL = "/editor";

    @Test
    public void shouldGetFormPage() throws Exception {
        //when
        ResultActions result = mockMvc.perform(get(EDITOR_URL));

        //then
        result.andExpect(status().isOk());
        result.andExpect(model().attribute("extraScripts", ImmutableList.of("/static/js/render.js")));
        result.andExpect(view().name("editor/form"));
    }

    @Test
    public void shouldReturnNotFound() throws Exception {
        //when
        ResultActions result = mockMvc.perform(get("/NotExistingPage"));

        //then
        result.andExpect(status().isNotFound());
        result.andExpect(view().name("error_pages/404"));
    }

    @Test
    public void shouldPostValidDataAndRedirectToResult() throws Exception {
        //given
        String template = "valid template";
        String model = "valid model";
        String mode = "HTML5";
        String expectedResult = "expected result";
        Map<String, Object> variables = ImmutableMap.of("var1", "mockVar");
        when(modelService.extractVariables(model)).thenReturn(variables);
        when(templateService.processTemplate(template, variables, mode)).thenReturn(expectedResult);

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("mode", mode)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, mode)));
        result.andExpect(flash().attribute("result", expectedResult));
    }

    @Test
    public void shouldGenerateErrorWhenNoTemplate() throws Exception {
        //given
        String model = "valid model";
        String mode = "HTML5";
        FlashMessage m1 = new FlashMessage(FlashMessageType.WARNING, "Template may not be empty");
        FlashMessage m2 = new FlashMessage(FlashMessageType.WARNING, "Template may not be null");

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("mode", mode)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(null, model, mode)));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(null, model, mode)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1, m2);
    }

    @Test
    public void shouldGenerateErrorWhenNoMode() throws Exception {
        //given
        String template = "valid template";
        String model = "valid model";
        FlashMessage m1 = new FlashMessage(FlashMessageType.WARNING, "Mode may not be empty");
        FlashMessage m2 = new FlashMessage(FlashMessageType.WARNING, "Mode may not be null");

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, null)));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, null)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1, m2);
    }

    @Test
    public void shouldPostValidDataWithoutModelAndRedirectToResult() throws Exception {
        //given
        String template = "valid template";
        String mode = "HTML5";
        String expectedResult = "expected result";
        when(templateService.processTemplate(template, Collections.emptyMap(), mode)).thenReturn(expectedResult);

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("mode", mode));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, null, mode)));
        result.andExpect(flash().attribute("result", expectedResult));
    }

    @Test
    public void shouldGenerateErrorWhenNoModeAndTemplate() throws Exception {
        //given
        String model = "valid model";
        FlashMessage m1 = new FlashMessage(FlashMessageType.WARNING, "Mode may not be empty");
        FlashMessage m2 = new FlashMessage(FlashMessageType.WARNING, "Mode may not be null");
        FlashMessage m3 = new FlashMessage(FlashMessageType.WARNING, "Template may not be empty");
        FlashMessage m4 = new FlashMessage(FlashMessageType.WARNING, "Template may not be null");

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(null, model, null)));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(null, model, null)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1, m2, m3, m4);
    }

    @Test
    public void shouldGenerateErrorOnInvalidMode() throws Exception {
        //given
        String template = "valid template";
        String model = "valid model";
        String mode = "Invalid";
        FlashMessage m1 = new FlashMessage(FlashMessageType.WARNING, "Invalid template mode");

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("mode", mode)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, mode)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1);
    }

    @Test
    public void shouldGenerateErrorOnInvalidModel() throws Exception {
        //given
        String template = "valid template";
        String model = "valid model";
        String mode = "HTML5";
        when(modelService.extractVariables(model)).thenThrow(new InvalidModelException(model, "Invalid model"));
        FlashMessage m1 = new FlashMessage(FlashMessageType.DANGER, "Invalid model. See usage below");

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("mode", mode)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, mode)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1);
    }

    @Test
    public void shouldGenerateErrorOnInvalidTemplate() throws Exception {
        //given
        String template = "valid template";
        String model = "valid model";
        String mode = "HTML5";
        Map<String, Object> variables = ImmutableMap.of("var1", "mockVar");
        when(modelService.extractVariables(model)).thenReturn(variables);
        FlashMessage m1 = new FlashMessage(FlashMessageType.DANGER, "Invalid template; Bad template");
        Throwable templateException = new TemplateInputException("Invalid template");
        //noinspection UnnecessaryInitCause
        templateException.initCause(new RuntimeException("Bad template"));
        when(templateService.processTemplate(template, variables, mode)).thenThrow(templateException);

        //when
        ResultActions result = mockMvc.perform(post(EDITOR_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("template", template)
                .param("mode", mode)
                .param("model", model));

        //then
        result.andExpect(status().isFound());
        result.andExpect(redirectedUrl("/editor"));
        result.andExpect(flash().attributeCount(2));
        result.andExpect(flash().attribute("templateForm", new TemplateForm(template, model, mode)));
        //noinspection unchecked
        assertThat((List<FlashMessage>) result.andReturn().getFlashMap().get(STANDARD_FLASH_ATTRIBUTE_NAME))
                .containsExactlyInAnyOrder(m1);
    }

}
