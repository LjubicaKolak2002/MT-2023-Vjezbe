package com.example.vjezba2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {
    @GET ("/search/repositories?q=stars:>100000")
    Call<RepositoryList> getBestRepositories();

}
