package org.example.repository;

import org.example.dto.BarberDto;
import org.example.service.I18nService;
import org.example.service.LoggerConsole;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BarberRepository implements Repo<BarberDto>{
    private final JdbcTemplate jdbcTemplate;
    private final I18nService i18n;
    private final LoggerConsole logger;

    public BarberRepository(JdbcTemplate jdbcTemplate, I18nService i18n, LoggerConsole logger) {
        this.jdbcTemplate = jdbcTemplate;
        this.i18n = i18n;
        this.logger = logger;
    }

    StringBuilder dtoToString(BarberDto barber) {
        StringBuilder sb = new StringBuilder();
        return sb.append(i18n.getMessage("name")).append(" ").append(barber.getName()).append("\n")
                .append(i18n.getMessage("surname")).append(" ").append(barber.getSurname()).append("\n")
                .append(i18n.getMessage("phone")).append(" ").append(barber.getPhone()).append("\n")
                .append(i18n.getMessage("mail")).append(" ").append(barber.getMail());
    }


    @Override
    public String getByArg(String arg) {
        String sql = "SELECT * FROM barber WHERE name = ?";
        var result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BarberDto.class), arg);

        StringBuilder sb = new StringBuilder();

        for (BarberDto barber : result) {
            sb = dtoToString(barber);
        }
        return sb.toString();
    }

    @Override
    public String getAll() {
        String sql = "SELECT * FROM barber LIMIT 10";
        var result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BarberDto.class));

        StringBuilder sb = new StringBuilder();
        for (BarberDto barber : result) {
            sb.append(dtoToString(barber)).append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public boolean delete(String arg) {
        String sql = "DELETE FROM barber WHERE name = ?";
        try {
            jdbcTemplate.update(sql, arg);
            return true;
        } catch (Exception e) {
            logger.logERROR(e.toString());
            return false;
        }
    }

    @Override
    public boolean update(String newValue, String oldValue) {
        String sql = "UPDATE barber set name = ? where name = ?";
        try {
            jdbcTemplate.update(sql, newValue, oldValue);
            return true;

        } catch (Exception e) {
            logger.logERROR(e.toString());
            return false;
        }
    }

    @Override
    public boolean add(BarberDto barberDto) {
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
}
