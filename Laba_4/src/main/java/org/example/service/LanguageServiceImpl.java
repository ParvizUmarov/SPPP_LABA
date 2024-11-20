package org.example.service;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;

@Service
public class LanguageServiceImpl implements LanguageService {
    private static final Collection<String> supportedLanguages = Set.of("ru", "en");

    @Override
    public Locale getCurrentLanguage() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public void setCurrentLanguage(String language) {
        language = language.strip().toLowerCase();
        if ( ! supportedLanguages.contains(language))
            throw new IllegalArgumentException("Not supported language: " + language);

        LocaleContextHolder.setLocale(Locale.forLanguageTag(language));
    }
}

