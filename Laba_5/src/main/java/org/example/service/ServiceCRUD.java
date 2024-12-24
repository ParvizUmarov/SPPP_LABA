package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Services;
import org.example.repository.ServiceRepo;
import org.springframework.stereotype.Service;

public interface ServiceCRUD {

    String getAll();
    String get(String name);
    String add(String name);
    String update(String name);
    String delete(String name);

    @Service
    @RequiredArgsConstructor
    @Slf4j
    class Base implements ServiceCRUD {

        private final ServiceRepo repository;
        private final I18nService i18n;
        private final IOService io;

        @Override
        public String getAll() {
            try {
                var result = repository.getAll();
                StringBuilder sb = new StringBuilder();
                for (Services service : result) {
                    sb.append(entityToString(service)).append("\n\n");
                }
                return sb.toString();
            } catch (Exception e) {
                return i18n.getMessage("error") + "\n" + e.getMessage();
            }
        }

        @Override
        public String get(String name) {
            try {
                var result = repository.getByArg(name);
                if (result == null) {
                    return i18n.getMessage("no-info");
                }
                return entityToString(result).toString();
            } catch (Exception e) {
                return i18n.getMessage("no-info");
            }

        }

        @Override
        public String add(String data) {
            io.print(i18n.getMessage("enter-service-price"));
            try {
                var price = io.readLine();
                var service = new Services();
                service.setName(data);
                service.setPrice(Integer.parseInt(price));
                repository.add(service);
                return i18n.getMessage("service-create");
            } catch (Exception e) {
                return i18n.getMessage("error") + "\n" + e.getMessage();
            }

        }

        @Override
        public String update(String name) {
            io.print("Введите новую цену: ");
            try {
                var newPrice = Integer.parseInt(io.readLine());
                repository.update(newPrice, name);
                return i18n.getMessage("service-update-success");
            } catch (Exception e) {
                return i18n.getMessage("service-update-error") + "\n" + e.getMessage();
            }

        }

        @Override
        public String delete(String data) {
            try {
                repository.delete(data);
                return  i18n.getMessage("service-delete");
            } catch (Exception e) {
                return i18n.getMessage("error") + "\n" + e.getMessage();
            }
        }

        StringBuilder entityToString(Services services) {
            StringBuilder sb = new StringBuilder();
            return sb.append("id: ").append(" ").append(services.getId()).append("\n")
                    .append(i18n.getMessage("service-name")).append(services.getName()).append("\n")
                    .append(i18n.getMessage("service-price")).append(services.getPrice());
        }
    }

}
