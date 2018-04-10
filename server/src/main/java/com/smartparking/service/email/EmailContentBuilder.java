package com.smartparking.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailContentBuilder {

    @Autowired
    private TemplateEngine templateEngine;

    public String buildWelcomeHtml(String message){
        Context context = new Context();
        context.setVariable("message", message);
        return templateEngine.process("welcomeEmail.html", context);
    }
}
