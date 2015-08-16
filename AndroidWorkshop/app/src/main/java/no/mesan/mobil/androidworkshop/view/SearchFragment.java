package no.mesan.mobil.androidworkshop.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import no.mesan.mobil.androidworkshop.R;
import no.mesan.mobil.androidworkshop.view.forecast.ForecastFragment;

public class SearchFragment extends Fragment {
    private static final String PREFERENCES_NAME = "Preferences";
    public static final String LOCATION_KEY = "locationKey";

    private RadioGroup radioGroupSearchMode;

    private EditText editTextLocation;
    private Button buttonSearch;
    private RecyclerView recyclerViewLocations;


    private SharedPreferences sharedPreferences;
    private LocationAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initViews(view);
        initAdapters();
        initListeners();

        return view;
    }

    private LinkedHashSet<String> getLocationHistory() {
        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
        return new LinkedHashSet<>(sharedPreferences.getStringSet("locations", new HashSet<String>()));
    }

    private void initViews(View view) {
        radioGroupSearchMode = (RadioGroup) view.findViewById(R.id.radioGroupSearchMode);

        editTextLocation = (EditText) view.findViewById(R.id.editTextLocation);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        recyclerViewLocations = (RecyclerView) view.findViewById(R.id.recyclerViewLocations);
    }

    private void initAdapters() {
        LinkedHashSet<String> locations = getLocationHistory();
        adapter = new LocationAdapter(locations, new LocationItemClickListener() {
            @Override
            public void onClick(String location) {
                goToCurrentWeatherFragment(location);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewLocations.setLayoutManager(linearLayoutManager);
        recyclerViewLocations.setAdapter(adapter);
        Drawable drawable = getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha);

        drawable.setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_IN);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(drawable);
        recyclerViewLocations.addItemDecoration(dividerItemDecoration);
    }

    private void goToCurrentWeatherFragment(String location) {
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_KEY, location);
        ((MainActivity) getActivity()).goToFragment(CurrentWeatherFragment.class, bundle);
    }

    private void goToForecastFragment(String location) {
        Bundle bundle = new Bundle();
        bundle.putString(LOCATION_KEY, location);
        ((MainActivity) getActivity()).goToFragment(ForecastFragment.class, bundle);
    }

    private void initListeners() {
         buttonSearch.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String location = editTextLocation.getText().toString();
                 adapter.addLocation(location);
                 saveLocations();
                 hideKeyboard();
                 switch (radioGroupSearchMode.getCheckedRadioButtonId()) {
                     case R.id.radioButtonCurrentWeather:
                         goToCurrentWeatherFragment(location);
                         break;
                     default:
                         goToForecastFragment(location);
                 }
             }
         });
    }

    private void saveLocations() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("locations", adapter.getLocations());
        editor.apply();
    }

    private void hideKeyboard() {
        // Check if no view has focus:
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
