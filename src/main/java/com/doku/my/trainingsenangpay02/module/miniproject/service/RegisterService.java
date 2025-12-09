package com.doku.my.trainingsenangpay02.module.miniproject.service;

import com.doku.my.trainingsenangpay02.module.miniproject.controller.converter.SnapHttpHeaders;
import com.doku.my.trainingsenangpay02.module.miniproject.dto.CreateVaRequest;
import com.doku.my.trainingsenangpay02.module.miniproject.dto.CreateVaResponse;
import com.doku.my.trainingsenangpay02.module.miniproject.entity.MerchantStatus;
import com.doku.my.trainingsenangpay02.module.miniproject.entity.Register;
import com.doku.my.trainingsenangpay02.module.miniproject.enums.SnapResponse;
import com.doku.my.trainingsenangpay02.module.miniproject.exception.MerchantException;
import com.doku.my.trainingsenangpay02.module.miniproject.model.Srw;
import com.doku.my.trainingsenangpay02.module.miniproject.repository.MerchantRepository;
import com.doku.my.trainingsenangpay02.module.miniproject.repository.RegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author Daniel Joi Partogi Hutapea
 */
@RequiredArgsConstructor
@Service
public class RegisterService
{
    private final MerchantRepository merchantRepository;
    private final RegisterRepository registerRepository;

    public Srw<CreateVaResponse> createVa(SnapHttpHeaders snapHttpHeaders, CreateVaRequest request)
    {
        var clientId = snapHttpHeaders.getXPartnerId();

        var merchant = merchantRepository.findFirstByClientIdAndStatus(clientId, MerchantStatus.ACTIVE)
            .orElseThrow(() -> new MerchantException(SnapResponse.INVALID_MERCHANT));

        var register = Register.builder()
            .merchant(merchant)
            .invoiceNumber(request.getInvoiceNumber())
            .virtualAccountNumber(request.getVirtualAccountNo())
            .virtualAccountName(request.getVirtualAccountName())
            .virtualAccountEmail(request.getVirtualAccountEmail())
            .virtualAccountPhone(request.getVirtualAccountPhone())
            .amount(new BigDecimal(request.getAmount().getValue()))
            .currency(request.getAmount().getCurrency())
            .additionalInfo(request.getAdditionalInfo())
            .status(MerchantStatus.ACTIVE)
            .build();

        registerRepository.save(register);

        var snapResponse = SnapResponse.SUCCESSFUL;

        return Srw.<CreateVaResponse>builder()
            .httpStatus(snapResponse.getHttpStatus())
            .body(CreateVaResponse.builder()
                .responseCode(snapResponse.buildResponseCode())
                .responseMessage(snapResponse.getResponseMessage())
                .virtualAccountData(CreateVaResponse.VirtualAccountData.builder()
                    .invoiceNumber(request.getInvoiceNumber())
                    .virtualAccountNo(request.getVirtualAccountNo())
                    .virtualAccountName(request.getVirtualAccountName())
                    .virtualAccountEmail(request.getVirtualAccountEmail())
                    .virtualAccountPhone(request.getVirtualAccountPhone())
                    .amount(request.getAmount())
                    .additionalInfo(request.getAdditionalInfo())
                    .build()
                )
                .build()
            )
            .build();
    }
}
