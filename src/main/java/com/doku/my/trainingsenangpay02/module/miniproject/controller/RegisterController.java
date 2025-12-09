package com.doku.my.trainingsenangpay02.module.miniproject.controller;

import com.doku.my.trainingsenangpay02.module.miniproject.controller.converter.SnapHttpHeaders;
import com.doku.my.trainingsenangpay02.module.miniproject.controller.converter.SnapRequestHeader;
import com.doku.my.trainingsenangpay02.module.miniproject.dto.CreateVaRequest;
import com.doku.my.trainingsenangpay02.module.miniproject.dto.CreateVaResponse;
import com.doku.my.trainingsenangpay02.module.miniproject.entity.Merchant;
import com.doku.my.trainingsenangpay02.module.miniproject.entity.MerchantStatus;
import com.doku.my.trainingsenangpay02.module.miniproject.service.MerchantService;
import com.doku.my.trainingsenangpay02.module.miniproject.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Joi Partogi Hutapea
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/register")
public class RegisterController
{
    private final RegisterService registerService;

    @PostMapping(value = "/create-va")
    public ResponseEntity<CreateVaResponse> createVa
    (
        @SnapRequestHeader SnapHttpHeaders snapHttpHeaders,
        @Validated @RequestBody CreateVaRequest request
    )
    {
        var srw = registerService.createVa(snapHttpHeaders, request);

        return ResponseEntity
            .status(srw.getHttpStatus())
            .headers(srw.getHttpHeaders())
            .body(srw.getBody());
    }
}
