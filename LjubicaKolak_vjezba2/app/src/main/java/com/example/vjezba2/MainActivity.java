package com.example.vjezba2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private CustomAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new CustomAdapter(this, new ArrayList<>()); //prazna lista na početku
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RepositoryList> call = service.getBestRepositories();
        call.enqueue(new Callback<RepositoryList>() {
            @Override
            public void onResponse(Call<RepositoryList> call, Response<RepositoryList> response) {
                if (response.isSuccessful()) {
                    //String jsonResponse = new GsonBuilder().setPrettyPrinting().create().toJson(response.body());
                    //Log.d("JSON Response", jsonResponse);

                    RepositoryList repositoryList = response.body();
                    generateDataList(repositoryList.items);
                }
            }

            @Override
            public void onFailure(Call<RepositoryList> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
    private void generateDataList(List<Repository> repositoryList) {
        adapter.setDataList(repositoryList);
        adapter.notifyDataSetChanged();
    }
}