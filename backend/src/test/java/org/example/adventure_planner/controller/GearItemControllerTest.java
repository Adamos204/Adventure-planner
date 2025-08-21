/*package org.example.adventure_planner.controller;

import org.example.adventure_planner.model.GearItem;
import org.example.adventure_planner.service.GearItemService;
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
class GearItemControllerTest {

    @Mock
    private GearItemService gearItemService;

    @InjectMocks
    private GearItemController gearItemController;

    private GearItem sampleGearItem;

    @BeforeEach
    void setUp() {
        sampleGearItem = new GearItem();
        sampleGearItem.setId(1L);
        sampleGearItem.setName("Sample Gear");
    }

    @Test
    void testGetAllGearItems() {
        when(gearItemService.getAllGearItems()).thenReturn(Arrays.asList(sampleGearItem));

        ResponseEntity<List<GearItem>> response = gearItemController.getAllGearItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(gearItemService, times(1)).getAllGearItems();
    }

    @Test
    void testGetGearItemById_Found() {
        when(gearItemService.getGearItemById(1L)).thenReturn(Optional.of(sampleGearItem));

        ResponseEntity<GearItem> response = gearItemController.getAdventureById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleGearItem, response.getBody());
        verify(gearItemService, times(1)).getGearItemById(1L);
    }

    @Test
    void testGetGearItemById_NotFound() {
        Long id = 1L;
        when(gearItemService.getGearItemById(id)).thenReturn(Optional.empty());

        ResponseEntity<GearItem> response = gearItemController.getAdventureById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(gearItemService, times(1)).getGearItemById(id);
    }

    @Test
    void testAddGearItem() {
        when(gearItemService.addGearItem(any(GearItem.class))).thenReturn(sampleGearItem);

        ResponseEntity<GearItem> response = gearItemController.addGearItem(sampleGearItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(sampleGearItem, response.getBody());
        verify(gearItemService, times(1)).addGearItem(sampleGearItem);
    }

    @Test
    void testUpdateGearItem() {
        when(gearItemService.updateGearItem(any(GearItem.class))).thenReturn(sampleGearItem);

        ResponseEntity<GearItem> response = gearItemController.updateGearItem(sampleGearItem.getId(),  sampleGearItem);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sampleGearItem, response.getBody());
        verify(gearItemService, times(1)).updateGearItem(sampleGearItem);
    }

    @Test
    void testDeleteGearItem() {
        doNothing().when(gearItemService).deleteGearItem(1L);

        ResponseEntity<Void> response = gearItemController.deleteGearItem(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(gearItemService, times(1)).deleteGearItem(1L);
    }

    @Test
    void testGetAllAdventureItems() {
        Long adventureId = 42L;
        when(gearItemService.getAllAdventureItems(adventureId)).thenReturn(Arrays.asList(sampleGearItem));

        ResponseEntity<List<GearItem>> response = gearItemController.getAllAdventureItems(adventureId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(gearItemService, times(1)).getAllAdventureItems(adventureId);
    }
}*/