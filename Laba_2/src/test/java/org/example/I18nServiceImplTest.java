package org.example;

import org.example.bot.LanguageService;
import org.example.bot.LoggerConsole;
import org.example.service.I18nServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class I18nServiceImplTest {

    private I18nServiceImpl i18nService;
    private LoggerConsole logger;
    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        logger = Mockito.mock(LoggerConsole.class);
        languageService = Mockito.mock(LanguageService.class);
        i18nService = new I18nServiceImpl(logger, languageService);
    }

    @Test
    void testGetMessageWithoutParams() {
        when(languageService.getCurrentLanguage()).thenReturn("ru");
        String message = i18nService.getMessage("enter-barber-name");
        assertEquals("Введите название барбера: ", message);
    }

}
