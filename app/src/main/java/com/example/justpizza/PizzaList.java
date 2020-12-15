package com.example.justpizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PizzaList extends AppCompatActivity {
    ListView list_view;
    List<PizzaPojo> pizzaPojos;
    ProgressDialog progressDialog;
    AllPizzasAdapter Adapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);
        getSupportActionBar().setTitle(" Pizzaz");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        pizzaPojos = new ArrayList<>();

        list_view = (ListView) findViewById(R.id.list_view);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait data is being Loaded");
        progressDialog.show();

        getdata();
    }

    public void getdata(){
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.102:3000/")
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        api ap=retrofit.create(api.class);
        Call<List<PizzaPojo>> p=ap.getpizza();
        p.enqueue(new Callback<List<PizzaPojo>>() {
            @Override
            public void onResponse(Call<List<PizzaPojo>> call, Response<List<PizzaPojo>> response) {
            pizzaPojos=response.body();
            listadapter li= new listadapter();
            list_view.setAdapter(li);
            }

            @Override
            public void onFailure(Call<List<PizzaPojo>> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public class listadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pizzaPojos.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v=getLayoutInflater().from(PizzaList.this).inflate(R.layout.list_all_pizzas,viewGroup,false);
            TextView ordertitle1=v.findViewById(R.id.tv_item_name);
            TextView description1=v.findViewById(R.id.tv_description);
            v.findViewById(R.id.btn_order).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(PizzaList.this,"Order placed successfully",Toast.LENGTH_LONG).show();
                }
            });


            ordertitle1.setText(pizzaPojos.get(i).getName());
            description1.setText(pizzaPojos.get(i).getDescription());


            return v;
        }
    }
}