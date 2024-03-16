package backend.backend.system.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@PropertySource("classpath:credentials.yml")
@Component
//public class Credentials implements ApplicationRunner {
public class Credentials {
    @Value("${client_id}")
    private String client_id;

    @Value("${project_id}")
    private String project_id;

    @Value("${auth_uri}")
    private String auth_uri;

    @Value("${token_uri}")
    private String token_uri;

    @Value("${auth_provider_x509_cert_url}")
    private String auth_provider;

    @Value("${client_secret}")
    private String client_secret;

    @Value("${redirect_uris}")
    private String[] list;

    // @TODO 추후 수정
//    @Override
    public void dddd(ApplicationArguments args) throws Exception {

        FileWriter writer = null;
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("client_id", client_id);
        jsonObject.addProperty("project_id", project_id);
        jsonObject.addProperty("auth_uri", auth_uri);
        jsonObject.addProperty("token_uri", token_uri);
        jsonObject.addProperty("auth_provider_x509_cert_url", auth_provider);
        jsonObject.addProperty("client_secret", client_secret);

        JsonArray array = new JsonArray();
        Arrays.asList(list).forEach(f -> array.add(f));

        jsonObject.add("redirect_uris" ,array);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonObject);

        try {
            writer = new FileWriter("src/main/resources/config.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
