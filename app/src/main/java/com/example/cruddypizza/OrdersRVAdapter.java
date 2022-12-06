package com.example.cruddypizza;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class OrdersRVAdapter extends RecyclerView.Adapter<OrdersRVAdapter.ViewHolder> {

    public ArrayList<Order> orders; //accessible in the MakePizza class
    public Language language;
    public String orderString;

    //constructor
    public OrdersRVAdapter(ArrayList<Order> pOrders, Language pLanguage, String pOrderString){
        this.orders = pOrders;
        this.language = pLanguage;
        this.orderString = pOrderString;
    }

    public OrdersRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_row,parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(OrdersRVAdapter.ViewHolder holder, int position) {
        holder.textViewOrderRowOrderNum.setText(orderString + orders.get(position).getOrderId());
        holder.textViewOrderRowCustName.setText(orders.get(position).getCustomerName());
        holder.orderID = orders.get(position).getOrderId();
        holder.orderDetails = orders.get(position);

        if (this.language == Language.ENGLISH){
            if (orders.get(position).getProgress() == 0){
                holder.textViewOrderRowProgress.setText(R.string.viewOrdersInProgressEN);
            }else{
                holder.textViewOrderRowProgress.setText(R.string.orderDetailsCompleteEN);
                holder.textViewOrderRowProgress.setBackgroundResource(R.drawable.progress_badge_complete);
            }

        }else{
            if (orders.get(position).getProgress() == 0){
                holder.textViewOrderRowProgress.setText(R.string.viewOrdersInProgressFR);
                holder.textViewOrderRowProgress.setText(R.string.viewOrdersInProgressFR);
            }else{
                holder.textViewOrderRowProgress.setText(R.string.orderDetailsCompleteFR);
                holder.textViewOrderRowProgress.setBackgroundResource(R.drawable.progress_badge_complete);
            }
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewOrderRowOrderNum, textViewOrderRowCustName, textViewOrderRowProgress;
        CardView cardViewOrder;
        Integer orderID;
        Order orderDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewOrderRowOrderNum = itemView.findViewById(R.id.textViewOrderRowOrderNum);
            textViewOrderRowCustName = itemView.findViewById(R.id.textViewOrderRowCustName);
            textViewOrderRowProgress = itemView.findViewById(R.id.textViewOrderRowProgress);

            cardViewOrder = itemView.findViewById(R.id.cardViewOrder);
            cardViewOrder.setClickable(true);

            cardViewOrder.setOnClickListener((v) -> { //onclick listener for each row item in the recycler view
                Intent i = new Intent(itemView.getContext(), Order_Details.class);
                i.putExtra("language", language);
                i.putExtra("orderDetails", orderDetails);
                itemView.getContext().startActivity(i);
            });

        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

//    public List<String> getIngredients(){
//        return customers;
//    }

    //methods ======================================================================================
}
