package com.fueltrack.platform.interfaces.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirects the legacy Swagger URL to the Springdoc Swagger UI entry point.
 */
@Controller
public class SwaggerRedirectController {

    /**
     * Redirects `/swagger-ui.html` to the Swagger UI resource.
     *
     * @return redirect instruction for the browser
     */
    @GetMapping("/swagger-ui.html")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui/index.html";
    }
}