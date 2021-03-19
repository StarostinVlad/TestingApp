package com.starostinvlad.professional_1c.Api;

import com.starostinvlad.professional_1c.Models.QuestionWithAnswers;
import com.starostinvlad.professional_1c.Models.Section;
import com.starostinvlad.professional_1c.Models.Theme;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("api/v1/sections/")
    Observable<List<Section>> getSections();

//    @GET("api/v1/")
    @GET("api.json")
    Single<List<Section>> loadAll();

    @GET("api/v1/sections/1/themes")
    Single<List<Theme>> getThemes();

    @GET("api/v1/themes/{id}/questions")
    Observable<List<QuestionWithAnswers>> getQuestions(@Path("id") int id);
}
