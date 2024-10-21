package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.Services;
import org.example.repository.ServiceRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceCRUD implements CRUDService<String> {

    private final ServiceRepository repository;
    private final I18nService i18n;
    private final IOService io;

    @Override
    public String getAll() {
        var result = repository.findAll();
        StringBuilder sb = new StringBuilder();
        for (Services service : result) {
            sb.append(entityToString(service)).append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public String get(String name) {
        var result = repository.getServiceByName(name);
        if(result == null){
            return i18n.getMessage("no-info");
        }
        return entityToString(result).toString();
    }

    @Override
    public String add(String data) {
        io.print(i18n.getMessage("enter-service-price"));
        try{
            var price = io.readLine();
            var service = new Services();
            service.setName(data);
            service.setPrice(Integer.parseInt(price));
            repository.save(service);
            return i18n.getMessage("service-create");
        }catch (Exception e){
            return i18n.getMessage("error") + "\n" + e.getMessage();
        }

    }

    @Override
    public String update(String name) {
        io.print("Введите новую цену: ");
        try{
            var price =  Integer.parseInt( io.readLine());
            var result = repository.updateService(price, name);
            return result == 1 ? i18n.getMessage("service-update-success") : i18n.getMessage("service-update-error");
        }catch (Exception e){
            return i18n.getMessage("service-update-error") + "\n" + e.getMessage();
        }

    }

    @Override
    public String delete(String data) {
        var result = repository.deleteService(data);
        return result == 1 ? i18n.getMessage("service-delete") : i18n.getMessage("error");
    }

    StringBuilder entityToString(Services services) {
        StringBuilder sb = new StringBuilder();
        return sb.append("id: ").append(" ").append(services.getId()).append("\n")
                .append(i18n.getMessage("service-name")).append(services.getName()).append("\n")
                .append(i18n.getMessage("service-price")).append(services.getPrice());
    }
}
