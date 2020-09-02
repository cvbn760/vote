package ru.vote.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @SequenceGenerator(name = "menus_seq", sequenceName = "menus_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menus_seq")
    @Column(name = "id_menu")
    private Integer idMenu;

    @Column(name = "id_rest", nullable = false)
    private Integer idRest;

    @Column(name = "counter_voice", nullable = false)
    private Integer counterVoice = 0;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id", referencedColumnName = "id_menu",nullable = false, insertable = false, updatable = false)
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
