package com.project.app.securitys.componentSecurity;

//import com.project.shopapp.utils.WebUtils;
import com.project.app.securitys.utilSecurity.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class LocalizationUtils {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    public String getLocalizedMessage(String messageKey, Object... params) {//spread operator
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = this.localeResolver.resolveLocale(request);

        return this.messageSource.getMessage(messageKey, params, locale);
    }
}
