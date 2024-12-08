package org.example.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.entity.Barber;
import org.example.repository.BarberRepository;
import org.example.exception.ResourceNotFoundException;
import org.example.service.BarberService;
import org.example.service.CRUDService;
import org.example.service.I18nService;
import org.example.service.IOService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service("barberService")
@RequiredArgsConstructor
public class BarberServiceImpl implements BarberService {

    private final IOService ioService;
    private final I18nService i18nService;
    private final BarberRepository repository;

    @Override
    public Collection<BarberDto> getAll() {
        return repository.getAllBarbers()
                .stream()
                .map(BarberServiceImpl::mapToDto)
                .toList();
    }

    @Override
    public BarberDto getById(int id) {
        try {
            var result = repository.findById(id);
            if (result == null) {
                throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
            } else {
                return mapToDto(result.get());
            }
        } catch (Exception e) {
            log.error("barber service error: " + e.getMessage());
            throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
        }
    }

    @Override
    public ResponseDto add(BarberDto data) {
        try {
            repository.save(mapToEntity(data));
            return new ResponseDto(i18nService.getMessage("succesfully-create"));
        } catch (Exception e) {
            return new ResponseDto(i18nService.getMessage("error"));
        }
    }

    @Override
    public ResponseDto update(BarberDto barberDto) {
        try {
            Barber barber = repository.findById(barberDto.getId()).orElseThrow(() -> new RuntimeException("Barber not found"));
            System.out.println("barber: " + barber);
            if(barberDto.getName() != null){barber.setName(barberDto.getName());}
            if(barberDto.getSurname() != null){barber.setSurname(barberDto.getSurname());}
            if(barberDto.getMail() != null){barber.setMail(barberDto.getMail());}
            if(barberDto.getBirthday() != null){barber.setBirthday(barberDto.getBirthday());}
            if(barberDto.getPhone() != null){barber.setPhone(barberDto.getPhone());}
            if(barberDto.getPassword() != null){barber.setPassword(barberDto.getPassword());}
            if(barberDto.getWorkExperience() != null){barber.setWorkExperience(barberDto.getWorkExperience());}
            if(barberDto.getAuthState() != null){barber.setAuthState(barberDto.getAuthState());}
            repository.save(barber);
            return new ResponseDto("successfully changed barber: " + barber.getName());
        } catch (Exception e) {
            return new ResponseDto(i18nService.getMessage("cannot-changed"));
        }

    }

    @Override
    public boolean isOwner(int barberId) {
        log.debug("called isOwner");
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("currentUsername: " + currentUsername);
        BarberDto barber = getById(barberId);
        log.debug("barber: " + barber);
        boolean isOwner = barber != null && barber.getCreatedBy().equals(currentUsername);
        log.debug("isOwner: " + isOwner);
        return isOwner;
    }

    @Override
    public ResponseDto delete(int id) {
        try {
            repository.deleteById(id);
            return new ResponseDto(i18nService.getMessage("succesfully-delete"));
        } catch (Exception e) {
            ioService.println(e.toString());
            return new ResponseDto(i18nService.getMessage("cannot-delete") + " " + id);
        }
    }

    @Override
    public BarberDto getByMail(String mail) {
        try {
            var result = repository.findByMail(mail);
            if (result == null) {
                throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
            } else {
                return mapToDto(result);
            }
        } catch (Exception e) {
            log.error("barber service error: " + e.getMessage());
            throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
        }
    }

    private static Barber mapToEntity(BarberDto barberDto) {
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
        barber.setCreatedBy(barberDto.getCreatedBy());
        return barber;
    }

    private static BarberDto mapToDto(Barber barber) {
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
        barberDto.setCreatedBy(barber.getCreatedBy());
        return barberDto;
    }

}
