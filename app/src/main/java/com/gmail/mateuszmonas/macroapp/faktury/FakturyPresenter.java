package com.gmail.mateuszmonas.macroapp.faktury;

import com.gmail.mateuszmonas.macroapp.data.DataRepository;
import com.gmail.mateuszmonas.macroapp.data.remote.ServerResponseFaktury;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class FakturyPresenter implements FakturyContract.Presenter {

    private DataRepository repository;
    private FakturyContract.View view;
    private String kontrahentReference;

    @Inject
    FakturyPresenter(DataRepository repository, FakturyContract.View view, String kontrahentReference) {
        this.repository = repository;
        this.view = view;
        this.kontrahentReference = kontrahentReference;
    }

    @Inject
    void setupListener() {
        view.setPresenter(this);
    }

    public void start() {
        loadFaktury(0);
    }

    @Override
    public void loadFaktury(int offset) {

        final boolean forceUpdate = offset==0;
        view.setLoadingView(true);

        Callback<ServerResponseFaktury> callback = new Callback<ServerResponseFaktury>() {
            @Override
            public void onResponse(Call<ServerResponseFaktury> call, Response<ServerResponseFaktury> response) {
                view.setLoadingView(false);
                view.showFaktury(response.body().getQ1().getData(), forceUpdate);
            }

            @Override
            public void onFailure(Call<ServerResponseFaktury> call, Throwable t) {
                t.printStackTrace();
                view.setLoadingView(false);
                view.setBrakPolaczeniaView(true);
            }
        };
        repository.getFaktury(callback, kontrahentReference, offset);
    }

    @Override
    public void openFakturaDetails(String fakturaReference) {
        view.showFakturaDetails(fakturaReference);
    }
}
