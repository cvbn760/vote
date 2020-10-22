package ru.vote.topjava.model;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @Column(name = "id_menu")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMenu;

    @Column(name = "id_rest", nullable = false)
    private Integer idRest;

    @Column(name = "counter_voice", nullable = false)
    private Integer counterVoice = 0;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", referencedColumnName = "id_menu",nullable = false, insertable = false, updatable = false)
    @BatchSize(size = 30)
    private List<Meal> mealSet;

    public Menu(){}

    public Menu(Integer idRest, LocalDate date) {
        this.idRest = idRest;
        this.counterVoice = 0;
        this.date = date;
        mealSet = new ArrayList<>();
    }

    public Integer getIdMenu() {
        return idMenu;
    }

    public Integer getIdRest() {
        return idRest;
    }

    public Integer getCounterVoice() {
        return counterVoice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void incrementCounter(){
        this.counterVoice += 1;
    }

    public void decrementCounter(){
        this.counterVoice = (counterVoice < 1) ? 0 : counterVoice - 1;
    }

    public List<Meal> getMealList() {
        return mealSet;
    }

    public void setMealList(List<Meal> mealSet) {
        this.mealSet = mealSet;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id_menu=" + idMenu +
                ", id_rest=" + idRest +
                ", counter_voice=" + counterVoice +
                ", date=" + date +
                '}';
    }
}
