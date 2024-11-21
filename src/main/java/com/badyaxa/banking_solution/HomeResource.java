package com.badyaxa.banking_solution;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.ZoneId;

@Slf4j
@RestController
public class HomeResource {

    @GetMapping("/")
    public String index() {
        return "Local Time Is  <b>" + LocalTime.now(ZoneId.of("UTC+2")) + "(UTC+2)</b>"
                + "<br>"
                + "<a href=\"/actuator/health\" target=\"_blank\">actuator/health</a>"
                + "<br>"
                + "<a href=\"/api\" target=\"_blank\">swagger-ui</a>";
    }
}
