package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.service.BarberService;
import org.example.service.LanguageService;
import org.example.service.ServiceCRUD;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * @author parviz
 */
@ShellComponent
@RequiredArgsConstructor
public class Command {

    private final BarberService barberService;
    private final LanguageService languageService;
    private final ServiceCRUD serviceCRUD;

    /**
     * command lang that get current language
     * @return current selected language
     */
    @ShellMethod(key = "lang")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage();
    }

    /**
     * Shell command lang -c for change language
     * @param arg value that we want to change
     * @return changed language
     */
    @ShellMethod(key = "lang -c")
    public String changeLanguage(@ShellOption String arg) {
        return languageService.setCurrentLanguage(arg);
    }


    /**
     * Shell command find -a for get all barbers from db
     * @return list of Barber
     */
    @ShellMethod(key = "find -a")
    public String findAll() {
        return barberService.getAll();
    }

    /**
     * Shell find command find barber by argument
     * @param arg the barber name
     * @return entity of Barber
     */
    @ShellMethod(key = "find")
    public String find(@ShellOption String arg) {
        return barberService.get(arg);
    }

    /**
     * Shell command barber with -d flag delete barber
     * @param arg name of barber where you want delete
     * @return message that delete is completed or not
     */
    @ShellMethod(key = "barber -d")
    public String delete(@ShellOption String arg) {
        return barberService.delete(arg);
    }

    /**
     * Shell command barber -u update barber info
     * @param arg name of barber
     * @return message
     */
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
