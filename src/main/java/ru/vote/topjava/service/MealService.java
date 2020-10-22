package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MealService {

    private MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    // Добавить новую еду в меню
    public Meal addMealInMenu(Meal meal){
        if (meal.getMealId() == null){
            return mealRepository.save(meal);
        }
        return mealRepository.save(meal);
    }

    // Достать еду по id
    public Meal getMealById(int id) {
        return mealRepository.findById(id).get();
    }

    // Удалить еду
    public void deleteMealById(int id){
        mealRepository.delete(id);
    }

    // Достать всю еду / Достать еду за конкретную дату
    public List<Meal> getAllMeal(LocalDate date) {
        if (date == null){
            return mealRepository.findAll();
        }
        return mealRepository.getMealByDate(date);
    }
}
