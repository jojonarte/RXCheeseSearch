package com.dnamicro.rxsearch;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.schedulers.Schedulers;

public class CheeseActivity extends BaseSearchActivity {

    Disposable disposable;

    public Observable<String> createButtonClickObservable() {
        return Observable.create((ObservableEmitter<String> emitter) -> {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    emitter.onNext(queryEditText.getText().toString());
                }
            });

            emitter.setCancellable(new Cancellable() {
                @Override
                public void cancel() throws Exception {
                    searchButton.setOnClickListener(null);
                }
            });
        });
    }

    public Observable<String> createTextChangedObservable() {
        Observable<String> textChangeObservable = Observable.create((emitter)-> {
            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!charSequence.toString().isEmpty()) {
                        emitter.onNext(charSequence.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            };

            queryEditText.addTextChangedListener(textWatcher);
            emitter.setCancellable(new Cancellable() {
                @Override
                public void cancel() throws Exception {
                    queryEditText.removeTextChangedListener(textWatcher);
                }
            });
        });
        return textChangeObservable.filter((it)-> it.length() >= 2)
                .debounce(1000, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Observable<String> buttonClickStream = createButtonClickObservable();
        Observable<String> textChangeStream = createTextChangedObservable();
        Observable<String> searchTextObservable = Observable.merge(buttonClickStream, textChangeStream);
        disposable = searchTextObservable
                .observeOn(AndroidSchedulers.mainThread()) // observable will be subscribed on i/o thread
                .doOnNext(it -> showProgress())
                .observeOn(Schedulers.io())
                .map(it -> cheeseSearchEngine.search(it))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it-> {
                    hideProgress();
                    showResult(it);
                });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
