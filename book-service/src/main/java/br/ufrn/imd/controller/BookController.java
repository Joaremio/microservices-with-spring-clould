package br.ufrn.imd.controller;


import br.ufrn.imd.dto.Exchange;
import br.ufrn.imd.environment.InstanceInformationService;
import br.ufrn.imd.model.Book;
import br.ufrn.imd.proxy.ExchangeProxy;
import br.ufrn.imd.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name="Book Endpoint")
@RestController
public class BookController {

    @Autowired
    private InstanceInformationService instanceInformationService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExchangeProxy proxy;

    /*@GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook (
            @PathVariable Long id,
            @PathVariable String currency
    ){
        String port = instanceInformationService.retrieveServerPort();

        var book = bookRepository.findById(id).orElseThrow();

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate()
                .getForEntity("http://localhost:8000/exchange-service/{amount}/{from}/{to}", Exchange.class, params);

        Exchange exchange = response.getBody();


        book.setEnvironment(port);
        book.setPrice(exchange.getConvertedValue().doubleValue());
        book.setCurrency(currency);

        return book;
    }*/

    @Operation(summary = "Find a specific book by your ID")
    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook (
            @PathVariable Long id,
            @PathVariable String currency
    ){
        String port = instanceInformationService.retrieveServerPort();

        var book = bookRepository.findById(id).orElseThrow();


        Exchange exchange = proxy.getExchange(book.getPrice(),"USD",currency);


        book.setEnvironment("BOOK PORT: " + port  + " EXCHANGE PORT: " + exchange.getEnvironment());
        book.setPrice(exchange.getConvertedValue().doubleValue());
        book.setCurrency(currency);

        return book;
    }

    /*@GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook (
            @PathVariable Long id,
            @PathVariable String currency
    ){
        String port = instanceInformationService.retrieveServerPort();

        var book = bookRepository.findById(id).orElseThrow();

        book.setEnvironment(port);
        book.setCurrency(currency);

        return book;
    }*/
}
