package org.example.bot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.example.repository.BarbershopRepository;
import org.example.repository.CRUDRepo;
import org.springframework.stereotype.Service;

@Service("bot")
public class BarberShopBot {
    private static final Log log = LogFactory.getLog(BarberShopBot.class);
    private final I18nService i18nService;
    private final IOService ioService;
    private final LanguageService languageService;
    private final CRUDRepo<String> repository;
    private final LoggerConsole logger;

    public BarberShopBot(I18nService i18nService,
                         IOService ioService,
                         LanguageService languageService,
                         BarbershopRepository repository, LoggerConsole logger) {
        this.ioService = ioService;
        this.i18nService = i18nService;
        this.languageService = languageService;
        this.repository = repository;
        this.logger = logger;
    }

    public void conversation() {
        selectLanguage();
        String userInput;
        do {
            ioService.print("> ");
            userInput = ioService.readLine().strip().toLowerCase();
            try {
                if (userInput.equals("find-all")) {
                    findAll();
                } else if (userInput.contains("find")) {
                    findByArg(userInput);
                } else if (userInput.equals("help")) {
                    help();
                } else if(userInput.contains("lang")){
                   checkLang(userInput);
                } else {
                    unrecognizedCommand(userInput);
                }
            } catch (Exception e) {
                ioService.print("Error: " + e.getMessage());
            }

        } while (!"exit".equals(userInput));
    }

    private void checkLang(String userInput){
        try{
            var list = userInput.split(" ");
            var language = list[1];
            languageService.setCurrentLanguage(language);
            ioService.println(languageService.getCurrentLanguage());
        }catch (Exception e){
            logger.logERROR(e.toString());
            ioService.println("Selected default language");
        }
    }

    private void selectLanguage() {
        try{
            ioService.print("Select language(ru/en): ");
            var language = ioService.readLine();
            languageService.setCurrentLanguage(language);
            ioService.println(i18nService.getMessage("welcome"));
        }catch (Exception e){
            logger.logERROR(e.toString());
            ioService.println("Selected default language");
        }
    }

    private void findAll() {
        try {
            var result = repository.getAll();
            ioService.println(result);
        } catch (Exception e) {
            logger.logERROR(e.toString());
            ioService.println(e.toString());
        }
    }

    private void help() {
        ioService.println(i18nService.getMessage("helpCommand"));
    }

    private void findByArg(String userInput) {
        try {
            var list = userInput.split(" ");
            var name = list[1];
            var result = repository.getByArg(name);
            ioService.println(result.isEmpty() ? i18nService.getMessage("no-info") : result);
        } catch (Exception e) {
            logger.logERROR(e.toString());
            ioService.println(e.toString());
        }
    }

    private void unrecognizedCommand(String unregMessage) {
        ioService.println(unregMessage + ": " + i18nService.getMessage("command-not-found"));
    }
}