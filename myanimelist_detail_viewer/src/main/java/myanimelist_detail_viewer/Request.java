package myanimelist_detail_viewer;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.Arrays;

// https://jsoup.org/cookbook/extracting-data/selector-syntax
// https://www.youtube.com/watch?v=riZ2GAaMDGM

// Convert animeDescriptorsArray to HashMap.
public class Request
{
    String url;
    Document document;

    Request(String url) throws IOException, InterruptedException
    {
        this.url = url;
        document = Jsoup.connect(url).get();
    }

    public void getRequest()
    {   
        Elements animeDescriptors = document.select("div.spaceit_pad");
        String[] animeDescriptorsArray = new String[0];

        for (Element descriptor : animeDescriptors)
        {   
            
            if (isValidDescriptor(descriptor.text()) == true)
            {
                String[] tempArray = Arrays.copyOf(animeDescriptorsArray, animeDescriptorsArray.length + 1);
                int lastIndex = tempArray.length - 1;

                tempArray[lastIndex] = descriptor.text();
                animeDescriptorsArray = tempArray;
            }
        }

        // Tries to remove double genres.
        // for (int x = 0; x < animeDescriptorsArray.length; x += 1)
        // {
        //     if (animeDescriptorsArray[x].contains("Action".repeat(2)))
        //     {
        //         animeDescriptorsArray[x] = "\naa\n";
        //     }
        // }

        for (String desc : animeDescriptorsArray)
        {
            System.out.println(desc);
        }
    }

    private boolean isValidDescriptor(String descriptor)
    {
        String[] validDescriptors = {
            "Type",
            "Episodes",
            "Status",
            "Aired",
            "Premiered",
            "Broadcast",
            "Producers",
            "Licensors",
            "Studios",
            "Source",
            "Genre",
            "Genres",
            "Theme",
            "Themes",
            "Demographic",
            "Duration",
            "Rating"
        };

        for (String description : validDescriptors)
        {
            if (descriptor.length() < description.length())
            {
                return false;
            }

            else if (descriptor.toLowerCase().substring(0, description.length()).contains(description.toLowerCase()))
            {
                return true;
            }
        }

        return false;
    }

    // When getting the genres, each genre is doubled due to how the html is formatted.
    // Remove the double genre to just a single one for each genre.
    // Then, return each genre as an array perhaps.
    private String removeDoubleGenre(Element descriptor)
    {
        if (descriptor.text().toLowerCase().contains("genres"))
        {
            String[] genre = descriptor.text().split(" ");
            genre[1] = genre[1].substring(genre[1].length() / 2);
            return "Genres: " + genre[1];
        }
        else if (descriptor.text().toLowerCase().contains("genre"))
        {
            String[] genre = descriptor.text().split(" ");
            genre[1] = genre[1].substring(genre[1].length() / 2);
            return "Genre: " + genre[1];
        }

        return descriptor.text();
    }

    protected String[] getGenres() throws IOException
    {
        Elements genres = document.select("span[itemprop][style]");
        String[] animeGenres = new String[0];
       
        for (Element genre : genres)
        {
            if (genre.attr("itemprop").equals("ratingCount"))
            {
                continue;
            }

            String[] newArray = Arrays.copyOf(animeGenres, animeGenres.length + 1);
            newArray[newArray.length - 1] = genre.text();
            animeGenres = newArray;
        }

        return animeGenres;
    }

    private void getDemographics()
    {

    }
}
