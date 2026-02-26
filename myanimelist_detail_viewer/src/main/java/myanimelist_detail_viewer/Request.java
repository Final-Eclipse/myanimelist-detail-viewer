package myanimelist_detail_viewer;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.util.Arrays;
import java.util.HashMap;

abstract class Request
{
    private String url;
    private Document document;
    protected HashMap<String, String[]> animeDescriptorsHashMap = new HashMap<String, String[]>();

    Request(String url) throws IOException, InterruptedException
    {
        this.url = url;
        document = Jsoup.connect(url).get();
        addInformation();
    }

    // Returns the specified value associated with the key from the HashMap.
    protected String[] get(String key)
    {
        return animeDescriptorsHashMap.get(key);
    }

    // Adds all the Request information to the HashMap.
    private void addInformation()
    {
        String[][] animeDetails = getDetails();

        // Adds Request details not covered by the other getters to the HashMap.
        for (String[] animeDetail : animeDetails)
        {
            // Handles edge cases.
            if (animeDetail[0].equals("Aired") || animeDetail[0].equals("Licensors") || animeDetail[0].equals("Published"))
            {
                animeDescriptorsHashMap.put(animeDetail[0], new String[] {animeDetail[1]});
                continue;
            }
        
            animeDescriptorsHashMap.put(animeDetail[0], animeDetail[1].split(", "));
        }

        // Adds the rest of the Request details to the HashMap.
        try 
        {
            animeDescriptorsHashMap.put("Title", getTitle());
            animeDescriptorsHashMap.put("Genres", getGenres());
            animeDescriptorsHashMap.put("Themes", getThemes());
            animeDescriptorsHashMap.put("Demographics", getDemographics());
        } 
        catch (IOException e) 
        {
            System.out.println(e);
        }
    }

    // Gets details about the Request, excluding details from the other getter methods.
    private String[][] getDetails()
    {   
        Elements animeDescriptors = document.select("div.spaceit_pad");
        String[][] animeDetails = new String[0][];


        // Adds details not covered by the other getters to the array.
        for (Element descriptor : animeDescriptors)
        {
            if (isValidDescriptor(descriptor.text()))
            {
                String[] descriptors = descriptor.text().split(": ");

                String[][] newArray = Arrays.copyOf(animeDetails, animeDetails.length + 1);
                newArray[newArray.length - 1] = new String[] {descriptors[0], descriptors[1]};
                animeDetails = newArray;
            }
        }

        return animeDetails;
    }

    // Returns true only for wanted descriptors.
    private boolean isValidDescriptor(String descriptor)
    {
        String[] validDescriptors = {
            "Type",
            "Volumes",
            "Chapters",
            "Published",
            "Serialization",
            "Authors",
            "Episodes",
            "Status",
            "Aired",
            "Premiered",
            "Broadcast",
            "Producers",
            "Licensors",
            "Studios",
            "Source",
            "Duration",
            "Rating"
        };

        for (String validDescriptor : validDescriptors)
        {
            // Prevents index error in the second if statement.
            // Prevents substring from reaching an index greater than the validDescriptor's length.
            if (descriptor.length() < validDescriptor.length())
            {
                continue;
            }

            validDescriptor = validDescriptor.toLowerCase();

            if (descriptor.toLowerCase().substring(0, validDescriptor.length()).contains(validDescriptor))
            {
                return true;
            }
        }

        return false;
    }

    // Gets the Request's genres.
    private String[] getGenres() throws IOException
    {
        String[] validGenres = {
            "Action",
            "Adventure",
            "Avant Garde",
            "Award Winning",
            "Boys Love",
            "Comedy",
            "Drama",
            "Fantasy",
            "Girls Love",
            "Gourmet",
            "Horror",
            "Mystery",
            "Romance",
            "Sci-Fi",
            "Slice of Life",
            "Sports",
            "Supernatural",
            "Suspense",
            "Ecchi",
            "Erotica",
            "Hentai"
        };

        Elements genreElements = document.select("span[itemprop][style]");
        String[] animeGenres = new String[0];
       
        // Adds each genre to the array.
        for (Element genre : genreElements)
        {
            for (String validGenre : validGenres)
            {
                if (validGenre.equals(genre.text()))
                {
                    String[] newArray = Arrays.copyOf(animeGenres, animeGenres.length + 1);
                    newArray[newArray.length - 1] = genre.text();
                    animeGenres = newArray;
                    break;
                }
            }
        }
   
        return animeGenres;
    }

    // Gets the Request's themes.
    private String[] getThemes()
    {
        String[] validThemes = {
            "Adult Cast",
            "Anthropomorphic",
            "CGDCT",
            "Childcare",
            "Combat Sports",
            "Crossdressing",
            "Delinquents",
            "Detective",
            "Educational",
            "Gag Humor",
            "Gore",
            "Harem",
            "High Stakes Game",
            "Historical",
            "Idols (Female)",
            "Idols (Male)",
            "Isekai",
            "Iyashikei",
            "Love Polygon",
            "Love Status Quo",
            "Magical Sex Shift",
            "Mahou Shoujo",
            "Martial Arts",
            "Mecha",
            "Medical",
            "Memoir",
            "Military",
            "Music",
            "Mythology",
            "Organized Crime",
            "Otaku Culture",
            "Parody",
            "Performing Arts",
            "Pets",
            "Psychological",
            "Racing",
            "Reincarnation",
            "Reverse Harem",
            "Samurai",
            "School",
            "Showbiz",
            "Space",
            "Strategy Game",
            "Super Power",
            "Survival",
            "Team Sports",
            "Time Travel",
            "Urban Fantasy",
            "Vampire",
            "Video Game",
            "Villainess",
            "Visual Arts",
            "Workplace"
        };

        Elements themeElements = document.select("span[itemprop][style]");
        String[] animeThemes = new String[0];
    
        // Adds each theme to the array.
        for (Element genre : themeElements)
        {
            for (String validTheme : validThemes)
            {
                if (validTheme.equals(genre.text()))
                {
                    String[] newArray = Arrays.copyOf(animeThemes, animeThemes.length + 1);
                    newArray[newArray.length - 1] = genre.text();
                    animeThemes = newArray;
                }
            }
        }

        return animeThemes;
    }

    // Gets the Request's demographics.
    private String[] getDemographics()
    {
        String[] validDemographics = {
            "Josei",
            "Kids",
            "Seinen",
            "Shoujo",
            "Shounen"
        };

        Elements demographicElements = document.select("span[itemprop][style]");
        String[] animeDemographics = new String[0];
    
        // Adds each demographic to the array.
        for (Element genre : demographicElements)
        {
            for (String validDemographic : validDemographics)
            {
                if (validDemographic.equals(genre.text()))
                {
                    String[] newArray = Arrays.copyOf(animeDemographics, animeDemographics.length + 1);
                    newArray[newArray.length - 1] = genre.text();
                    animeDemographics = newArray;
                }
            }
        }

        return animeDemographics;
    }

    // Gets the Request's title.
    private String[] getTitle()
    {
        String mainTitle = document.select("h1.title-name").text();
        String alternateTitle = document.select("p.title-english").text();  
        
        // Will return one or both titles depending on if both exist or not.
        if (mainTitle.equals(""))
        {
            return new String[] {alternateTitle};
        }
        else if (alternateTitle.equals(""))
        {
            return new String[] {mainTitle};
        }
        else
        {
            return new String[] {mainTitle, alternateTitle};
        }
    }

    // Returns the Request's URL.
    protected String getURL()
    {
        return url;
    }
}



