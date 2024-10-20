package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class LanguageServiceImpl implements LanguageService {
    private static final Collection<String> supportedLanguages = Set.of("ru", "en");

    private String currentLanguage = "ru";

    @Override
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    @Override
    public String setCurrentLanguage(String language) {
        language = language.strip().toLowerCase();
        if (!supportedLanguages.contains(language))
            return "Not supported language: " + language;
        currentLanguage = language;
        return "Language changed to " + language;
    }
}
