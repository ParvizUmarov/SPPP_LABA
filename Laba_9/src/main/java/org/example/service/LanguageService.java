package org.example.serviceInterface;

import java.util.Locale;

public interface LanguageService {
    Locale getCurrentLanguage();
    String setCurrentLanguage(String language);
}
