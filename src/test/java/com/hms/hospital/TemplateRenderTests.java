package com.hms.hospital;

import com.hms.hospital.controller.AdminDashboardController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.ui.ExtendedModelMap;
import org.thymeleaf.TemplateEngine;

@SpringBootTest
public class TemplateRenderTests {
    @Autowired
    private AdminDashboardController adminDashboardController;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void renderAdminAppointmentsTemplate() {
        Model model = new ExtendedModelMap();
        String view = adminDashboardController.appointments(model);
        System.out.println("controller returned view=" + view);
        System.out.println("model attributes:");
        model.asMap().forEach((k,v) -> System.out.println("  " + k + " -> " +(v==null?"null":v.getClass().getName())));
        try {
            org.thymeleaf.context.Context ctx = new org.thymeleaf.context.Context();
            ctx.setVariables(model.asMap());
            String out = templateEngine.process(view, ctx);
            System.out.println("Rendered output length=" + (out!=null?out.length():0));
        } catch (Exception e) {
            System.err.println("Template processing threw exception:");
            e.printStackTrace();
            throw e;
        }
    }
}
