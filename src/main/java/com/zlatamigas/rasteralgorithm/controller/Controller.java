package com.zlatamigas.rasteralgorithm.controller;

import com.zlatamigas.rasteralgorithm.entity.Circle;
import com.zlatamigas.rasteralgorithm.entity.Line;
import com.zlatamigas.rasteralgorithm.service.RasterAlgorithm;
import com.zlatamigas.rasteralgorithm.service.impl.RasterAlgorithmImpl;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static javafx.scene.chart.XYChart.Data;
import static javafx.scene.chart.XYChart.Series;

public class Controller implements Initializable {

    private static final int sizeAxis = 20;
    private static final int stepAxis = 1;
    private static final NumberAxis xAxis = new NumberAxis(0, sizeAxis, stepAxis);
    private static final NumberAxis yAxis = new NumberAxis(0, sizeAxis, stepAxis);

    public Pane idCharPane;
    public TextField idTVStartX;
    public TextField idTVStartY;
    public TextField idTVEndX;
    public TextField idTVEndY;
    public TextField idTVCenterX;
    public TextField idTVCenterY;
    public TextField idTVRadius;

    private Stage stage;

    private LineChart<Number, Number> chart;

    private RasterAlgorithm rasterAlgorithm;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rasterAlgorithm = new RasterAlgorithmImpl();

        Pattern validDouble = Pattern.compile("(([1-9]\\d*)|0)?(\\.\\d*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            return validDouble.matcher(text).matches() ? c : null;
        };
        StringConverter<Double> converter = new StringConverter<Double>() {
            @Override
            public Double fromString(String s) {
                return s.isEmpty() || ".".equals(s) ? 0.0 : Double.valueOf(s);
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        idTVStartX.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVStartY.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVEndX.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVEndY.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVCenterX.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVCenterY.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));
        idTVRadius.setTextFormatter(new TextFormatter<>(converter, 0.0, filter));

        xAxis.setLabel("x");
        yAxis.setLabel("y");
        chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setPrefSize(500, 500);
        chart.setAnimated(false);
        idCharPane.getChildren().add(chart);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void onSubmitStep(ActionEvent actionEvent) {

        Line line = getLineData();

        List<Data<Number, Number>> lineSeries = Arrays.asList(
                new Data<>(line.getStartX(), line.getStartY()),
                new Data<>(line.getEndX(), line.getEndY()));

        List<Data<Number, Number>> pixelSeries = rasterAlgorithm.simpleStep(line);

        refreshChart(pixelSeries, lineSeries);
    }

    public void onSubmitBresenhamLine(ActionEvent actionEvent) {

        Line line = getLineData();

        List<Data<Number, Number>> lineSeries = Arrays.asList(
                new Data<>(line.getStartX(), line.getStartY()),
                new Data<>(line.getEndX(), line.getEndY()));

        List<Data<Number, Number>> pixelSeries = rasterAlgorithm.bresenhamLine(line);

        refreshChart(pixelSeries, lineSeries);
    }

    public void onSubmitDDA(ActionEvent actionEvent) {
        Line line = getLineData();

        List<Data<Number, Number>> lineSeries = Arrays.asList(
                new Data<>(line.getStartX(), line.getStartY()),
                new Data<>(line.getEndX(), line.getEndY()));

        List<Data<Number, Number>> pixelSeries = rasterAlgorithm.digitalDifferentialAnalyzer(line);

        refreshChart(pixelSeries, lineSeries);
    }

    private Line getLineData() {
        double startX = Double.parseDouble(idTVStartX.getText());
        double startY = Double.parseDouble(idTVStartY.getText());
        double endX = Double.parseDouble(idTVEndX.getText());
        double endY = Double.parseDouble(idTVEndY.getText());

        return new Line(startX, startY, endX, endY);
    }

    public void onSubmitBresenhamCircle(ActionEvent actionEvent) {

        chart.getData().clear();
        chart.setId("chart-circle");

        double centerX = Integer.MAX_VALUE;
        double centerY = Integer.MAX_VALUE;
        double radius = Integer.MAX_VALUE;
        try {
            centerX = Double.parseDouble(idTVCenterX.getText());
        } catch (NumberFormatException e){

        }
        try {
            centerY = Double.parseDouble(idTVCenterY.getText());
        } catch (NumberFormatException e){

        }
        try {
            radius = Double.parseDouble(idTVRadius.getText());
        } catch (NumberFormatException e){

        }


        Circle circle = new Circle(centerX, centerY, radius);

        double stepX = 0.1;
        double startX = centerX - radius;
        double endX = centerX + radius;
        double r2 = radius * radius;

        List<Data<Number, Number>> line1 = new ArrayList<>();
        List<Data<Number, Number>> line2 = new ArrayList<>();


        double y = 0;
        double x = startX;
        line1.add(new Data(startX, centerY));
        for (; x <= endX; x += stepX) {
            y = Math.sqrt(r2 - (x - centerX) * (x - centerX)) + centerY;
            line1.add(new Data<>(x, y));
        }
        line1.add(new Data<>(endX, centerY));
        line2.add(new Data<>(endX, centerY));
        for (; x >= startX; x -= stepX) {
            y = -Math.sqrt(r2 - (x - centerX) * (x - centerX)) + centerY;
            line2.add(new Data<>(x, y));
        }
        line2.add(new Data(startX, centerY));

        List<Data<Number, Number>> pixels = rasterAlgorithm.bresenhamCircle(circle);

        Series pixelSeries = new Series();
        pixelSeries.getData().addAll(pixels);
        Series lineSeries1 = new Series();
        lineSeries1.getData().addAll(line1);
        Series lineSeries2 = new Series();
        lineSeries2.getData().addAll(line2);


        chart.getData().addAll(pixelSeries, lineSeries1, lineSeries2);
    }

    private void refreshChart(List<Data<Number, Number>> pixels, List<Data<Number, Number>> line) {

        chart.getData().clear();
        chart.setId("chart-line");

        Series pixelSeries = new Series();
        pixelSeries.getData().addAll(pixels);
        Series lineSeries = new Series();
        lineSeries.getData().addAll(line);

        chart.getData().addAll(pixelSeries, lineSeries);
    }
}
