package dev.muskrat.library.dao;

import lombok.Getter;

public enum  Genre {

    CLASSIC("Классика"),
    FANTASTIC("Фантастика"),
    DRAMA("Драма"),
    BIOGRAPHY("Биография"),
    GUIDE("Обучающая лит-ра"),
    COMICS("Комиксы");

    @Getter
    private String i18n;

    Genre(String i18n) {
        this.i18n = i18n;
    }
}
