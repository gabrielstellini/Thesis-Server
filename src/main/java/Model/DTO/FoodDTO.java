package Model.DTO;

import Model.DatabaseEntities.Food;
import Model.EntityToDto;

public class FoodDTO extends EntityToDto<FoodDTO, Food> {
    private String name;
    private int quantity;
    private int calories;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
