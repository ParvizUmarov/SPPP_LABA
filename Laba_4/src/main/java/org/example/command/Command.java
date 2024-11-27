package org.example.command;

import org.example.service.BarberService;
import org.example.service.LanguageService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Command {

    private final BarberService barberService;
    private final LanguageService languageService;

    public Command(BarberService barberService, LanguageService languageService) {
        this.barberService = barberService;
        this.languageService = languageService;
    }

    @ShellMethod(key = "lang", value = "Получить текущий язык системы")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage().toString();
    }

    @ShellMethod(key = "lang -c", value = "Изменить текущий язык")
    public String changeLanguage(@ShellOption(value = {"-c", "--change"}, help = "Укажите новый язык") String arg) {
        try{
            languageService.setCurrentLanguage(arg);
            return "language changed to " + arg;
        }catch (Exception e){
            return "language doesn't changed to " + arg;
        }
    }

    @ShellMethod(key = "find -a", value = "Вывод всех парикмахеров")
    public String findAll(@ShellOption(value = {"-a", "--all"}, help = "Вывести всех парикмахеров") boolean all) {
        return barberService.getAll();
    }

    @ShellMethod(key = "find", value = "Фильтр парикмахера по имени")
    public String find(@ShellOption(help = "Фильтр по имени") String arg) {
        return barberService.get(arg);
    }

    @ShellMethod(key = "barber -d")
    public String delete(@ShellOption(value = {"-d", "--delete"}, help = "Удаление парикмахера по имени") String arg){
        return barberService.delete(arg);
    }

    @ShellMethod(key = "barber -u", value = "Обновление")
    public String update(@ShellOption(value = {"-u", "--update"}, help = "Обновление профиля парикмахера") String arg){
        return barberService.update(arg);
    }

    @ShellMethod(key = "barber", value = "Создание парикмахера")
    public String create(@ShellOption String args){
        return barberService.add(args);
    }
}
