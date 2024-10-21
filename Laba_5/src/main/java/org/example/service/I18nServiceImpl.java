package org.example.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class I18nServiceImpl implements I18nService {

    private Map<String, String> phraseMap = Map.ofEntries(
            Map.entry("ru#helpCommand", "Все команды:\n-find <name>\n-find all\n-help"),
            Map.entry("ru#no-info", "Нету информации"),
            Map.entry("ru#name", "имя:"),
            Map.entry("ru#surname", "фамилия:"),
            Map.entry("ru#phone", "номер"),
            Map.entry("ru#succesfully-create", "Пользователь успешно создан"),
            Map.entry("ru#error", "Что та пошло не так!"),
            Map.entry("ru#mail", "почта"),
            Map.entry("ru#no-such-user", "Такого пользователя нету в бд"),
            Map.entry("ru#enter-name", "Введите новое имя: "),
            Map.entry("ru#name-changed", "Имя пользователь был изменен на: "),
            Map.entry("ru#cannot-changed", "Не удалось изменить имя пользователя: "),
            Map.entry("ru#cannot-delete", "Не удалось удалить пользователя: "),
            Map.entry("ru#service-name", "Название услуги: "),
            Map.entry("ru#service-price", "Стоимость услуги: "),
            Map.entry("ru#service-delete", "Сервис удален"),
            Map.entry("ru#service-create", "Сервис успешно создан"),
            Map.entry("ru#enter-service-price", "Введите цену на услугу:"),
            Map.entry("ru#service-update-error", "Не получилось обновить"),
            Map.entry("ru#service-update-success", "Данные сервиса успешно изменены"),

            Map.entry("en#helpCommand", "All commands:\n-find <имя>\n-find all\n-help"),
            Map.entry("en#no-info", "No information"),
            Map.entry("en#succesfully-create", "User is successfully create"),
            Map.entry("en#name", "name:"),
            Map.entry("en#surname", "surname"),
            Map.entry("en#phone", "phone"),
            Map.entry("en#mail", "mail"),
            Map.entry("en#error", "Something went wrong!"),
            Map.entry("en#no-such-user", "There is no such user in the database"),
            Map.entry("en#enter-name", "Enter a new name: "),
            Map.entry("en#name-changed", "The username has been changed to: "),
            Map.entry("en#cannot-changed", "Failed to change username: "),
            Map.entry("en#cannot-delete", "Failed to delete user: "),
            Map.entry("en#service-name", "Service name: "),
            Map.entry("en#service-price", "Service price: "),
            Map.entry("en#service-delete", "Service deleted"),
            Map.entry("en#service-create", "Service created successfully! "),
            Map.entry("en#enter-service-price", "Enter the price for the service: "),
            Map.entry("en#service-update-error", "Cannot update service"),
            Map.entry("en#service-update-success", "Service update successfully")

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
