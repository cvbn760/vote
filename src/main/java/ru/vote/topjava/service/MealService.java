package ru.vote.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vote.topjava.model.Meal;
import ru.vote.topjava.repository.MealRepository;

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
        return mealRepository.save(meal);
    }

    // Достать еду по id
    public Meal getMealById(int id) {
        return mealRepository.findById(id).get();
    }

    // Удалить еду
    public void deleteMealById(int id){
        mealRepository.deleteById(id);
    }

    // Достать всю еду
    public List<Meal> getAllMeal(){
        return mealRepository.findAll();
    }
}
