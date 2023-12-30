package com.passion.lingosphere.services;

import com.passion.lingosphere.dtos.LanguageDto;
import com.passion.lingosphere.models.Language;
import com.passion.lingosphere.repositories.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;
    @Mock
    private Language language;
    @InjectMocks
    private LanguageService languageService;
    private LanguageDto languageDto;

    @BeforeEach
    public void setup() {
        String name = "testlang";
        String code = "tl";
        languageDto = new LanguageDto(name, code);
    }

    @Test
    public void addLanguageSuccessfulTest() throws Exception {
        given(languageRepository.existsByName(languageDto.getName())).willReturn(false);
        given(languageRepository.existsByCode(languageDto.getCode())).willReturn(false);
        given(languageRepository.save(any(Language.class))).willReturn(new Language(languageDto.getName(), languageDto.getCode()));

        Language added = languageService.addLanguage(languageDto);

        assertNotNull(added);
        assertEquals(languageDto.getName(), added.getName());
    }
}