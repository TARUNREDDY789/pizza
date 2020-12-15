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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    Button submit;
    EditText name,email,phone,password;
    ProgressDialog loadingBar;
    FirebaseFirestore db;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        image = (ImageView)findViewById(R.id.image2) ;



        getSupportActionBar().setTitle("Sign UP");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingBar = new ProgressDialog(SignUp.this);


        name=(EditText) findViewById(R.id.et_name);
        email=(EditText) findViewById(R.id.et_email);
        phone=(EditText) findViewById(R.id.et_phone);
        password=(EditText) findViewById(R.id.et_password);

        submit=(Button)findViewById(R.id.btn_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();

            }
        });
    }
    private void CreateAccount() {

        String ename = name.getText().toString();
        String ephone = phone.getText().toString();
        String eemail = email.getText().toString();
        String epassword = password.getText().toString();

        if (TextUtils.isEmpty(ename))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(eemail))
        {
            Toast.makeText(this, "Please write your Email...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ephone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(epassword))
        {
            Toast.makeText(this, "Please Enter password...", Toast.LENGTH_SHORT).show();
        }

        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatepEmail(ename, ephone,eemail,epassword);
        }
    }

    private void ValidatepEmail(final String name, final String phone, final String email,String password) {
        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                        .baseUrl("http://192.168.0.102:3000/")
                        .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        api ap=retrofit.create(api.class);
        Call<ResponseBody> reg=ap.register(name,email,phone,password);
        reg.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d("test",response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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
}