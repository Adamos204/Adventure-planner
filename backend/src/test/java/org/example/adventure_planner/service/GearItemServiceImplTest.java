/*package org.example.adventure_planner.service;

import org.example.adventure_planner.model.GearItem;
import org.example.adventure_planner.repository.GearItemRepository;
import org.example.adventure_planner.repository.UserAdventureRepository;
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
class GearItemServiceImplTest {

    private static final String GEAR_ITEM_NOT_FOUND_MSG = "Gear item not found";
    private static final String GEAR_ITEM_CANNOT_BE_NULL_MSG = "Gear item cannot be null";
    private static final String ADVENTURE_NOT_FOUND_MSG = "Adventure not found";

    @Mock
    private GearItemRepository gearItemRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private UserAdventureRepository userAdventureRepository;

    @InjectMocks
    private GearItemServiceImpl gearItemService;

    private GearItem sampleGearItem;

    @BeforeEach
    void setUp() {
        sampleGearItem = new GearItem();
        sampleGearItem.setId(1L);
        sampleGearItem.setName("Sample Gear");
    }

    @Test
    void getAllGearItems_returnsList() {
        when(gearItemRepository.findAll()).thenReturn(Arrays.asList(sampleGearItem));

        List<GearItem> gearItems = gearItemService.getAllGearItems();

        assertEquals(1, gearItems.size());
        assertEquals(sampleGearItem, gearItems.get(0));
        verify(gearItemRepository).findAll();
    }

    @Test
    void getGearItemById_existingId_returnsGearItem() {
        when(gearItemRepository.existsById(1L)).thenReturn(true);
        when(gearItemRepository.findById(1L)).thenReturn(Optional.of(sampleGearItem));

        Optional<GearItem> result = gearItemService.getGearItemById(1L);

        assertTrue(result.isPresent());
        assertEquals(sampleGearItem, result.get());

        verify(validationService).requireId(1L);
        verify(gearItemRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, "Gear item with ID 1 not found");
        verify(gearItemRepository).findById(1L);
    }

    @Test
    void getGearItemById_nonExistingId_throwsException() {
        when(gearItemRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException("Gear item with ID 1 not found"))
                .when(validationService).requireEntityExists(false, "Gear item with ID 1 not found");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gearItemService.getGearItemById(1L);
        });

        assertEquals("Gear item with ID 1 not found", exception.getMessage());

        verify(validationService).requireId(1L);
        verify(gearItemRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, "Gear item with ID 1 not found");
        verify(gearItemRepository, never()).findById(any());
    }

    @Test
    void addGearItem_valid_returnsSavedGearItem() {
        when(gearItemRepository.save(sampleGearItem)).thenReturn(sampleGearItem);

        GearItem result = gearItemService.addGearItem(sampleGearItem);

        assertEquals(sampleGearItem, result);
        verify(validationService).requireNotNull(sampleGearItem, GEAR_ITEM_CANNOT_BE_NULL_MSG);
        verify(gearItemRepository).save(sampleGearItem);
    }

    @Test
    void addGearItem_null_throwsException() {
        doThrow(new IllegalArgumentException(GEAR_ITEM_CANNOT_BE_NULL_MSG))
                .when(validationService).requireNotNull(null, GEAR_ITEM_CANNOT_BE_NULL_MSG);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            gearItemService.addGearItem(null);
        });

        assertEquals(GEAR_ITEM_CANNOT_BE_NULL_MSG, exception.getMessage());
        verify(validationService).requireNotNull(null, GEAR_ITEM_CANNOT_BE_NULL_MSG);
        verify(gearItemRepository, never()).save(any());
    }

    @Test
    void updateGearItem_valid_returnsUpdatedGearItem() {
        when(gearItemRepository.existsById(sampleGearItem.getId())).thenReturn(true);
        when(gearItemRepository.save(sampleGearItem)).thenReturn(sampleGearItem);

        GearItem result = gearItemService.updateGearItem(sampleGearItem);

        assertEquals(sampleGearItem, result);

        verify(validationService).requireNotNull(sampleGearItem, GEAR_ITEM_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleGearItem.getId());
        verify(gearItemRepository).existsById(sampleGearItem.getId());
        verify(validationService).requireEntityExists(true, GEAR_ITEM_NOT_FOUND_MSG);
        verify(gearItemRepository).save(sampleGearItem);
    }

    @Test
    void updateGearItem_gearItemNotFound_throwsException() {
        when(gearItemRepository.existsById(sampleGearItem.getId())).thenReturn(false);

        doThrow(new IllegalStateException(GEAR_ITEM_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, GEAR_ITEM_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gearItemService.updateGearItem(sampleGearItem);
        });

        assertEquals(GEAR_ITEM_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireNotNull(sampleGearItem, GEAR_ITEM_CANNOT_BE_NULL_MSG);
        verify(validationService).requireId(sampleGearItem.getId());
        verify(gearItemRepository).existsById(sampleGearItem.getId());
        verify(validationService).requireEntityExists(false, GEAR_ITEM_NOT_FOUND_MSG);
        verify(gearItemRepository, never()).save(any());
    }

    @Test
    void deleteGearItem_existingGearItem_doesNotThrow() {
        when(gearItemRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> gearItemService.deleteGearItem(1L));

        verify(validationService).requireId(1L);
        verify(gearItemRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, GEAR_ITEM_NOT_FOUND_MSG);
        verify(gearItemRepository).deleteById(1L);
    }

    @Test
    void deleteGearItem_gearItemNotFound_throwsException() {
        when(gearItemRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException(GEAR_ITEM_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, GEAR_ITEM_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gearItemService.deleteGearItem(1L);
        });

        assertEquals(GEAR_ITEM_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(1L);
        verify(gearItemRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, GEAR_ITEM_NOT_FOUND_MSG);
        verify(gearItemRepository, never()).deleteById(any());
    }

    @Test
    void getAllAdventureItems_existingAdventure_returnsGearItems() {
        when(userAdventureRepository.existsById(1L)).thenReturn(true);
        when(gearItemRepository.findByAdventureId(1L)).thenReturn(Arrays.asList(sampleGearItem));

        List<GearItem> result = gearItemService.getAllAdventureItems(1L);

        assertEquals(1, result.size());
        assertEquals(sampleGearItem, result.get(0));

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(true, ADVENTURE_NOT_FOUND_MSG);
        verify(gearItemRepository).findByAdventureId(1L);
    }

    @Test
    void getAllAdventureItems_adventureNotFound_throwsException() {
        when(userAdventureRepository.existsById(1L)).thenReturn(false);

        doThrow(new IllegalStateException(ADVENTURE_NOT_FOUND_MSG))
                .when(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            gearItemService.getAllAdventureItems(1L);
        });

        assertEquals(ADVENTURE_NOT_FOUND_MSG, exception.getMessage());

        verify(validationService).requireId(1L);
        verify(userAdventureRepository).existsById(1L);
        verify(validationService).requireEntityExists(false, ADVENTURE_NOT_FOUND_MSG);
        verify(gearItemRepository, never()).findByAdventureId(any());
    }
}*/