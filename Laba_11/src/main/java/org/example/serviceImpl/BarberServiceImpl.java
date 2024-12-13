package org.example.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BarberDto;
import org.example.dto.ResponseDto;
import org.example.dto.UserDto;
import org.example.entity.Barber;
import org.example.exception.RestException;
import org.example.mapper.BarberMapper;
import org.example.repository.BarberRepository;
import org.example.exception.ResourceNotFoundException;
import org.example.service.BarberService;
import org.example.service.CRUDService;
import org.example.service.I18nService;
import org.example.service.IOService;
import org.example.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collection;

@Slf4j
@Service("barberService")
@RequiredArgsConstructor
public class BarberServiceImpl implements BarberService {

    private final IOService ioService;
    private final I18nService i18nService;
    private final BarberRepository repository;
    private final JwtUtil jwtUtil;
    private final BarberMapper mapper;

    @Override
    public Collection<BarberDto> getAll() {
        return repository.getAllBarbers()
                .stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public BarberDto getById(int id) {
        try {
            var result = repository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException(i18nService.getMessage("no-info")));

            return mapper.mapToDto(result);

        } catch (Exception e) {
            log.error("barber service getById error: " + e.getMessage());
            throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
        }
    }

    @Override
    public ResponseDto add(BarberDto data) {
        try {
            repository.save(mapper.mapToEntity(data));
            return new ResponseDto(i18nService.getMessage("succesfully-create"));
        } catch (Exception e) {
            throw new RestException(i18nService.getMessage("error"));
        }
    }

    @Override
    public ResponseDto update(BarberDto barberDto) {
        try {
            Barber barber = repository.findById(barberDto.getId()).orElseThrow(() -> new RuntimeException("Barber not found"));
            System.out.println("barber: " + barber);
            if (barberDto.getName() != null) {
                barber.setName(barberDto.getName());
            }
            if (barberDto.getSurname() != null) {
                barber.setSurname(barberDto.getSurname());
            }
            if (barberDto.getMail() != null) {
                barber.setMail(barberDto.getMail());
            }
            if (barberDto.getBirthday() != null) {
                barber.setBirthday(barberDto.getBirthday());
            }
            if (barberDto.getPhone() != null) {
                barber.setPhone(barberDto.getPhone());
            }
            if (barberDto.getPassword() != null) {
                barber.setPassword(barberDto.getPassword());
            }
            if (barberDto.getWorkExperience() != null) {
                barber.setWorkExperience(barberDto.getWorkExperience());
            }
            if (barberDto.getAuthState() != null) {
                barber.setAuthState(barberDto.getAuthState());
            }
            repository.save(barber);
            return new ResponseDto("successfully changed barber: " + barber.getName());
        } catch (Exception e) {
            throw new RestException(i18nService.getMessage("cannot-changed"));
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
    public ResponseDto login(UserDto userDto) {
        var barber = repository.findByMail(userDto.getMail());
        if(barber == null){
            throw new RestException(MessageFormat.format("User with {0} email doest exist", userDto.getMail()));
        }

        var generatedToken = jwtUtil.generateToken(userDto.getMail(), barber.getCreatedBy());
        return new ResponseDto(generatedToken);
    }

    @Override
    public ResponseDto register(BarberDto barberDto) {
        var barber = repository.findByMail(barberDto.getMail());
        if(barber != null){
            throw new RestException(MessageFormat.format("User with {0} email already exist!", barberDto.getMail()));
        }

        log.info("barber info: " + barberDto);

        repository.save(mapper.mapToEntity(barberDto));
        var generatedToken = jwtUtil.generateToken(barberDto.getMail(), barberDto.getCreatedBy());

        return new ResponseDto(generatedToken);
    }

    @Override
    public ResponseDto delete(int id) {
        try {
            repository.deleteById(id);
            return new ResponseDto(i18nService.getMessage("succesfully-delete"));
        } catch (Exception e) {
            ioService.println(e.toString());
            throw new RestException(i18nService.getMessage("cannot-delete") + " " + id);
        }
    }

    @Override
    public BarberDto getByMail(String mail) {
        try {
            var result = repository.findByMail(mail);
            if (result == null) {
                throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
            } else {
                return mapper.mapToDto(result);
            }
        } catch (Exception e) {
            log.error("barber service error: " + e.getMessage());
            throw new ResourceNotFoundException(i18nService.getMessage("no-info"));
        }
    }

}
