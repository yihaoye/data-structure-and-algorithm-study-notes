public interface Search {
    public List<Movie> searchByTitle(String title);
    public List<Movie> searchByLanguage(String language);
    public List<Movie> searchByGenre(String genre);
    public List<Movie> searchByReleaseDate(Date relDate);
    public List<Movie> searchByCity(String cityName);
}
  
public class Catalog implements Search {
     HashMap<String, List<Movie>> movieTitles;
     HashMap<String, List<Movie>> movieLanguages;
     HashMap<String, List<Movie>> movieGenres;
     HashMap<Date, List<Movie>> movieReleaseDates;
     HashMap<String, List<Movie>> movieCities;
  
    public List<Movie> searchByTitle(String title) {
      return movieTitles.get(title);
    }
  
    public List<Movie> searchByLanguage(String language) {
      return movieLanguages.get(language);
    }
  
    //...
  
    public List<Movie> searchByCity(String cityName) {
      return movieCities.get(cityName);
    }
}