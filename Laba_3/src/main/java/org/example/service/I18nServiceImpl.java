package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class I18nServiceImpl implements I18nService {

    private Map<String, String> phraseMap = Map.ofEntries(
            Map.entry("ru#command-not-found", "Команда не найдена. Попробуйте <help>"),
            Map.entry("ru#helpCommand", "Все команды:\n-find <name>\n-find all\n-help"),
            Map.entry("ru#no-info", "Нету информации"),
            Map.entry("ru#welcome", "Добро пожаловать!"),
            Map.entry("ru#name", "имя:"),
            Map.entry("ru#surname", "фамилия:"),
            Map.entry("ru#phone", "номер"),
            Map.entry("ru#mail", "почта"),

            Map.entry("en#command-not-found", "Command not found. Try <help>"),
            Map.entry("en#helpCommand", "All commands:\n-find <имя>\n-find all\n-help"),
            Map.entry("en#no-info", "No information"),
            Map.entry("en#name", "name:"),
            Map.entry("en#surname", "surname"),
            Map.entry("en#phone", "phone"),
            Map.entry("en#mail", "mail")

    );

    private final LanguageService languageService;

    public I18nServiceImpl(LanguageService languageService) {
        this.languageService = languageService;
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    @Override
    public String getMessage(String code, Map<String, Object> params) {
        var language = languageService.getCurrentLanguage();
        var key = language + "#" + code;
        var phrase = phraseMap.get(key);
        if (params != null)
            phrase = handlePlaceholders(phrase, params);
        return phrase;
    }

    private String handlePlaceholders(String phrase, Map<String, Object> params) {
        for (var e : params.entrySet())
            phrase = phrase.replace("%" + e.getKey() + "%", e.getValue().toString());
        return phrase;
    }
}
