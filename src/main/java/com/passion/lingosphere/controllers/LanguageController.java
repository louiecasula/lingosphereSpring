package com.passion.lingosphere.controllers;

import com.passion.lingosphere.dtos.LanguageDto;
import com.passion.lingosphere.models.Language;
import com.passion.lingosphere.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<?> addLanguage(@RequestBody LanguageDto languageDto) {
        try {
            Language newLanguage = languageService.addLanguage(languageDto);
            return new ResponseEntity<>(newLanguage, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Language creation failed: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllLanguages() {
        List<Language> languageList = languageService.getAllLanguages();
        return new ResponseEntity<>(languageList, HttpStatus.OK);
    }

    @GetMapping("/{languageId}")
    public ResponseEntity<?> getLanguageById(@PathVariable Long languageId) throws Exception {
        Language language = languageService.getLanguageById(languageId);
        return new ResponseEntity<>(language, HttpStatus.OK);
    }
}
