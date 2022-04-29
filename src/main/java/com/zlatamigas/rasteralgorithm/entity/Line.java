package com.zlatamigas.rasteralgorithm.entity;

import java.util.Objects;

public class Line {

    private double startX;
    private double startY;
    private double endX;
    private double endY;

    public Line(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Double.compare(line.startX, startX) == 0 && Double.compare(line.startY, startY) == 0 && Double.compare(line.endX, endX) == 0 && Double.compare(line.endY, endY) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startX, startY, endX, endY);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("L: ");
        sb.append("(").append(startX);
        sb.append(", ").append(startY);
        sb.append(") (").append(endX);
        sb.append(", ").append(endY);
        sb.append(')');
        return sb.toString();
    }
}
