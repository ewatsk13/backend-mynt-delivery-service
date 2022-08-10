package com.esb.myntdeliveryservice.presentation.rest;

import com.esb.myntdeliveryservice.core.dto.CostOfDeliveryDto;
import com.esb.myntdeliveryservice.core.service.interfaces.DeliveryService;
import com.esb.myntdeliveryservice.presentation.dto.DeliveryRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.esb.myntdeliveryservice.presentation.dto.mapper.DtoMapper.toDeliveryDto;

@RestController
@RequestMapping(value = "/delivery")
@Slf4j
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping(value = "/calculate-cost", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CostOfDeliveryDto> calculateDeliveryCost(@RequestBody DeliveryRequest request) {
        log.info("### Calculate delivery cost started.");
        CostOfDeliveryDto result = deliveryService.calculateCostOfDelivery(toDeliveryDto(request));
        log.info("### Calculate delivery cost ended. Cost: {}", result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
