package dmit2015.restclient;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Movie {

    private String title;
    private String releaseDate;
    private String genre;
    private String rating;
    private BigDecimal price;

}
