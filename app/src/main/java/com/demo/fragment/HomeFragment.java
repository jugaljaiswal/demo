package com.demo.fragment;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.adapter.DemoAdapter;
import com.demo.been.DemoBeen;
import com.demo.database.DataBaseHelper;
import com.demo.main.R;
import com.demo.other.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {

    RecyclerView mRecyclerView;
    public static ListView listview;
    private LinearLayout ll;
    private View viewR;
    private DemoBeen demoBeen;
    Timer timer;
    DataBaseHelper dataBaseHelper;
    private ArrayList<DemoBeen> demoBeenArrayList = new ArrayList<DemoBeen>();

    TimerTask timerTask;
    Handler handler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_recycler_view, container, false);
        dataBaseHelper= new DataBaseHelper(getActivity());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        if (Singleton.checkInternetConenction(getActivity())) {

            startTimer();

        }
        else
        {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }



        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //mRecyclerView.setAdapter(new DataBeanAdapter(dbAdapter.getAllData(), R.layout.item));




        // Inflate the layout for this fragment
        return rootView;
    }

    public void startTimer() {
        //set a new Timer
        handler=new Handler();
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 0, 900000); //
    }

    public void stoptimertask()
    {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void initializeTimerTask()
    {

        timerTask = new TimerTask()
        {
            public void run()
            {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable()
                {
                    public void run()
                    {
                        //get the current timeStamp
                            new GetDemoInfoTask().execute();
                    }
                });
            }
        };
    }


    class GetDemoInfoTask extends AsyncTask<Void, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            String response = Singleton.readStream(Singleton.getUrlApi());
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            demoBeenArrayList.clear();
            if (s != null)
            {
                System.out.println("====>"+s);

                try
                {
                    JSONObject jsonObject = new JSONObject(s);
                    int result_count = jsonObject.getInt("result_count");
                    JSONArray ja = jsonObject.getJSONArray("data");
                    if (result_count>0)
                    {

                        for (int i = 0; i < ja.length(); i++) {

                            JSONObject person = ja.getJSONObject(i);
                            String title = person.getString("title");
                            if(!title.equals("null"))
                            {

                                String id = String.valueOf(person.getInt("id"));
                                String uri = person.getString("uri");

                                DemoBeen sb = new DemoBeen(id,title,uri);

                                if(dataBaseHelper.isALreadHasStockDetails(id))
                                {
                                    dataBaseHelper.updateDemo(sb);
                                }
                                else
                                {
                                    dataBaseHelper.createDemo(sb);
                                }
                                //dataBaseHelper.createDemo(sb);
                                //demoBeenArrayList.add(sb);


                            }



                        }
                    }
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Check Internet Connection",Toast.LENGTH_SHORT);

                }


                demoBeenArrayList = dataBaseHelper.getAllDemo();
                DemoAdapter stockadapter = new DemoAdapter(getActivity(), demoBeenArrayList);
                mRecyclerView.setAdapter(stockadapter);


            }

            else
            {
                Log.e("", "Response is Null");
            }

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
