package com.priyanka.covid_19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.priyanka.covid_19tracker.api.ApiUtilities;
import com.priyanka.covid_19tracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalConfirm,totalActive,totalRecovered,totalDeath,totalTests;
    private TextView todayConfirm,todayRecovered,todayDeath,todayActive;
    private PieChart pieChart;
    private List<CountryData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        init();
        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());
                        for (int i=0;i<list.size();i++){
                            if (list.get(i).getCountry().equals("India")) {
                                int total=Integer.parseInt(list.get(i).getCases());
                                int active=Integer.parseInt(list.get(i).getActive());
                                int recovered=Integer.parseInt(list.get(i).getRecovered());
                                int deaths=Integer.parseInt(list.get(i).getDeaths());

                                totalConfirm.setText(NumberFormat.getInstance().format(total));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeath.setText(NumberFormat.getInstance().format(deaths));

                                todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                                pieChart.addPieSlice(new PieModel("Total", total, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green)));
                                pieChart.addPieSlice(new PieModel("Deaths", deaths,getResources().getColor(R.color.red)));
                                pieChart.startAnimation();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"Error : "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

    }
    private void init(){
        totalConfirm=findViewById(R.id.totalCases);
        totalActive=findViewById(R.id.totalActive);
        totalRecovered=findViewById(R.id.totalRecovered);
        totalDeath=findViewById(R.id.totalDeath);
        todayRecovered=findViewById(R.id.todayRecovered);
        todayDeath=findViewById(R.id.todayDeath);
        todayConfirm=findViewById(R.id.todayCases);
        todayActive=findViewById(R.id.todayActive);
        pieChart=findViewById(R.id.pieChart);
    }
}