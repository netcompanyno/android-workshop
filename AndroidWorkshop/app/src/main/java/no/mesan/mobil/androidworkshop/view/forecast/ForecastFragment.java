package no.mesan.mobil.androidworkshop.view.forecast;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import no.mesan.mobil.androidworkshop.R;
import no.mesan.mobil.androidworkshop.model.WeatherInfo;
import no.mesan.mobil.androidworkshop.task.FiveDayForecastTask;
import no.mesan.mobil.androidworkshop.task.ResponseListener;
import no.mesan.mobil.androidworkshop.view.SearchFragment;

/**
 * Created by Thomas on 16.08.2015.
 */
public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastAdapter adapter;

    private String location;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forecast, container, false);

        location = getArguments().getString(SearchFragment.LOCATION_KEY, "Oslo");

        initGui();
        initData();

        return view;
    }

    private void initGui() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewForecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        adapter = new ForecastAdapter(view.getContext());
        recyclerView.setAdapter(adapter);
    }

    private void initData() {

        new FiveDayForecastTask(new ResponseListener<List<WeatherInfo>>() {
            @Override
            public void success(List<WeatherInfo> weatherInfoList) {
                adapter.setWeather(weatherInfoList);
            }

            @Override
            public void error() {

            }
        });
    }
}