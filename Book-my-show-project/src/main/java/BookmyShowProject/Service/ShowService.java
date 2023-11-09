package BookmyShowProject.Service;

import BookmyShowProject.Enums.City;
import BookmyShowProject.Enums.SeatType;
import BookmyShowProject.Exception.showalreadyavailable;
import BookmyShowProject.Models.Movie;
import BookmyShowProject.Models.Show;
import BookmyShowProject.Models.ShowSeat;
import BookmyShowProject.Models.Theater;
import BookmyShowProject.Models.TheaterSeat;
import BookmyShowProject.Repository.MovieRepository;
import BookmyShowProject.Repository.ShowRepository;
import BookmyShowProject.Repository.ShowSeatRespository;
import BookmyShowProject.Repository.TheaterRepository;
import BookmyShowProject.RequestDtos.AddShowRequest;
import BookmyShowProject.RequestDtos.AddShowSeatsRequest;
import BookmyShowProject.RequestDtos.GetShowByDateDTO;
import BookmyShowProject.Transformers.ShowTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRespository showSeatRespository;

    public String addShow(AddShowRequest addShowRequest) throws Exception{

        //Goal is to set the attributes of the Show Entity and save it to db.

        Show show = ShowTransformers.convertAddRequestToEntity(addShowRequest);
        Show findshow =showRepository.findShowByDateAndTime(show.getShowDate(),show.getShowTime(),addShowRequest.getTheaterId());
        if(findshow!=null){
            throw new showalreadyavailable("THE SHOW ALREADY AVAILABLE FOR THE DATE "+show.getShowDate()+" TIME "+show.getShowTime() );
        }
        Movie movie = movieRepository.findMovieByMovieName(addShowRequest.getMovieName());

        Optional<Theater> optionalTheater = theaterRepository.findById(addShowRequest.getTheaterId());

        Theater theater = optionalTheater.get();

        //Setting the FK values
        show.setMovie(movie);
        show.setTheater(theater);

        //Setting the bidirectional mapping
        theater.getShowList().add(show);
        movie.getShowList().add(show);

        show = showRepository.save(show);

        return "The show for the day "+show.getShowDate()+" and show time "+show.getShowTime()+" and show name "+show.getMovie().getMovieName()+" add successful";

    }

    public String createShowSeats(AddShowSeatsRequest showSeatsRequest){

        //I need to create the show Seats and save to the DB.

        Show show = showRepository.findById(showSeatsRequest.getShowId()).get();
        Theater theater = show.getTheater();
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = new ArrayList<>();


        for(TheaterSeat theaterSeat:theaterSeatList) {

            ShowSeat showSeat = ShowSeat.builder()
                    .seatNo(theaterSeat.getSeatNo())
                    .seatType(theaterSeat.getSeatType())
                    .isAvailable(true)
                    .isFoodAttached(false)
                    .show(show)
                    .build();

            if(theaterSeat.getSeatType().equals(SeatType.CLASSIC)){
                showSeat.setCost(showSeatsRequest.getPriceOfClassicSeats());
            }
            else{
                showSeat.setCost(showSeatsRequest.getPriceOfPremiumSeats());
            }

            showSeatList.add(showSeat);
        }

        show.setShowSeatList(showSeatList);

        //Either save parent or save child

        //child is alot of seats (you need to save that list)

        showRepository.save(show);
        return "The show seats have been added";

    }
    public List<GetShowByDateDTO> find_all_show_by_movie_date(LocalDate date, String move_name, City city){
        List<Show> findallshow=showRepository.find_all_show_by_movie_date(date);
        List<GetShowByDateDTO> findallbydateandmove=new ArrayList<>();
        for(Show show:findallshow){
            if(show.getMovie().getMovieName().equals(move_name) && show.getTheater().getCity().equals(city)){
                GetShowByDateDTO getshowbydatedto = GetShowByDateDTO.builder()
                        .MovieName(move_name)
                        .TheaterName(show.getTheater().getName())
                        .ShowTime(show.getShowTime())
                        .City(city)
                        .Address(show.getTheater().getAddress()).build();
                findallbydateandmove.add(getshowbydatedto);

            }

        }


        return findallbydateandmove;
    }


    public List<String> findbookedseats(int show_id , int theater_id){
        List<String> seats=new ArrayList<>();
        Show show = showRepository.findById(show_id).get();
        for(ShowSeat showSeat:show.getShowSeatList()){
            if(!showSeat.isAvailable()){

                seats.add(showSeat.getSeatNo()+" "+"Seat Tye ="+showSeat.getSeatType());
            }
        }
        return seats;


    }
    public List<String> findunbookedseat(int show_id , int theater_id){
        List<String> seats=new ArrayList<>();
        Show show = showRepository.findById(show_id).get();
        for(ShowSeat showSeat:show.getShowSeatList()){
            if(showSeat.isAvailable()){

                seats.add(showSeat.getSeatNo()+" "+"Seat Tye ="+showSeat.getSeatType());
            }
        }
        return seats;


    }





}
