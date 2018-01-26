package com.levrsoft.carmudiapptest.main.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.levrsoft.carmudiapptest.R;
import com.levrsoft.carmudiapptest.main.adapters.CarRecyclerAdapter;
import com.levrsoft.carmudiapptest.main.model.CarmudiResponse;
import com.levrsoft.carmudiapptest.main.presenter.MainPresenter;
import com.levrsoft.carmudiapptest.main.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {


    //Views
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view_car_list)
    RecyclerView mRecyclerViewCarList;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    //Our presenter
    private MainPresenter mainPresenter;

    //Adapter
    private CarRecyclerAdapter mCarRecyclerAdapter;

    //This will be our page counter default as one
    private int mPageCounter = 0;

    //Max items
    private int mMaxItems = 10;

    //default sorting
    private String mSortBy = "newest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //boiler plate
        setContentView(R.layout.activity_main);

        //bind views
        ButterKnife.bind(this);

        //set actionbar
        setSupportActionBar(toolbar);


        //add listener to swiperefreshlayout and set color scheme
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        //Initialize adapter
        mCarRecyclerAdapter = new CarRecyclerAdapter(this);

        //reyclerview required initialization
        mRecyclerViewCarList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewCarList.setHasFixedSize(true);
        mRecyclerViewCarList.setAdapter(mCarRecyclerAdapter);

        //initialize the presenter
        mainPresenter = new MainPresenter(this);


        //add listener to recyclerview for pagination
        recyclerViewListener();


        //request first data
        requestFreshData();

    }

    private void recyclerViewListener() {


        mRecyclerViewCarList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mRecyclerViewCarList.canScrollVertically(1)) {
                    mainPresenter.requestCarmudiData(mPageCounter, mMaxItems, mSortBy);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {

            case R.id.action_oldest:

                mSortBy = "oldest";

                requestFreshData();

                break;

            case R.id.action_newest:

                mSortBy = "newest";

                requestFreshData();


                break;
            case R.id.action_price_low:

                mSortBy = "price-low";


                requestFreshData();

                break;

            case R.id.action_price_high:

                mSortBy = "price-high";


                requestFreshData();

                break;
            case R.id.action_mileage_low:

                mSortBy = "mileage-low";


                requestFreshData();

                break;

            case R.id.action_mileage_high:


                mSortBy = "mileage-high";

                requestFreshData();

                break;


        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCarmudiDataRequest() {
        //Show loading dialog

        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onCarmudiDataRequestFinished(boolean isSuccessful, String responseMessage, CarmudiResponse carmudiResponse) {
        //Dismiss loading dialog
        mSwipeRefreshLayout.setRefreshing(false);

        //increment counter
        mPageCounter++;



        if (carmudiResponse != null) {

            //set the carmudi response
            mCarRecyclerAdapter.setNewCarmudiData(carmudiResponse);


        }

    }

    @Override
    public void onRefresh() {

        requestFreshData();
    }

    private void requestFreshData() {


        //set back our page counter to 1
        mPageCounter = 1;

        //clear data first
        mCarRecyclerAdapter.clearOldData();

        //request new daya
        mainPresenter.requestCarmudiData(mPageCounter, mMaxItems, mSortBy);
    }
}
