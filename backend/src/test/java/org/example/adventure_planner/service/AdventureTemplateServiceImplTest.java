package org.example.adventure_planner.service;

import org.example.adventure_planner.model.AdventureTemplate;
import org.example.adventure_planner.repository.AdventureTemplateRepository;
import org.example.adventure_planner.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdventureTemplateServiceImplTest {

    private static final String TEMPLATE_NOT_FOUND_MSG = "Template not found";
    private static final String TEMPLATE_CANNOT_BE_NULL_MSG = "Template cannot be null";

    @Mock
    private AdventureTemplateRepository adventureTemplateRepository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private AdventureTemplateServiceImpl adventureTemplateService;

    private AdventureTemplate sampleTemplate;

    @BeforeEach
    void setUp() {
        sampleTemplate = new AdventureTemplate();
        sampleTemplate.setId(1L);
        sampleTemplate.setName("Test Template");
    }

    @Test
    void getAllAdventureTemplates_returnsList() {
        when(adventureTemplateRepository.findAll()).thenReturn(Arrays.asList(sampleTemplate));

        List<AdventureTemplate> templates = adventureTemplateService.getAllAdventureTemplates();

        assertEquals(1, templates.size());
        assertEquals(sampleTemplate, templates.get(0));
        verify(adventureTemplateRepository).findAll();
    }

    @Test
    void getAdventureTemplateById_existingId_returnsTemplate() {
        Long id = 1L;
        when(adventureTemplateRepository.existsById(id)).thenReturn(true);
        when(adventureTemplateRepository.findById(id)).thenReturn(Optional.of(sampleTemplate));

        Optional<AdventureTemplate> result = adventureTemplateService.getAdventureTemplateById(id);

        assertTrue(result.isPresent());
        assertEquals(sampleTemplate, result.get());

        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(true, "Template with ID " + id + " not found");
        verify(adventureTemplateRepository).findById(id);
    }

    @Test
    void getAdventureTemplateById_nonExistingId_throwsException() {
        Long id = 1L;
        when(adventureTemplateRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException("Template with ID " + id + " not found"))
                .when(validationService).requireEntityExists(false, "Template with ID " + id + " not found");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            adventureTemplateService.getAdventureTemplateById(id);
        });

        assertEquals("Template with ID " + id + " not found", exception.getMessage());

        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(false, "Template with ID " + id + " not found");
        verify(adventureTemplateRepository, never()).findById(any());
    }

    @Test
    void addAdventureTemplate_valid_returnsSavedTemplate() {
        when(adventureTemplateRepository.save(sampleTemplate)).thenReturn(sampleTemplate);

        AdventureTemplate result = adventureTemplateService.addAdventureTemplate(sampleTemplate);

        assertEquals(sampleTemplate, result);
        verify(validationService).requireNotNull(sampleTemplate, TEMPLATE_CANNOT_BE_NULL_MSG);
        verify(adventureTemplateRepository).save(sampleTemplate);
    }

    @Test
    void addAdventureTemplate_null_throwsException() {
        doThrow(new IllegalArgumentException(TEMPLATE_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, TEMPLATE_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            adventureTemplateService.addAdventureTemplate(null);
        });

        assertEquals(TEMPLATE_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, TEMPLATE_CANNOT_BE_NULL_MSG);
        verify(adventureTemplateRepository, never()).save(any());
    }

    @Test
    void updateAdventureTemplate_valid_returnsUpdatedTemplate() {
        Long id = sampleTemplate.getId();
        when(adventureTemplateRepository.existsById(id)).thenReturn(true);
        when(adventureTemplateRepository.save(sampleTemplate)).thenReturn(sampleTemplate);

        AdventureTemplate result = adventureTemplateService.updateAdventureTemplate(sampleTemplate);

        assertEquals(sampleTemplate, result);

        verify(validationService).requireNotNull(sampleTemplate, TEMPLATE_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(true, TEMPLATE_NOT_FOUND_MSG);
        verify(adventureTemplateRepository).save(sampleTemplate);
    }

    @Test
    void updateAdventureTemplate_templateNotFound_throwsException() {
        Long id = sampleTemplate.getId();
        when(adventureTemplateRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException(TEMPLATE_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TEMPLATE_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            adventureTemplateService.updateAdventureTemplate(sampleTemplate);
        });

        assertEquals(TEMPLATE_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleTemplate, TEMPLATE_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(false, TEMPLATE_NOT_FOUND_MSG);
        verify(adventureTemplateRepository, never()).save(any());
    }

    @Test
    void deleteAdventureTemplate_existingTemplate_doesNotThrow() {
        Long id = 1L;
        when(adventureTemplateRepository.existsById(id)).thenReturn(true);

        assertDoesNotThrow(() -> adventureTemplateService.deleteAdventureTemplate(id));

        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(true, TEMPLATE_NOT_FOUND_MSG);
        verify(adventureTemplateRepository).deleteById(id);
    }

    @Test
    void deleteAdventureTemplate_templateNotFound_throwsException() {
        Long id = 1L;
        when(adventureTemplateRepository.existsById(id)).thenReturn(false);

        doThrow(new IllegalStateException(TEMPLATE_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, TEMPLATE_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            adventureTemplateService.deleteAdventureTemplate(id);
        });

        assertEquals(TEMPLATE_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(id);
        verify(adventureTemplateRepository).existsById(id);
        verify(validationService).requireEntityExists(false, TEMPLATE_NOT_FOUND_MSG);
        verify(adventureTemplateRepository, never()).deleteById(any());
    }
}