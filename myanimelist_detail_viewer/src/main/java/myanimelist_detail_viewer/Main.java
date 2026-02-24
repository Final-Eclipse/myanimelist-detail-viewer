package myanimelist_detail_viewer;

import java.io.IOException;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.net.http.HttpResponse.BodyHandlers;

public class Main 
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        Request anime = new Request("https://myanimelist.net/anime/55825/Jigokuraku_2nd_Season");
        anime.getRequest();
        // anime.getGenres();
    }    
}
