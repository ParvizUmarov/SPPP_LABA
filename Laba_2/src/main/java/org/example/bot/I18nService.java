package org.example.bot;

import java.util.Map;

public interface I18nService {
    String getMessage(String code);
    String getMessage(String code, Map<String, Object> params);
}
