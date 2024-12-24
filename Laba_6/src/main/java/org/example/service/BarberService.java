package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BarberDto;
import org.example.entity.Barber;
import org.example.repository.BarberRepository;
import org.springframework.stereotype.Service;

public interface BarberService {
    String getAll();
    String get(String name);
    String add(String data);
    String update(String data);
    String delete(String data);

    @Service
    @RequiredArgsConstructor
    class Base implements BarberService {

        private final IOService ioService;
        private final I18nService i18nService;
        private final BarberRepository repository;

        @Override
        public String getAll() {
            var result = repository.getAllBarbers();
            StringBuilder sb = new StringBuilder();
            for (Barber barber : result) {
                sb.append(entityToString(barber)).append("\n\n");
            }
            return sb.toString();
        }

        @Override
        public String get(String name) {
            var result = repository.findByName(name);
            return entityToString(result).toString();
        }

        @Override
        public String add(String data) {
            try {
                var barber = new Barber();
                barber.setName(data);
                barber.setSurname("surname");
                barber.setBirthday("2004-10-10");
                barber.setMail(data + "@gmail.ru");
                barber.setPhone("99999999");
                barber.setWorkExperience(10);
                barber.setAuthState(false);
                repository.save(barber);
                return i18nService.getMessage("succesfully-create");
            }catch (Exception e){
                return i18nService.getMessage("error");
            }

        }

        @Override
        public String update(String oldName) {
            try{
                ioService.print(i18nService.getMessage("enter-name"));
                var newName = ioService.readLine();
                repository.updateBarber(newName, oldName);
                return i18nService.getMessage("name-changed") + newName;
            }catch (Exception e){
                return i18nService.getMessage("cannot-changed");
            }
        }

        @Override
        public String delete(String data) {
            try{
                repository.deleteBarber(data);
                return i18nService.getMessage("succesfully-create");
            }catch (Exception e){
                ioService.println(e.toString());
                return i18nService.getMessage("cannot-delete") + " " + data;
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