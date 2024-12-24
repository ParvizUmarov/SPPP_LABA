package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Barber;
import org.example.repository.BarberRepo;
import org.springframework.stereotype.Service;

public interface BarberService {

    String getAll();
    String get(String name);
    String add(String name, String surname, String email);
    String update(String name);
    String delete(String name);

    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Base implements BarberService {

        private final IOService ioService;
        private final I18nService i18nService;
        private final BarberRepo repository;

        @Override
        public String getAll() {
            var result = repository.getAll();
            StringBuilder sb = new StringBuilder();
            for (Barber barber : result) {
                sb.append(entityToString(barber)).append("\n\n");
            }
            return sb.toString();
        }

        @Override
        public String get(String name) {
            try{
                var result = repository.getByArg(name);
                if (result == null) {
                    return i18nService.getMessage("no-info");
                }
                return entityToString(result).toString();
            }catch (Exception e){
               return i18nService.getMessage("no-info");
            }
        }

        @Override
        public String add(String name, String surname, String email) {
            try {
                var barber = new Barber();
                barber.setName(name);
                barber.setSurname(surname);
                barber.setBirthday("2004-10-10");
                barber.setMail(email);
                barber.setPhone("99999999");
                barber.setWorkExperience(10);
                barber.setAuthState(false);
                repository.add(barber);
                return i18nService.getMessage("succesfully-create");
            } catch (Exception e) {
                return e.getMessage();
            }

        }

        @Override
        public String update(String oldName) {
            try {
                ioService.print(i18nService.getMessage("enter-name"));
                var newName = ioService.readLine();
                repository.update(newName, oldName);
                return i18nService.getMessage("name-changed") + newName;
            } catch (Exception e) {
                log.info(e.getMessage());
                return i18nService.getMessage("cannot-changed");
            }
        }

        @Override
        public String delete(String data) {
            try {
                repository.delete(data);
                return i18nService.getMessage("succesfully-create");
            } catch (Exception e) {
                ioService.println(e.toString());
                return i18nService.getMessage("cannot-delete") + data;
            }
        }

        StringBuilder entityToString(Barber barber) {
            StringBuilder sb = new StringBuilder();
            return sb.append(i18nService.getMessage("name")).append(" ").append(barber.getName()).append("\n")
                    .append(i18nService.getMessage("surname")).append(" ").append(barber.getSurname()).append("\n")
                    .append(i18nService.getMessage("phone")).append(" ").append(barber.getPhone()).append("\n")
                    .append(i18nService.getMessage("mail")).append(" ").append(barber.getMail());
        }

    }

}
