package BookmyShowProject.Controllers;

import BookmyShowProject.RequestDtos.BookTicketRequest;
import BookmyShowProject.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/book Ticket")
    private String bookTicket(@RequestBody BookTicketRequest bookTicketRequest ) {

        String result  = ticketService.bookTicket(bookTicketRequest);
        return result;

    }
    @DeleteMapping("/cancel Ticket")
    private String cancel_ticket(@RequestBody BookTicketRequest cancelTicketRequest ,@RequestParam("Ticket Id") int id){
        String result = ticketService.cancel_ticket(cancelTicketRequest,id);
        return result;
    }

}
