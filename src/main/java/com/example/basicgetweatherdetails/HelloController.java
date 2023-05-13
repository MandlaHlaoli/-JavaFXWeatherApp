package com.example.basicgetweatherdetails;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


public class HelloController {

    @FXML
    private ImageView imageView;
    @FXML
    private TextField cityInput;
    @FXML
    private Label temperature;
    @FXML
    private Label windpressure;
    @FXML
    private Label humidity;

    @FXML
    void onHelloButtonClick() throws IOException, JSONException {
        String city = cityInput.getText();

        if(city.trim().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Enter required field");
            alert.setContentText("Enter all location name");
            alert.showAndWait();
        }
        else {
            String apiKey = "2f2f08795fd5229ed83805ed2378d9fd";
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" +
                    city + "&appid=" + apiKey + "&units=metric";

            Backendless backendless;
            URL url = new URL(apiUrl);
            InputStream inputStream = url.openStream();
            Scanner scanner = new Scanner(inputStream);
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject jsonObject = new JSONObject(response);

            String description = jsonObject.getJSONArray("weather")
                    .getJSONObject(0).getString("description");

            double temp = jsonObject.getJSONObject("main").getDouble("temp");
            double humid = jsonObject.getJSONObject("main").getDouble("humidity");
            double windpre = jsonObject.getJSONObject("main").getDouble("pressure");

            try {
                String cloud = "cloud.png";
                String cloudy = "cloudy.png";
                String mostly_sunny = "mostly_sunny.png";
                String partly_sunny = "partly_sunny.png";
                String snowy = "snowy.png";
                String snowy_cloud = "snowy_cloud.png";
                String storm = "storm.png";
                String sun = "sun.png";
                String sunny = "sunny(1).png";



                if(description.equals("light snow"))
                {
                    Image snowyImage = new Image(setPath(snowy));
                    imageView.setImage(snowyImage);
                }
                else if(description.equals("few clouds"))
                {
                    Image fewClouds = new Image(setPath(cloudy));
                    imageView.setImage(fewClouds);
                }
                else if(description.equals("overcast clouds"))
                {
                    Image overCastCloud = new Image(setPath(cloud));
                    imageView.setImage(overCastCloud);
                }
                else if(description.equals("few clouds"))
                {
                    Image fewClouds = new Image(setPath(cloudy));
                    imageView.setImage(fewClouds);
                }

            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

            String weatherText = String.format("Temperature %s. C",temp);
            String humidityText = String.format("Humidity %s.",humid);
            String windPressureText = String.format("Wind pressure %s.",windpre);

            temperature.setText(weatherText);
            humidity.setText(humidityText);
            windpressure.setText(windPressureText);
        }
    }
    public String setPath(String imageView)
    {
        String path = "C:\\Users\\mandl\\javaprojects\\BasicGetWeatherDetails" +
                "\\src\\main\\resources\\com\\example\\basicgetweatherdetails" +
                "\\weather_vector\\"+imageView;
        return path;
    }
}