package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.AdventureTemplate;
import org.example.adventure_planner.service.AdventureTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class AdventureTemplateControllerTest {

    @Mock
    private AdventureTemplateService adventureTemplateService;

    @InjectMocks
    private AdventureTemplateController adventureTemplateController;

    private AdventureTemplate sampleTemplate;

    @BeforeEach
    void setUp() {
        sampleTemplate = new AdventureTemplate();
        sampleTemplate.setId(1L);
        sampleTemplate.setName("Sample Adventure");
    }

    @Test
    void testGetAllAdventureTemplates() {
        when(adventureTemplateService.getAllAdventureTemplates()).thenReturn(Arrays.asList(sampleTemplate));

        ResponseEntity<List<AdventureTemplate>> response = adventureTemplateController.getAllAdventureTemplates();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(adventureTemplateService, times(1)).getAllAdventureTemplates();
    }

    @Test
    void testAddAdventureTemplate() {
        when(adventureTemplateService.addAdventureTemplate(any(AdventureTemplate.class))).thenReturn(sampleTemplate);

        ResponseEntity<AdventureTemplate> response = adventureTemplateController.addAdventureTemplate(sampleTemplate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleTemplate, response.getBody());
        verify(adventureTemplateService, times(1)).addAdventureTemplate(sampleTemplate);
    }

    @Test
    void testGetAdventureTemplateById_Found() {
        when(adventureTemplateService.getAdventureTemplateById(1L)).thenReturn(Optional.of(sampleTemplate));

        ResponseEntity<AdventureTemplate> response = adventureTemplateController.getAdventureTemplateById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTemplate, response.getBody());
        verify(adventureTemplateService, times(1)).getAdventureTemplateById(1L);
    }

    @Test
    void testGetAdventureTemplateById_NotFound() {
        Long id = 1L;
        when(adventureTemplateService.getAdventureTemplateById(id)).thenReturn(Optional.empty());

        ResponseEntity<AdventureTemplate> response = adventureTemplateController.getAdventureTemplateById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(adventureTemplateService, times(1)).getAdventureTemplateById(id);
    }

    @Test
    void testUpdateAdventureTemplate() {
        when(adventureTemplateService.updateAdventureTemplate(any(AdventureTemplate.class))).thenReturn(sampleTemplate);

        ResponseEntity<AdventureTemplate> response = adventureTemplateController.updateAdventureTemplate(sampleTemplate.getId(), sampleTemplate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleTemplate, response.getBody());
        verify(adventureTemplateService, times(1)).updateAdventureTemplate(sampleTemplate);
    }

    @Test
    void testDeleteAdventureTemplate() {
        doNothing().when(adventureTemplateService).deleteAdventureTemplate(1L);

        ResponseEntity<Void> response = adventureTemplateController.deleteAdventureTemplate(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(adventureTemplateService, times(1)).deleteAdventureTemplate(1L);
    }
}