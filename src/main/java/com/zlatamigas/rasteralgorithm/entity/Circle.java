package com.zlatamigas.rasteralgorithm.entity;

import java.util.Objects;

public class Circle {

    private double centerX;
    private double centerY;
    private double radius;

    public Circle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Circle circle = (Circle) o;
        return Double.compare(circle.centerX, centerX) == 0 && Double.compare(circle.centerY, centerY) == 0 && Double.compare(circle.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(centerX, centerY, radius);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("C: ");
        sb.append("(").append(centerX);
        sb.append(", ").append(centerY);
        sb.append(") r=").append(radius);
        return sb.toString();
    }
}
