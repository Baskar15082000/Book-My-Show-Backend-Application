package BookmyShowProject.Controllers;

import BookmyShowProject.Enums.City;
import BookmyShowProject.Models.ShowSeat;
import BookmyShowProject.RequestDtos.AddShowRequest;
import BookmyShowProject.RequestDtos.AddShowSeatsRequest;
import BookmyShowProject.RequestDtos.GetShowByDateDTO;
import BookmyShowProject.Service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public String addShow(@RequestBody AddShowRequest addShowRequest ){
        try{
            String result = showService.addShow(addShowRequest);
            return result;
        }
        catch (Exception e){
            return e.getMessage();
        }


    }

    @PostMapping("/createShowSeats")
    public String enableShowSeats(@RequestBody AddShowSeatsRequest addShowSeatsRequest) {

        String result = showService.createShowSeats(addShowSeatsRequest);
        return result;
    }
    @GetMapping("/Find all show by movie/date/")
    public ResponseEntity find_all_show_by_movie_date(@RequestParam("Date")LocalDate date, @RequestParam("Movie Name") String move_name, @RequestParam("City")City city){
        List<GetShowByDateDTO> allshow=showService.find_all_show_by_movie_date(date,move_name,city);
        if(allshow.isEmpty())return new ResponseEntity<>("No Show For A Day", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(allshow,HttpStatus.FOUND);
    }
    @GetMapping("/Find booked seats by show Id")
    public List<String> findbookedseats(@RequestParam("show id") int show_id ,@RequestParam("Theater id")int theater_id){
        return showService.findbookedseats(show_id,theater_id);
    }
    @GetMapping("/Find Unbooked seats by show Id")
    public List<String> findunbookedseats(@RequestParam("show id") int show_id ,@RequestParam("Theater id")int theater_id){
        return showService.findunbookedseat(show_id,theater_id);
    }






}
