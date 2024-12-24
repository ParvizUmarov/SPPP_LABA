package org.example.command;

import lombok.RequiredArgsConstructor;
import org.example.service.impl.BarberService;
import org.example.service.LanguageService;
import org.example.service.impl.ServiceCRUD;
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

    @ShellMethod(key = "lang", value = "Для получение текущего языка в программе")
    public String getCurrentLanguage() {
        return languageService.getCurrentLanguage().toString();
    }

    @ShellMethod(key = "lang -c", value = "Для изменение языка в программе")
    public String changeLanguage(@ShellOption String arg) {
        return languageService.setCurrentLanguage(arg);
    }

    @ShellMethod(key = "find -a", value = "Для получение всех парикмахеров из бд")
    public String findAll() {
        return barberService.getAll();
    }

    @ShellMethod(key = "find", value = "Поиск парикмахера по его имени")
    public String find(@ShellOption String arg) {
        return barberService.get(arg);
    }

    @ShellMethod(key = "barber -d", value = "Удаление парикмахера по его имени")
    public String delete(@ShellOption String arg) {
        return barberService.delete(arg);
    }

    @ShellMethod(key = "barber -u", value = "Обновление имени парикмахера")
    public String update(@ShellOption String arg) {
        return barberService.update(arg);
    }

    @ShellMethod(key = "barber ", value = "Создание парикмахера")
    public String create(@ShellOption String args) {
        return barberService.add(args);
    }

    @ShellMethod(key = "service -a", value = "Для получение всех сервисов")
    public String getAllService() {
        return serviceCRUD.getAll();
    }

    @ShellMethod(key = "service", value = "Для поиска сервиса по его названию")
    public String getServiceByName(@ShellOption String arg) {
        return serviceCRUD.get(arg);
    }

    @ShellMethod(key = "service -d", value = "Удаление сервиса по его имени")
    public String deleteServiceByName(@ShellOption String arg) {
        return serviceCRUD.delete(arg);
    }

    @ShellMethod(key = "service -add", value = "Добавление нового сервиса")
    public String addService(@ShellOption String arg) {
        return serviceCRUD.add(arg);
    }

    @ShellMethod(key = "service -u", value = "Обновление сервиса по его имени")
    public String updateService(@ShellOption String arg) {
        return serviceCRUD.update(arg);
    }

}
