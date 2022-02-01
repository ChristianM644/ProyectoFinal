package com.example.projectandroid.ApiRest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projectandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {
    private TextView categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_payment);
        categoryId=findViewById(R.id.text_category_id);
        get_list();

    }

    public void get_list()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.coingecko.com/api/v3/coins/categories/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PaymentTypeApi service=retrofit.create(PaymentTypeApi.class);
        Call<List<PaymentType>> call=service.findAll();
        call.enqueue(new Callback<List<PaymentType>>() {
            @Override
            public void onResponse(Call<List<PaymentType>> call, Response<List<PaymentType>> response) {
                if(!response.isSuccessful())
                {
                    categoryId.setText("Categoria Id: "+response.code());
                    return;
                }
                List<PaymentType> categoryList=response.body();
                //creamos un for para recorrer cada elemento por id
                for(PaymentType cat: categoryList)
                {
                    String result="";
                    result+="Category Id: "+cat.getCategory_id()+"\n";
                    result+="Category Name: "+cat.getName()+"\n\n";
                    categoryId.append(result);

                }
            }

            @Override
            public void onFailure(Call<List<PaymentType>> call, Throwable t) {
                categoryId.setText(t.getMessage());
            }
        });

    }

}