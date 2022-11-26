package com.example.cruddypizza;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Attr;

import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.ViewHolder> {

    public List<String> customers; //accessible in the MakePizza class
    public Language language;

    //constructor
    public OrdersRVAdapter(List<String> pCustomers, Language pLanguage){
        this.customers = pCustomers;
        this.language = pLanguage;
    }

    public OrdersRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(OrdersRVAdapter.ViewHolder holder, int position) {
        holder.textViewOrderRowOrderNum.setText("Order #" + (position + 1));
        holder.textViewOrderRowCustName.setText(customers.get(position));

        if (this.language == Language.ENGLISH){
            holder.textViewOrderRowProgress.setText(R.string.viewOrdersInProgressEN);
        }else{
            holder.textViewOrderRowProgress.setText(R.string.viewOrdersInProgressFR);
        }

        holder.position = holder.getAdapterPosition();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderRowOrderNum, textViewOrderRowCustName, textViewOrderRowProgress;
        CardView cardViewOrder;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewOrderRowOrderNum = itemView.findViewById(R.id.textViewOrderRowOrderNum);
            textViewOrderRowCustName = itemView.findViewById(R.id.textViewOrderRowCustName);
            textViewOrderRowProgress = itemView.findViewById(R.id.textViewOrderRowProgress);

            cardViewOrder = itemView.findViewById(R.id.cardViewOrder);
            cardViewOrder.setClickable(true);

            cardViewOrder.setOnClickListener((v) -> { //onclick listener for each row item in the recycler view
                Toast.makeText(itemView.getContext(), "you clicked me", Toast.LENGTH_SHORT).show();
            });

        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public List<String> getIngredients(){
        return customers;
    }

    //methods ======================================================================================
}
