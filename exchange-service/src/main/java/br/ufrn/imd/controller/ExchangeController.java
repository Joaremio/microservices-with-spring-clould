package br.ufrn.imd.controller;

import br.ufrn.imd.environment.InstanceInformationService;
import br.ufrn.imd.model.Exchange;
import br.ufrn.imd.repository.ExchangeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@Tag(name="Exchange Endpoints")
@RestController
public class ExchangeController {

    private Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    InstanceInformationService instanceInformationService;

    @Autowired
    ExchangeRepository exchangeRepository;


    @Operation(summary = "Get an exchange from amount of currency")
    @GetMapping(value = "/{amount}/{from}/{to}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Exchange getExchange(
            @PathVariable BigDecimal amount,
            @PathVariable String from,
            @PathVariable String to){


        logger.info("getExchange is called with -> {}, {}, and {}", amount, from, to);
        Exchange exchange = exchangeRepository.findByFromAndTo(from, to);

        if(exchange == null){
            throw new RuntimeException("Exchange not found");
        }

        BigDecimal conversionFactor = exchange.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        exchange.setConvertedValue(convertedValue.doubleValue());
        exchange.setEnvironment("PORT " + instanceInformationService.retrieveServerPort());

        return exchange;
    }
}
