package org.example.command;


import lombok.RequiredArgsConstructor;
import org.example.service.BarberService;
import org.example.service.LanguageService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class Command {

    private final BarberService barberService;
    private final LanguageService languageService;

    @ShellMethod(key = "lang", value = "Получить текущий язык системы")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage().toString();
    }

    @ShellMethod(key = "lang -c", value = "Изменить текущий язык")
    public String changeLanguage(@ShellOption(value = {"-c", "--change"}, help = "Укажите новый язык") String arg) {
        try {
            languageService.setCurrentLanguage(arg);
            return "language changed to " + arg;
        } catch (Exception e) {
            return "language doesn't changed to " + arg;
        }
    }

    @ShellMethod(key = "find -a", value = "Вывод всех парикмахеров")
    public String findAll(
            @ShellOption(value = {"-a", "--all"}, help = "Вывести всех парикмахеров") boolean all) {
        return barberService.getAll();
    }

    @ShellMethod(key = "find -n", value = "Поиск парикмахера по имени")
    public String find(
            @ShellOption(value = {"-n", "--name"}, help = "Фильтр по имени") String name) {
        return barberService.get(name);
    }
}
