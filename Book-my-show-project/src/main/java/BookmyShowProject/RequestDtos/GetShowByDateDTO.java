package BookmyShowProject.RequestDtos;

import BookmyShowProject.Enums.City;
import lombok.*;

import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class GetShowByDateDTO {
   private String TheaterName;
   private String MovieName;
   private LocalTime ShowTime;
   private City City;
   private String Address;
}
