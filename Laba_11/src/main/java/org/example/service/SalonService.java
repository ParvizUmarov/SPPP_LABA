package org.example.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.ResponseDto;
import org.example.dto.SalonDto;
import org.example.entity.Barber;
import org.example.entity.Salon;
import org.example.exception.ResourceNotFoundException;
import org.example.exception.RestException;
import org.example.mapper.BarberMapper;
import org.example.mapper.SalonMapper;
import org.example.repository.SalonRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SalonService {

    List<SalonDto> getAllSalons();

    ResponseDto add(SalonDto salonDto);

    ResponseDto update(SalonDto salonDto);

    ResponseDto delete(int id);

    @Slf4j
    @Service
    @RequiredArgsConstructor
    class Base implements SalonService {

        final SalonRepository salonRepository;
        final BarberService barberService;
        final SalonMapper salonMapper;
        final BarberMapper barberMapper;

        @Override
        public List<SalonDto> getAllSalons() {
            return salonRepository
                    .findAll()
                    .stream()
                    .map(salonMapper::mapToDto)
                    .toList();
        }

        @Override
        public ResponseDto add(SalonDto salonDto) {
            var barber = getBarber();

            var mappedToEntity = salonMapper.mapToEntity(salonDto);
            mappedToEntity.setBarber(barber);
            var result = salonRepository.save(mappedToEntity);

            return new ResponseDto("Salon is add successfully, id: " + result.getId());
        }

        @Override
        public ResponseDto update(SalonDto salonDto) {
            var salon = new Salon();

            var findSalon = salonRepository
                    .findById(salonDto.getId() == null ? 0 : salonDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Salon not found!"));

            salon.setId(salonDto.getId());
            if(findSalon.getBarber() == null)
                throw new ResourceNotFoundException("Permission denied");

            salon.setBarber(barberMapper.mapToEntity(barberService.getById(findSalon.getBarber().getId())));

            isOwner(salon.getBarber() == null ? 0 : salon.getBarber().getId());

            if(salonDto.getAddress() != null){
                salon.setAddress(salonDto.getAddress());
            }else{
                throw new RestException("Field 'address' cannot be null");
            }

            if(salonDto.getLongitude() != null)
                salon.setLongitude(salonDto.getLongitude());

            if(salonDto.getLatitude() != null)
                salon.setLatitude(salonDto.getLatitude());

            if(salonDto.getImages() != null)
                salon.setImages(salonDto.getImages());

            salonRepository.save(salon);

            return new ResponseDto("Salon with id <"+ salon.getId()+"> updated successfully");
        }

        @Override
        public ResponseDto delete(int id) {
            var salon = salonRepository
                    .findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Salon with that id < " + id + " > doesnt exist"));

            isOwner(salon.getBarber() == null ? 0 : salon.getBarber().getId());

            salonRepository.deleteById(id);
            return new ResponseDto("Salon with id <" + id+ "> is delete successfully");
        }

        private void isOwner(Integer ownerId){
            var barber = getBarber();
            if(!ownerId.equals(barber.getId()))
                throw new RestException("Permission denied");
        }

        private Barber getBarber() {
            var mail = SecurityContextHolder.getContext().getAuthentication().getName();
            var barber = barberService.getByMail(mail);

            if (barber == null) throw new RestException("Barber " + mail + " doesnt exist");

            return barberMapper.mapToEntity(barber);
        }
    }
}