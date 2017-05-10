package com.gmail.mateuszmonas.macroapp.kontrahenci;

import com.gmail.mateuszmonas.macroapp.BasePresenter;
import com.gmail.mateuszmonas.macroapp.BaseView;
import com.gmail.mateuszmonas.macroapp.data.Kontrahent;

import java.util.List;

interface KontrahenciContract {

    interface View extends BaseView<Presenter>{

        void showKontrachenci(List<Kontrahent> kontrachenci);

        void showFaktury();
    }

    interface Presenter extends BasePresenter{

        void loadKontrachenci();

        void openFaktury();

    }

}
