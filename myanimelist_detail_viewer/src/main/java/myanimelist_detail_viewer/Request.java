package myanimelist_detail_viewer;
// import java.net.http.HttpClient;

import java.io.IOException;

// import javax.swing.text.html.parser.Element;
// import java.net.URI;
// import java.net.http.HttpClient;
// import java.net.http.HttpRequest;
// import java.net.http.HttpResponse;
// import java.net.http.HttpResponse.BodyHandlers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.util.Arrays;

// https://jsoup.org/cookbook/extracting-data/selector-syntax
// https://www.youtube.com/watch?v=riZ2GAaMDGM

public class Request
{
    public static void getRequest() throws IOException, InterruptedException
    {
        // HttpClient client = HttpClient.newHttpClient();
        // HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://myanimelist.net/anime/59978/Sousou_no_Frieren_2nd_Season")).GET().build();
        // HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        // System.out.println(response.body());
        
        String url = "https://myanimelist.net/anime/59978/Sousou_no_Frieren_2nd_Season";
        Document document = Jsoup.connect(url).get();
        Elements animeDescriptors = document.select("div.spaceit_pad");
        
        String[] animeDescriptorsArray = new String[0];

        for (Element descriptor : animeDescriptors)
        {   
            
            if (isValidDescriptor(descriptor.text()) == true)
            {
                // System.out.println(descriptor.text());
                String[] tempArray = Arrays.copyOf(animeDescriptorsArray, animeDescriptorsArray.length + 1);
                int lastIndex = tempArray.length - 1;

                
                tempArray[lastIndex] = removeDoubleGenre(descriptor);
           
                animeDescriptorsArray = tempArray;
            }
        }

        // System.out.println(Arrays.toString(animeDescriptorsArray));
        for (String desc : animeDescriptorsArray)
        {
            // Only adventure genre is printed right now.
            // Fix in removeDoubleGenre().
            System.out.println(desc);
        }

        // System.out.println(animeDescriptorsArray.length);
    }

    private static boolean isValidDescriptor(String descriptor)
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
            "Genres",
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
    private static String removeDoubleGenre(Element descriptor)
    {
        if (descriptor.text().toLowerCase().contains("genre"))
        {
            // System.out.println(descriptor.text());

            String[] genre = descriptor.text().split(" ");
            genre[1] = genre[1].substring(genre[1].length() / 2);
            return genre[1];
        }

        return descriptor.text();
    }
}
