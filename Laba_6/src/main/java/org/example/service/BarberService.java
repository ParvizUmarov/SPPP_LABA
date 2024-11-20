package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BarberDto;
import org.example.entity.Barber;
import org.example.repository.BarberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BarberService implements CRUDService<String> {

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

    public static Barber mapToEntity(BarberDto barberDto){
        Barber barber = new Barber();
        barber.setId(barberDto.getId());
        barber.setName(barberDto.getName());
        barber.setSurname(barberDto.getSurname());
        barber.setBirthday(barberDto.getBirthday());
        barber.setPhone(barberDto.getPhone());
        barber.setMail(barberDto.getMail());
        barber.setWorkExperience(barberDto.getWorkExperience());
        barber.setPassword(barberDto.getPassword());
        barber.setAuthState(barberDto.getAuthState());
        return barber;
    }

    public static BarberDto mapToDto(Barber barber){
        BarberDto barberDto = new BarberDto();
        barberDto.setId(barber.getId());
        barberDto.setName(barber.getName());
        barberDto.setSurname(barber.getSurname());
        barberDto.setBirthday(barber.getBirthday());
        barberDto.setPhone(barber.getPhone());
        barberDto.setMail(barber.getMail());
        barberDto.setWorkExperience(barber.getWorkExperience());
        barberDto.setPassword(barber.getPassword());
        barberDto.setAuthState(barber.getAuthState());
        return barberDto;
    }

    StringBuilder entityToString(Barber barber) {
        StringBuilder sb = new StringBuilder();
        return sb.append(i18nService.getMessage("name")).append(" ").append(barber.getName()).append("\n")
                .append(i18nService.getMessage("surname")).append(" ").append(barber.getSurname()).append("\n")
                .append(i18nService.getMessage("phone")).append(" ").append(barber.getPhone()).append("\n")
                .append(i18nService.getMessage("mail")).append(" ").append(barber.getMail());
    }

}
