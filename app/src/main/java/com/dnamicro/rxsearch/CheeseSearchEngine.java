package com.dnamicro.rxsearch;

import android.os.Build;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

/**
 * Created by Joselito Narte Jr. on 04/12/2017.
 */

public class CheeseSearchEngine {


    ArrayList<String> cheeses;
    public CheeseSearchEngine(String[] cheeses) {
        this.cheeses = new ArrayList<>(Arrays.asList(cheeses));
    }


    public List<String> search(String query) throws InterruptedException {
        Thread.sleep(2000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return cheeses.stream().filter((it)-> it.toLowerCase().contains(query)).collect(Collectors.toList());
        }

        Predicate<String> predicate = new Predicate<String>() {
            @Override
            public boolean apply(@Nullable String input) {
                return input.trim().toLowerCase().contains(query);
            }
        };
        return Lists.newArrayList(Collections2.filter(cheeses, predicate));
    }

}
