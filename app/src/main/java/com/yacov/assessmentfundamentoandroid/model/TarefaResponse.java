package com.yacov.assessmentfundamentoandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YacovR on 12-Dec-17.
 */

public class TarefaResponse {

    @SerializedName("tarefa")
    @Expose
    private ArrayList<Tarefa> tarefa;

    public ArrayList<Tarefa> getTarefa() {
        return tarefa;
    }

    public void setTarefa(ArrayList<Tarefa> tarefa) {
        this.tarefa = tarefa;
    }
}
