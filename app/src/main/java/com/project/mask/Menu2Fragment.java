package com.project.mask;


import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Menu2Fragment extends Fragment {


    private JsonFromServerTask jsonFromServerTask;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PharmacyAdapter adapter;


    ArrayList<Pharmacy> listItem;
    public Menu2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_menu2, container, false);
        listItem = new ArrayList<>();
        adapter = new PharmacyAdapter(getContext());
        recyclerView = rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);

        //주소 입력시 활성화
        final SearchView searchView = rootView.findViewById(R.id.serchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String local) {
                adapter.clearItems();
                jsonFromServerTask = (JsonFromServerTask) new JsonFromServerTask().execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return rootView;
    }

    public class JsonFromServerTask extends AsyncTask<String, Void, Pharmacy[]> {
        private ProgressDialog progressDialog = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("정보를 가져오고 있습니다.");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Pharmacy[] doInBackground(String... strings) {
            try {
                Gson gson = new GsonBuilder().create();
                InputStream inputStream = getContext().getAssets().open("json/mask.json");
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();

                String json = new String(buffer, "UTF-8");

                JsonParser parser = new JsonParser();
                JsonElement element = parser.parse(json);

                JsonElement rootObject = parser.parse(String.valueOf(element.getAsJsonObject().get("stores")));
                System.out.println(rootObject.toString());

                Pharmacy[] pharmcy = gson.fromJson(rootObject, Pharmacy[].class);
                return pharmcy;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

            @Override
        protected void onPostExecute(Pharmacy[] posts) {
            super.onPostExecute(posts);

           if(posts.length > 0){
                for(Pharmacy post : posts){
                    listItem.add(post);
                }
            }
            //adapter.clearItems();
            //adapter.notifyDataSetChanged();
            adapter = new PharmacyAdapter(getContext(), listItem);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }

    /*
    2020-08-03 공적마스크 API 중단으로 인한 코드 Block
    private String apiURL =  "https://8oi9s0nnth.apigw.ntruss.com/corona19-masks/v1/storesByAddr/json?address=";
    private String query = "";

    @Override
    protected Pharmacy[] doInBackground(String... strings) {
        return null;
            /*
            String resultURL = apiURL + query;
            OkHttpClient client = new OkHttpClient.Builder().build();
            //헤더에 인증키 추가
            //Request request = new Request.Builder().addHeader("x-api-key", RestTestCommon.API_KEY).url(requestURL).build();
            Request request = new Request.Builder().url(resultURL).build();
            try {

                Response response = client.newCall(request).execute();
                Gson gson = new GsonBuilder().create();
                JsonParser parser = new JsonParser();
                JsonElement rootObject = parser.parse(response.body().charStream()).getAsJsonObject().get("stores");
                Pharmacy[] pharmcy = gson.fromJson(rootObject, Pharmacy[].class);
                return pharmcy;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }*/

}
