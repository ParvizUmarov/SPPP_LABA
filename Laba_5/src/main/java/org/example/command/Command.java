package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.service.BarberService;
import org.example.service.LanguageService;
import org.example.service.ServiceCRUD;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class Command {

    private final BarberService barberService;
    private final LanguageService languageService;
    private final ServiceCRUD serviceCRUD;

    @ShellMethod(key = "lang")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage();
    }

    @ShellMethod(key = "lang -c")
    public String changeLanguage(@ShellOption String arg) {
        return languageService.setCurrentLanguage(arg);
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
    public String delete(@ShellOption String arg) {
        return barberService.delete(arg);
    }

    @ShellMethod(key = "barber -u")
    public String update(@ShellOption String arg) {
        return barberService.update(arg);
    }

    @ShellMethod(key = "barber ")
    public String create(@ShellOption String args) {
        return barberService.add(args);
    }

    @ShellMethod(key = "service -a")
    public String getAllService() {
        return serviceCRUD.getAll();
    }

    @ShellMethod(key = "service")
    public String getServiceByName(@ShellOption String arg) {
        return serviceCRUD.get(arg);
    }

    @ShellMethod(key = "service -d")
    public String deleteServiceByName(@ShellOption String arg) {
        return serviceCRUD.delete(arg);
    }

    @ShellMethod(key = "service -add")
    public String addService(@ShellOption String arg) {
        return serviceCRUD.add(arg);
    }

    @ShellMethod(key = "service -u")
    public String updateService(@ShellOption String arg) {
        return serviceCRUD.update(arg);
    }




}
