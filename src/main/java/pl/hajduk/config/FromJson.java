package pl.hajduk.config;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FromJson
    {
    String token;
    String prefix;
    
    public FromJson()
        {
        String jsonString = "";
        try
            {
            jsonString = new String(Files.readAllBytes(Paths.get("src/main/java/pl/hajduk/config/config.json")));
            } catch (IOException e)
            {
            throw new RuntimeException(e);
            }
        JSONObject jsonObject = new JSONObject(jsonString);
        String token = jsonObject.get("token").toString();
        String prefix=jsonObject.get("prefix").toString();
        setToken(token);
        setPrefix(prefix);
        
        }
    
    public String getPrefix()
        {
        return prefix;
        }
    
    public void setPrefix(String prefix)
        {
        this.prefix = prefix;
        }
    
    public String getToken()
        {
        return token;
        }
    
    public void setToken(String token)
        {
        this.token = token;
        }
    }
