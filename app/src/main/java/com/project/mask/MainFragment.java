package com.project.mask;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private TextView korea1;
    private TextView korea2;
    private TextView korea3;
    private TextView korea4;
    private TextView date;

    private TextView kangwon1;
    private TextView kangwon2;
    private TextView kangwon3;
    private TextView kangwon4;
    private TextView date1;

    private TextView local;
    private TextView local1;
    private TextView local2;
    private TextView local3;
    private TextView local4;
    private TextView local5;
    private TextView local6;
    private TextView local7;
    private TextView local8;
    private TextView local9;
    private TextView local10;
    private TextView local11;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        korea1 = rootView.findViewById(R.id.korea1);
        korea2 = rootView.findViewById(R.id.korea2);
        korea3 = rootView.findViewById(R.id.korea3);
        korea4 = rootView.findViewById(R.id.korea4);
        date = rootView.findViewById(R.id.date);


        kangwon1 = rootView.findViewById(R.id.kangwon1);
        kangwon2 = rootView.findViewById(R.id.kangwon2);
        kangwon3 = rootView.findViewById(R.id.kangwon3);
        kangwon4 = rootView.findViewById(R.id.kangwon4);
        date1 = rootView.findViewById(R.id.date1);

        local = rootView.findViewById(R.id.local);
        local1 = rootView.findViewById(R.id.local1);
        local2 = rootView.findViewById(R.id.local2);
        local3 = rootView.findViewById(R.id.local3);
        local4 = rootView.findViewById(R.id.local4);
        local5 = rootView.findViewById(R.id.local5);
        local6 = rootView.findViewById(R.id.local6);
        local7 = rootView.findViewById(R.id.local7);
        local8 = rootView.findViewById(R.id.local8);
        local9 = rootView.findViewById(R.id.local9);
        local10 = rootView.findViewById(R.id.local10);
        local11 = rootView.findViewById(R.id.local11);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        GetKoreaStatus task2 = new GetKoreaStatus();
        task2.execute();

        GetKangwonStatus task = new GetKangwonStatus();
        task.execute();
    }

        private class GetKangwonStatus extends AsyncTask<Void, Void, Map<String, String>>{

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> result = new HashMap<String, String>();

            try {
                //강원도청 HTML 가져오기
                Document doc = (Document) Jsoup.connect("https://www.provin.gangwon.kr/covid-19.html").get();
                Elements links = doc.select("div.condition h3 span");

                //기준일 가져오기
                result.put("date2", links.toString().replace("<span>","").replace("</span>",""));

                /*
                    HTMl Class 가져오기 id는 #id
                    강원도내 확진자 가져오기
                 */
                links = doc.select("div.condition li");
                int i = 1;
                for(Element e : links){
                    result.put("status"+i, e.text().substring(e.text().indexOf(" ")+1, e.text().length()-1));
                    i++;
                }

                //order by 춘천, 원주, 강릉, 태백, 속초, 삼척, 횡성, 영월, 철원, 인제, 양양, 서울
                links = doc.select("div.condition .memo");
                String localTotal = links.toString().substring(links.toString().indexOf("춘"), links.toString().lastIndexOf("<br>")).replace(" ","");
                String[] local = localTotal.split(",");
                for(int j=0;j<local.length;j++){
                    result.put("local"+j, local[j].substring(local[j].indexOf("(")+1,local[j].indexOf(")")));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {
            kangwon1.setText(map.get("status1"));
            kangwon2.setText(map.get("status2"));
            kangwon3.setText(map.get("status3"));
            kangwon4.setText(map.get("status4"));
            date1.setText(map.get("date2"));

            local.setText(map.get("local0"));
            local1.setText(map.get("local1"));
            local2.setText(map.get("local2"));
            local3.setText(map.get("local3"));
            local4.setText(map.get("local4"));
            local5.setText(map.get("local5"));
            local6.setText(map.get("local6"));
            local7.setText(map.get("local7"));
            local8.setText(map.get("local8"));
            local9.setText(map.get("local9"));
            local10.setText(map.get("local10"));
            local11.setText(map.get("local11"));
        }
    }

    private class GetKoreaStatus extends AsyncTask<Void, Void, Map<String, String>>{

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> result = new HashMap<String, String>();

            try {
                //전국 코로나 HTML 가져오기
                Document doc = (Document) Jsoup.connect("http://ncov.mohw.go.kr/").get();
                Elements links = doc.select("div .liveNumOuter .livedate");

                //기준일 가져오기
                result.put("date1",links.toString().substring(String.valueOf(links).indexOf("(")+1,String.valueOf(links).indexOf(")")));

                /*
                    HTMl Class 가져오기 id는 #id
                    질병관리본부 통계 가져오기
                 */
                links = doc.select("div .liveNumOuter .num");
                int i = 1;
                for(Element e : links){
                    //Log.d("dddd",e.text());
                    result.put("korea"+i, e.text());
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {
            korea1.setText(map.get("korea1").replace("(누적)",""));
            korea2.setText(map.get("korea2"));
            korea3.setText(map.get("korea3"));
            korea4.setText(map.get("korea4"));
            date.setText(map.get("date1"));
        }
    }


}

