package com.zlatamigas.rasteralgorithm.service;

import com.zlatamigas.rasteralgorithm.entity.Circle;
import com.zlatamigas.rasteralgorithm.entity.Line;
import javafx.scene.chart.XYChart;

import java.util.List;

import static javafx.scene.chart.XYChart.*;

public interface RasterAlgorithm {

    double PIXEL_MARK_XY_OFFSET = 0.5;

    List<Data<Number, Number>> simpleStep(Line line);

    List<Data<Number, Number>> digitalDifferentialAnalyzer(Line line);

    List<Data<Number, Number>> bresenhamLine(Line line);

    List<Data<Number, Number>> bresenhamCircle(Circle circle);

}
