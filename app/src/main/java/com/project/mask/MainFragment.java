package com.project.mask;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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
                links = doc.select("div .condition li");
                int i = 1;
                for(Element e : links){
                    result.put("status"+i, e.text().substring(e.text().indexOf(" ")+1, e.text().length()));
                    i++;
            }

                links = doc.select("div .skinTb-wrapper .txt-c");
                i = 1;
                for(Element e : links){
                    result.put("local"+i, e.text());
                    i++;
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

            local.setText(map.get("local2")); //춘천
            local1.setText(map.get("local3")); //원주
            local2.setText(map.get("local4"));  //강릉
            local3.setText(map.get("local5"));  //동해
            local4.setText(map.get("local6")); //태백
            local5.setText(map.get("local7")); //속초
            local6.setText(map.get("local8")); //삼척
            local7.setText(map.get("local9")); //홍천
            local8.setText(map.get("local10")); //횡성
            local9.setText(map.get("local11")); //영월
            local10.setText(map.get("local12")); //평창
            local11.setText(map.get("local13")); //정선
        }
    }

    private class GetKoreaStatus extends AsyncTask<Void, Void, Map<String, String>>{

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String, String> result = new HashMap<String, String>();

            try {
                //질병관리청
                Document doc = (Document) Jsoup.connect("http://ncov.mohw.go.kr/").get();

                //2022-01-30 수정
                Elements links = doc.select("table .ds_table");
                Elements title = doc.select("div .occurrenceStatus .title1 .livedate");

                String standard = title.toString().substring(String.valueOf(title).indexOf("(")+1,String.valueOf(title).indexOf(","));
                result.put("date1", standard);

                /*
                    HTMl Class 가져오기 id는 #id
                    질병관리본부 통계 가져오기
                 */
                links = doc.select("tbody td span");
                int i = 1;
                for(Element e : links){
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
            korea1.setText(map.get("korea1"));
            korea2.setText(map.get("korea2"));
            korea3.setText(map.get("korea3"));
            korea4.setText(map.get("korea4"));
            date.setText(map.get("date1"));
        }
    }
}

