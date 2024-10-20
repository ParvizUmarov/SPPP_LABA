package org.example.service;

import org.example.dto.BarberDto;
import org.example.repository.BarberRepository;
import org.springframework.stereotype.Service;

@Service
public class BarberService implements CRUDService<String> {

    private final BarberRepository repository;
    private final IOService ioService;
    private final I18nService i18nService;

    public BarberService(BarberRepository repository, IOService ioService, I18nService i18nService) {
        this.repository = repository;
        this.ioService = ioService;
        this.i18nService = i18nService;
    }

    @Override
    public String getAll() {
        return repository.getAllBarber();
    }

    @Override
    public String get(String name) {
        return repository.getUserByName(name);
    }

    @Override
    public String add(String data) {
        var barber = new BarberDto();
        barber.setName(data);
        barber.setSurname("surname");
        barber.setBirthday("2004-10-10");
        barber.setMail(data + "@gmail.ru");
        barber.setPhone("99999999");
        barber.setWorkExperience(10);
        barber.setServiceId(5);
        barber.setSalonId(10);
        barber.setAuthState(false);
        var isAddSuccessfully = repository.addBarber(barber);
        return isAddSuccessfully
                ? i18nService.getMessage("succesfully-create")
                : i18nService.getMessage("error");
    }

    @Override
    public String update(String oldName) {
        var check =  repository.checkBarberExist(oldName);
        if(check.isEmpty()){
            return i18nService.getMessage("no-such-user");
        }else{
            ioService.print(i18nService.getMessage("enter-name"));
            var newName = ioService.readLine();
            var isUpdate = repository.updateBarber(newName, oldName);
            return isUpdate ? i18nService.getMessage("name-changed") + newName : i18nService.getMessage("cannot-changed");
        }
    }

    @Override
    public String delete(String data) {
        var isDeleted = repository.deleteBarber(data);
        return isDeleted ? "Пользователь <" + data + "> успешно удален" : "Не удалось удалить " + data;
    }
}
