package BookmyShowProject.Controllers;

import BookmyShowProject.Enums.City;
import BookmyShowProject.RequestDtos.AddTheaterRequest;
import BookmyShowProject.Service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/addTheater")
    public ResponseEntity addTheater(@RequestBody AddTheaterRequest addTheaterRequest){

        String result = theaterService.addTheater(addTheaterRequest);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @GetMapping("/show theater by city")
    public ResponseEntity show_theater_by_location(@RequestParam("city") City city){
        List<String> theaterlist=theaterService.show_theater_by_location(city);
        return new ResponseEntity<>(theaterlist,HttpStatus.FOUND);
    }






}
