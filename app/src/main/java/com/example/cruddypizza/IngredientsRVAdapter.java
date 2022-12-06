package com.example.cruddypizza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRVAdapter extends RecyclerView.Adapter<IngredientsRVAdapter.ViewHolder> {

    public List<Ingredient> ingredientList; //accessible in the MakePizza class
    public int ingredientsCount = 0;

    public String maxIngredientsMsg;
    public final int MAX_INGREDIENTS = 3; //max number of ingredients

    Order orderDetails = null;

    //constructor
    public  IngredientsRVAdapter(List<Ingredient> pIngredients, String message){
        this.ingredientList = pIngredients;
        this.maxIngredientsMsg = message;
    }

    public  IngredientsRVAdapter(List<Ingredient> pIngredients, String message, Order pOrderDetails){
        this.ingredientList = pIngredients;
        this.maxIngredientsMsg = message;

        orderDetails = pOrderDetails;
        ArrayList<Integer> userSelectionToppings = orderDetails.getToppings();

        for (int i = 0; i < userSelectionToppings.size(); i++){
            this.ingredientList.get(i).setCount(userSelectionToppings.get(i));
            this.ingredientsCount += userSelectionToppings.get(i);
        }
    }

    public IngredientsRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row,parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(IngredientsRVAdapter.ViewHolder holder, int position) {
        holder.textViewIngredientCount.setText(Integer.toString(ingredientList.get(position).getCount()));
        holder.textViewIngredientName.setText(ingredientList.get(position).getName());
        holder.position = holder.getAdapterPosition();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIngredientCount, textViewIngredientName;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewIngredientCount = itemView.findViewById(R.id.textViewIngredientCount);
            textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);

            itemView.findViewById(R.id.buttonAddIngredient).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientsCount < MAX_INGREDIENTS){
                        ingredientList.get(position).increaseCount();
                        textViewIngredientCount.setText(Integer.toString(ingredientList.get(position).getCount()));
                        ingredientsCount++;
                    }else{
                        Toast.makeText(itemView.getContext(), String.format(maxIngredientsMsg, MAX_INGREDIENTS), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.findViewById(R.id.buttonRemoveIngredient).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ingredientList.get(position).getCount() > 0){
                        ingredientList.get(position).decreaseCount();
                        ingredientsCount--;
                    }
                    textViewIngredientCount.setText(Integer.toString(ingredientList.get(position).getCount()));
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public List<Ingredient> getIngredients(){
        return ingredientList;
    }

    //methods ======================================================================================
}
