package dev.muskrat.library.dao;

public enum  Genre {

    CLASSIC("Классика"),
    FANTASTIC("Фантастика"),
    DRAMA("Драма");


    private String name;

    Genre(String name) {
        this.name = name;
    }
}
