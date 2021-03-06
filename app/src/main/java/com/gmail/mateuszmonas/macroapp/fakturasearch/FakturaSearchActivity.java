package com.gmail.mateuszmonas.macroapp.fakturasearch;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.gmail.mateuszmonas.macroapp.R;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.gmail.mateuszmonas.macroapp.faktury.FakturyActivity.EXTRA_KONTRAHENT_NAME;
import static com.gmail.mateuszmonas.macroapp.faktury.FakturyActivity.EXTRA_SEARCH_PARAMETERS;

public class FakturaSearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.symbol)
    TextView symbol;
    @BindView(R.id.dateMin)
    TextView dateMin;
    @BindView(R.id.dateMax)
    TextView dateMax;
    @BindView(R.id.cenaMin)
    EditText cenaMin;
    @BindView(R.id.cenaMax)
    EditText cenaMax;

    public static Intent createIntent(Context context, String kontrahentName) {
        Intent intent = new Intent(context, FakturaSearchActivity.class);
        intent.putExtra(EXTRA_KONTRAHENT_NAME, kontrahentName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktura_search);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            FakturaSearchParameters searchParameters = savedInstanceState.getParcelable(EXTRA_SEARCH_PARAMETERS);
            if (searchParameters != null) {
                symbol.setText(searchParameters.getSymbol());
                dateMin.setText(searchParameters.getDateMin());
                dateMax.setText(searchParameters.getDateMax());
                cenaMin.setText(searchParameters.getCenaMin());
                cenaMax.setText(searchParameters.getCenaMax());
            }
        }

        toolbar.setTitle(getIntent().getStringExtra(EXTRA_KONTRAHENT_NAME));
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.dateMin, R.id.dateMax})
    void onDateMinClick(final TextView textView) {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = String.format(Locale.getDefault(), "%d—%02d—%02d", year, month + 1, dayOfMonth);
                textView.setText(date);
            }
        }, 2003, 0, 1);
        dialog.show();
    }

    @OnClick(R.id.searchFaktury)
    void onSearchFakturyClick() {
        Intent data = new Intent();
        data.putExtra(EXTRA_SEARCH_PARAMETERS, new FakturaSearchParameters(
                symbol.getText().toString(),
                dateMin.getText().toString(),
                dateMax.getText().toString(),
                cenaMin.getText().toString(),
                cenaMax.getText().toString()
        ));
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FakturaSearchParameters searchParameters = new FakturaSearchParameters(
                symbol.getText().toString(),
                dateMin.getText().toString(),
                dateMax.getText().toString(),
                cenaMin.getText().toString(),
                cenaMax.getText().toString()
        );
        outState.putParcelable(EXTRA_SEARCH_PARAMETERS, searchParameters);
    }
}
