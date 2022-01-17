package bonus;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Graph extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Main.init();
        File file = new File("src/bonus/input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        int max = 0, size = 0;
        String line;
        int prev = -1;
        while ( (line = br.readLine()) != null ) {
            String[] tokens = line.split(" ");
            int x = Integer.parseInt(tokens[0]);
            if(tokens.length > 1) {
                max = Math.max(max, Integer.parseInt(tokens[1]));
            }
            max = Math.max(max, x);
            if(x != prev) size++;
            prev = x;
        }

        int sy = max/10;
        if(sy == 0) sy = 1;

        int sx = max/10;

        NumberAxis xAxis = new NumberAxis(0, size, Math.max(sx, 5));
        xAxis.setLabel("Cycle Number");

        NumberAxis yAxis = new NumberAxis(0, max+1, Math.min(sy, 15));
        yAxis.setLabel("Memory Address");

        ScatterChart<String, Number> scatterChart = new ScatterChart(xAxis, yAxis);

        XYChart.Series series = new XYChart.Series();

        int cycle = 0;
        br = new BufferedReader(new FileReader(file));
        while ( (line = br.readLine()) != null) {
            String[] tokens = line.split(" ");
            if(tokens.length == 1)
                series.getData().add(new XYChart.Data(cycle++, Integer.parseInt(line)));
            else {
                series.getData().add(new XYChart.Data(cycle, Integer.parseInt(tokens[1])));
            }
        }

        scatterChart.getData().addAll(series);

        Group root = new Group(scatterChart);
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Scatter Chart");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
