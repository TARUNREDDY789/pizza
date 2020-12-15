package com.example.justpizza;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;

public class Signin extends AppCompatActivity {
    EditText et_email,et_password;
    Button btn_login;
    TextView tv_register;
    ProgressDialog loadingBar;
    private String parentDbName = "Users";
    FirebaseFirestore db;
    ImageView image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getSupportActionBar().setTitle("Login");

        loadingBar = new ProgressDialog(Signin.this);





        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        image = (ImageView)findViewById(R.id.image1) ;

        tv_register=(TextView)findViewById(R.id.tv_register);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this, SignUp.class));
            }
        });

        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser();
            }
        });

    }
    private void LoginUser() {
        String username = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            SignIn(username, password);

        }
    }
    private void SignIn(final String username, final String password) {
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.102:3000/")
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        api ap=retrofit.create(api.class);
        Call<ResponseBody> reg=ap.login(username,password);
        reg.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    Log.d("success",response.body().string());

                    Intent i=new Intent(Signin.this,PizzaList.class);
                    startActivity(i);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}