package com.yacov.assessmentfundamentoandroid.view;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.yacov.assessmentfundamentoandroid.api.Client;
import com.yacov.assessmentfundamentoandroid.api.Service;
import com.yacov.assessmentfundamentoandroid.model.Tarefa;
import com.yacov.assessmentfundamentoandroid.model.TarefaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityContent extends ListActivity {

    private Tarefa tarefa;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            initViews();
        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }




    }

    private void initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Fetching Data");
        pd.setCancelable(false);
        pd.show();
        loadJson();
    }

    private void loadJson(){
        try {

            final ListView lista = getListView();

            Client client = new Client();
            Service apiservice =
                    client.getClient().create(Service.class);
            Call<TarefaResponse> call = apiservice.getTarefa();
            call.enqueue(new Callback<TarefaResponse>() {
                @Override
                public void onResponse(Call<TarefaResponse> call, Response<TarefaResponse> response) {
                    Tarefa tarefa = new Tarefa();
                    List<Tarefa> tarefas = response.body().getTarefa();
                    ArrayList<String> arrayList = new ArrayList();
                    ArrayAdapter<Tarefa> arrayAdapter = new ArrayAdapter<Tarefa>(getApplicationContext(), android.R.layout.simple_list_item_1, tarefas);
                    lista.setAdapter(arrayAdapter);
                    pd.hide();

                }

                @Override
                public void onFailure(Call<TarefaResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(getApplicationContext(), "Error Fetching Data!", Toast.LENGTH_SHORT).show();
                    pd.hide();

                }
            });



        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();

    }
}
