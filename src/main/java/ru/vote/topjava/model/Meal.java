package ru.vote.topjava.model;

import net.bytebuddy.build.ToStringPlugin;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @SequenceGenerator(name = "meal_seq", sequenceName = "meal_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal_seq")
    @Column(name = "meal_id")
    private Integer mealId;

    @Column(name = "menu_id", nullable = false)
    @NonNull
    @Size(min = 0)
    private Integer menuId;

    @Column(name = "name", nullable = false)
    @NonNull
    @Size(min = 3, max = 100, message = "Название блюда должно быть от 5 до 100 символов")
    private String name;

    @Column(name = "description", nullable = false)
    @NonNull
    @Size(min = 5, max = 100, message = "Описание блюда должно быть от 5 до 100 символов")
    private String description;

    @Column(name = "calories", nullable = false)
    @NonNull
    @Size(min = 0, max = 5000)
    private Integer calories;

    @Column(name = "price", nullable = false)
    @NonNull
    @Size(min = 0)
    private Float price;

//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "menu_id", referencedColumnName = "id_menu", nullable = false, insertable = false, updatable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @BatchSize(size = 1)
//    private Menu menu;
//
//    public Menu getMenu() {
//        return menu;
//    }
//
//    public void setMenu(Menu menu) {
//        this.menu = menu;
//    }

    public Integer getMealId() {
        return mealId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Meal(){}

    public Meal(Integer menuId, String name, String description, Integer calories, Float price) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
    }

    public Meal(Integer mealId, Integer menuId, String name, String description, Integer calories, Float price) {
        this.mealId = mealId;
        this.menuId = menuId;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", menuId=" + menuId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", price=" + price +
                '}';
    }
}
