package myanimelist_detail_viewer;

import java.io.IOException;
import java.util.Arrays;

public class Main 
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        run();
    }    

    private static void run() throws IOException, InterruptedException
    {
        String[] animeURLs = {
            "https://myanimelist.net/anime/52991/Sousou_no_Frieren",
            "https://myanimelist.net/anime/59978/Sousou_no_Frieren_2nd_Season",
            "https://myanimelist.net/anime/5114/Fullmetal_Alchemist__Brotherhood",
            "https://myanimelist.net/anime/57555/Chainsaw_Man_Movie__Reze-hen",
            "https://myanimelist.net/anime/9253/Steins_Gate",
            "https://myanimelist.net/anime/38524/Shingeki_no_Kyojin_Season_3_Part_2",
            "https://myanimelist.net/anime/39486/Gintama__The_Final",
            "https://myanimelist.net/anime/28977/Gintama%C2%B0",
            "https://myanimelist.net/anime/11061/Hunter_x_Hunter_2011",
            "https://myanimelist.net/anime/820/Ginga_Eiyuu_Densetsu"
        };

        Anime[] animes = new Anime[0];
        
        // Creates Anime objects and adds to array.
        for (String animeURL : animeURLs)
        {
            Anime anime = new Anime(animeURL);
            Anime[] newArray = Arrays.copyOf(animes, animes.length + 1);
            newArray[newArray.length - 1] = anime;
            animes = newArray;
        }

        // Prints anime details.
        for (Request anime : animes)
        {
            System.out.println(anime.toString());
            System.out.println();
        }
    }
}
