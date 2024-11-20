package org.example.repository;
import org.example.dto.BarberDto;
import org.example.service.I18nService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BarberRepository implements Repo<String>{
    private final JdbcTemplate jdbcTemplate;
    private final I18nService i18n;

    public BarberRepository(JdbcTemplate jdbcTemplate, I18nService i18n) {
        this.jdbcTemplate = jdbcTemplate;
        this.i18n = i18n;
    }

    StringBuilder dtoToString(BarberDto barber){
        StringBuilder sb = new StringBuilder();
        return sb.append(i18n.getMessage("name")).append(" ").append(barber.getName()).append("\n")
                .append(i18n.getMessage("surname")).append(" ").append(barber.getSurname()).append("\n")
                .append(i18n.getMessage("phone")).append(" ").append(barber.getPhone()).append("\n")
                .append(i18n.getMessage("mail")).append(" ").append(barber.getMail());
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
    public String getByArg(String arg) {
        String sql = "SELECT * FROM barber WHERE name = ?";
        var result = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BarberDto.class), arg);
        StringBuilder sb = new StringBuilder();

        for (BarberDto barber : result) {
            sb = dtoToString(barber);
        }

        return sb.toString();
    }
}
