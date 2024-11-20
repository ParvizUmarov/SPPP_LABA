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

    @ShellMethod(key = "lang")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage().toString();
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

    @ShellMethod(key = "barber -d")
    public String delete(@ShellOption String arg){
        return barberService.delete(arg);
    }

    @ShellMethod(key = "barber -u")
    public String update(@ShellOption String arg){
        return barberService.update(arg);
    }

    @ShellMethod(key = "barber ")
    public String create(@ShellOption String args){
        return barberService.add(args);
    }
}
