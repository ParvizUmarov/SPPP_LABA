package org.example;

import org.example.bot.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LanguageServiceImplTest {

    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        languageService = Mockito.mock(LanguageService.class);
    }

    @Test
    void testLang() {
        when(languageService.getCurrentLanguage()).thenReturn("ru");
        var actual = languageService.getCurrentLanguage();
        assertEquals("ru", actual);
    }

}
