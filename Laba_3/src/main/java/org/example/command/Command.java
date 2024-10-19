package org.example.command;


import org.example.service.BarberService;
import org.example.service.I18nService;
import org.example.service.LanguageService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class Command {

    private final BarberService barberService;
    private final LanguageService languageService;
    private final I18nService i18nService;

    public Command(BarberService barberService, LanguageService languageService, I18nService i18nService) {
        this.barberService = barberService;
        this.languageService = languageService;
        this.i18nService = i18nService;
    }

    @ShellMethod(key = "hello")
    public String helloWorld(@ShellOption(defaultValue = "spring") String arg) {
        return "Hello world " + arg;
    }

    @ShellMethod(key = "lang")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage();
    }

    @ShellMethod(key = "lang -c")
    public String changeLanguage(@ShellOption String arg) {
        try{
            languageService.setCurrentLanguage(arg);
            return "language changed to " + arg;
        }catch (Exception e){
            return "language doesn't changed to " + arg;
        }
    }

    @ShellMethod(key = "find -a")
    public String findAll() {
        return barberService.getAll();
    }

    @ShellMethod(key = "find")
    public String find(@ShellOption String arg) {
        return barberService.get(arg);
    }

}
