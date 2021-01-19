package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    void initialize() {
        assert city != null : "fx:id=\"city\" was not injected: check your FXML file 'Untitled'.";
        assert getData != null : "fx:id=\"getData\" was not injected: check your FXML file 'Untitled'.";
        assert temp_info != null : "fx:id=\"temp_info\" was not injected: check your FXML file 'Untitled'.";
        assert temp_feels != null : "fx:id=\"temp_feels\" was not injected: check your FXML file 'Untitled'.";
        assert temp_max != null : "fx:id=\"temp_max\" was not injected: check your FXML file 'Untitled'.";

        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&APPID=d9fd6dfa55e57e9c2b108718c17b19aa");
            if (!output.isEmpty()) { // Нет ошибки и такой город есть
                JSONObject obj = new JSONObject(output);
                // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                DecimalFormat df = new DecimalFormat("###.#");

                double temperatura = (obj.getJSONObject("main").getDouble("temp") - 273);
                double temperatura_feels = (obj.getJSONObject("main").getDouble("feels_like") - 273);
                double temperatura_max = (obj.getJSONObject("main").getDouble("temp_max") - 273);


                temp_info.setText("Температура: " + df.format(temperatura));
                temp_feels.setText("Ощущается: " + df.format(temperatura_feels));
                temp_max.setText("Максимум: " + df.format(temperatura_max));


            }
       });
    }

    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try{
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch(Exception e){
            System.out.println(e);

        }
        return content.toString();
    }
}
