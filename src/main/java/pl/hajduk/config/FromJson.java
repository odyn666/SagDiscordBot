package pl.hajduk.config;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Setter
public class FromJson {
    String token;
    String prefix;
    String kutangPan;

    @SneakyThrows
    public FromJson() {
        String jsonString = "";
        Path pathToCfg = Paths.get("config.json");
        try {
//            jsonString = new String(Files.readAllBytes(Paths.get("src/main/java/pl/hajduk/config/config.json")));
            jsonString = new String(Files.readAllBytes(pathToCfg));
        } catch (IOException e) {
            jsonString = new String(Files.readAllBytes(pathToCfg));

//            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        token = jsonObject.get("token").toString();
        prefix = jsonObject.get("prefix").toString();
        kutangPan = jsonObject.get("kutang").toString();
        setToken(token);
        setPrefix(prefix);

    }


}
