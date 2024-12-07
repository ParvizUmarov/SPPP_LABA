package org.example.service;

import java.util.Locale;

public interface LanguageService {
    Locale getCurrentLanguage();
    String setCurrentLanguage(String language);
}
