package com.demo.app.auth.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.view.RedirectView;

@Data
@NoArgsConstructor
public class CustomerLoginResponse {

    private String jwtToken;

    private String customerName;

    public CustomerLoginResponse(String jwtToken, String customerName) {
        this.jwtToken = jwtToken;
        this.customerName = customerName;
        RedirectView redirectView = new RedirectView("/my");
    }
}
