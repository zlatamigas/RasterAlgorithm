package com.zlatamigas.rasteralgorithm.service.impl;

import com.zlatamigas.rasteralgorithm.entity.Circle;
import com.zlatamigas.rasteralgorithm.entity.Line;
import com.zlatamigas.rasteralgorithm.service.RasterAlgorithm;
import javafx.scene.chart.XYChart.Data;

import java.util.ArrayList;
import java.util.List;

public class RasterAlgorithmImpl implements RasterAlgorithm {

    @Override
    public List<Data<Number, Number>> simpleStep(Line line) {
        List<Data<Number, Number>> pixels = new ArrayList<>();

        double k = (line.getStartY() - line.getEndY()) / (line.getStartX() - line.getEndX());
        double b = line.getStartY() - k * line.getStartX();
        double xStep = 0.01;

        double x = Math.min(line.getStartX(), line.getEndX());
        double xStop = Math.max(line.getStartX(), line.getEndX());
        double y;

        int xi, yi;

        for (; x <= xStop; x += xStep) {

            xi = (int) x + (x - (int) x >= 0.5 ? 1 : 0);
            y = k * x + b;
            yi = (int) y + (y - (int) y >= 0.5 ? 1 : 0);

            pixels.add(new Data<>(xi + PIXEL_MARK_XY_OFFSET, yi + PIXEL_MARK_XY_OFFSET));
        }

        return pixels;
    }

    @Override
    public List<Data<Number, Number>> digitalDifferentialAnalyzer(Line line) {
        List<Data<Number, Number>> pixels = new ArrayList<>();

        double l, x, y;
        double dx, dy;

        x = (int) line.getStartX();
        y = (int) line.getStartY();

        dx = line.getEndX() - line.getStartX();
        dy = line.getEndY() - line.getStartY();

        l = (int) Math.max(Math.abs(dx), Math.abs(dy));
        dx /= l;
        dy /= l;

        pixels.add(new Data<>(x + PIXEL_MARK_XY_OFFSET, y + PIXEL_MARK_XY_OFFSET));

        for (int i = 0; i < l; i++) {
            x += dx;
            y += dy;

            pixels.add(new Data<>((int) x + PIXEL_MARK_XY_OFFSET, (int) y + PIXEL_MARK_XY_OFFSET));
        }

        return pixels;
    }

    @Override
    public List<Data<Number, Number>> bresenhamLine(Line line) {
        List<Data<Number, Number>> pixels = new ArrayList<>();

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = (int) (line.getEndX() - line.getStartX());
        dy = (int) (line.getEndY() - line.getStartY());

        incx = (dx > 0) ? 1 : ((dx < 0) ? -1 : 0);
        incy = (dy > 0) ? 1 : ((dy < 0) ? -1 : 0);

        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;

        if (dx > dy) {
            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else {
            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;
        }

        x = (int) line.getStartX();
        y = (int) line.getStartY();
        err = el / 2;

        pixels.add(new Data<>(x + PIXEL_MARK_XY_OFFSET, y + PIXEL_MARK_XY_OFFSET));

        for (int t = 0; t < el; t++) {
            err -= es;
            if (err < 0) {
                err += el;
                x += incx;
                y += incy;
            } else {
                x += pdx;
                y += pdy;
            }
            pixels.add(new Data<>(x + PIXEL_MARK_XY_OFFSET, y + PIXEL_MARK_XY_OFFSET));
        }

        return pixels;
    }

    @Override
    public List<Data<Number, Number>> bresenhamCircle(Circle circle) {
        List<Data<Number, Number>> pixels = new ArrayList<>();

        int x = 0;
        int y = (int) circle.getRadius();
        int e = (int) (3 - 2 * circle.getRadius());

        int centerX = (int) circle.getCenterX();
        int centerY = (int) circle.getCenterY();

        while (x <= y) {

            addCircleOctantPoints(pixels, x, y, centerX, centerY);

            if (e >= 0) {
                e += 4 * (x - y) + 10;
                y--;
            } else {
                e += 4 * x + 6;
            }
            x++;
        }


        return pixels;
    }

    private void addCircleOctantPoints(List<Data<Number, Number>> pixels, int x, int y, int centerX, int centerY) {

        double xOffset = centerX + PIXEL_MARK_XY_OFFSET;
        double yOffset = centerY + PIXEL_MARK_XY_OFFSET;

        pixels.add(new Data<>(x + xOffset, y + yOffset));
        pixels.add(new Data<>(-x + xOffset, y + yOffset));
        pixels.add(new Data<>(-x + xOffset,  -y + yOffset));
        pixels.add(new Data<>(x + xOffset, -y + yOffset));
        pixels.add(new Data<>(y + xOffset, x + yOffset));
        pixels.add(new Data<>(-y + xOffset, x + yOffset));
        pixels.add(new Data<>(-y + xOffset,  -x + yOffset));
        pixels.add(new Data<>(y + xOffset, -x + yOffset));
    }
}
