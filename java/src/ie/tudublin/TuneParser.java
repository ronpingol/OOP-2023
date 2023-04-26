package ie.tudublin;
import java.io.*;
import java.util.*;

public class TuneParser
{
    ArrayList<Tune> tunes = new ArrayList<Tune>();

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        for(Tune t:tunes)
        {
            sb.append(t + "\n");
        }
        return sb.toString();
    }

    public Tune findTune(String title)
    {
        for(Tune t:tunes)
        {
            if (t.getTitle().contains(title))
            {
                return t;
            }
        }
        return null;
    }
    
    public TuneParser(String fileName)
    {
        BufferedReader inputStream = null;

        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            String l;
            Tune tune = null;
            String notation = "";
            while ((l = inputStream.readLine()) != null) 
            {
                if (l.startsWith("X:"))
                {
                    tune = new Tune();
                    tunes.add(tune);
                    tune.setX(Integer.parseInt(l.substring(2)));
                }
                if (l.startsWith("T:"))
                {
                    if (tune.getTitle() == null)
                    {
                        tune.setTitle(l.substring(2));
                    }
                    else if (tune.getAltTitle() == null)
                    {
                        tune.setAltTitle(l.substring(2));
                    }                    
                }
                if (tune!= null)
                {
                    if (l.equals(""))
                    {
                        tune.setNotation(notation);
                        notation = "";
                    }
                    else
                    {
                        notation += l + "\n";
                    }                    
                }
                
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } 
        finally 
        {
            if (inputStream != null) {
                try
                {
                    inputStream.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}