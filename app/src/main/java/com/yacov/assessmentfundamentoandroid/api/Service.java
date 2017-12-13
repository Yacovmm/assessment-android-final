package com.yacov.assessmentfundamentoandroid.api;

import com.yacov.assessmentfundamentoandroid.model.TarefaResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by YacovR on 12-Dec-17.
 */

public interface Service {

    @GET("/dadosAtividades.php")
    Call<TarefaResponse> getTarefa();
}
