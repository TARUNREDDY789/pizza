package com.example.justpizza;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AllPizzasAdapter extends BaseAdapter {

    ProgressDialog progressDialog;
    List<PizzaPojo> pizzaPojos;
    String itemname,description;
    Context cnt;
    public AllPizzasAdapter(List<PizzaPojo> ar, String itemname, String description, Context cnt)
    {
        this.pizzaPojos=ar;
        this.itemname=itemname;
        this.description = description;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return pizzaPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_all_pizzas,null);

        TextView tv_items_name=(TextView)obj2.findViewById(R.id.tv_item_name);
        TextView tv_desc_name=(TextView)obj2.findViewById(R.id.tv_description);
        tv_items_name.setText("Item Name :"+pizzaPojos.get(pos).getName());
        tv_desc_name.setText("Description :"+pizzaPojos.get(pos).getDescription());

        Button btn_book=(Button)obj2.findViewById(R.id.btn_order);
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt, "  "+pizzaPojos.get(pos).getName() +"  "+"order placed Successfullly " , Toast.LENGTH_LONG).show();
                cnt.startActivity(new Intent(cnt, PizzaList.class));
                ((Activity)cnt).finish();
            }
        });




        return obj2;
    }

}



