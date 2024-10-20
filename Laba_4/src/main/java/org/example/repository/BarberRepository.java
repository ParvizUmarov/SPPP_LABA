package org.example.repository;

import org.example.dto.BarberDto;
import org.example.service.I18nService;
import org.example.service.LoggerConsole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BarberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final I18nService i18n;
    private final LoggerConsole logger;

    public BarberRepository(JdbcTemplate jdbcTemplate, I18nService i18n, LoggerConsole logger) {
        this.jdbcTemplate = jdbcTemplate;
        this.i18n = i18n;
        this.logger = logger;
    }

    public String getAllBarber() {
        String sql = "SELECT * FROM barber LIMIT 10";
        var result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BarberDto.class));

        StringBuilder sb = new StringBuilder();
        for (BarberDto barber : result) {
            sb.append(dtoToString(barber)).append("\n\n");
        }
        return sb.toString();
    }

    public String getUserByName(String name) {
        var result = checkBarberExist(name);
        StringBuilder sb = new StringBuilder();

        for (BarberDto barber : result) {
            sb = dtoToString(barber);
        }
        return sb.toString();
    }

    public List<BarberDto> checkBarberExist(String name) {
        String sql = "SELECT * FROM barber WHERE name = ?";
        var result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BarberDto.class), name);
        return result;
    }

    public boolean addBarber(BarberDto barberDto) {
        String sql = "INSERT INTO barber (name, surname, birthday, phone, mail, work_experience, password, auth_state, salon_id, service_id)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
        try {
            jdbcTemplate.update(sql,
                    barberDto.getName(),
                    barberDto.getSurname(),
                    barberDto.getBirthday(),
                    barberDto.getPhone(),
                    barberDto.getMail(),
                    barberDto.getWorkExperience(),
                    barberDto.getPassword(),
                    barberDto.getAuthState(),
                    barberDto.getSalonId(),
                    barberDto.getServiceId());
            return true;
        } catch (Exception e) {
            logger.logERROR(e.toString());
            return false;
        }
    }

    public boolean deleteBarber(String name) {
        String sql = "DELETE FROM barber WHERE name = ?";
        try {
            jdbcTemplate.update(sql, name);
            return true;
        } catch (Exception e) {
            logger.logERROR(e.toString());
            return false;
        }
    }

    public boolean updateBarber(String newName, String oldName) {
        String sql = "UPDATE barber set name = ? where name = ?";
        try {
            jdbcTemplate.update(sql, newName, oldName);
            return true;

        } catch (Exception e) {
            logger.logERROR(e.toString());
            return false;
        }
    }


    StringBuilder dtoToString(BarberDto barber) {
        StringBuilder sb = new StringBuilder();
        return sb.append(i18n.getMessage("name")).append(" ").append(barber.getName()).append("\n")
                .append(i18n.getMessage("surname")).append(" ").append(barber.getSurname()).append("\n")
                .append(i18n.getMessage("phone")).append(" ").append(barber.getPhone()).append("\n")
                .append(i18n.getMessage("mail")).append(" ").append(barber.getMail());
    }


}
