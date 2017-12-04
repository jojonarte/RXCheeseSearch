package com.dnamicro.rxsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Joselito Narte Jr. on 04/12/2017.
 */

public class BaseSearchActivity extends AppCompatActivity {

    protected CheeseSearchEngine cheeseSearchEngine;
    protected CheeseAdapter cheeseAdapter;

    @BindView(R.id.searchButton)
    Button searchButton;
    @BindView(R.id.queryEditText)
    EditText queryEditText;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        cheeseAdapter = new CheeseAdapter();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(cheeseAdapter);

        String[] array = getResources().getStringArray(R.array.cheeses);
        cheeseSearchEngine = new CheeseSearchEngine(array);
    }

    protected void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    protected void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    protected void showResult(List<String> result) {
        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show();
        }
        cheeseAdapter.swapItems(result);
    }


}
