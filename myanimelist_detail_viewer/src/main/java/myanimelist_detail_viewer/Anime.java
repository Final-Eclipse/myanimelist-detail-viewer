package myanimelist_detail_viewer;

import java.io.IOException;
import java.util.Arrays;

public class Anime extends Request
{
    Anime(String url) throws IOException, InterruptedException
    {
        super(url);
    }
    
    public String toString()
    {
        String information = "";
        information += "-----" + Arrays.toString(animeDescriptorsHashMap.get("Title")) + "-----\n";

        for (String informationType : animeDescriptorsHashMap.keySet())
        {
            if (informationType.equals("Title"))
            {
                continue;
            }

            information += informationType + "- " + Arrays.toString(animeDescriptorsHashMap.get(informationType)) + "\n";
        }

        // Removes the newline at the end.
        information = information.substring(0, information.length() - 1);

        return information;
    }
}