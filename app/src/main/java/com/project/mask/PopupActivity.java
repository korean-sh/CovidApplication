package com.project.mask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class PopupActivity extends AppCompatActivity {

    Button btnClose;
    TextView textView6, textView8, textView9, textView10, textView11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //창 투명하게 하기
        setContentView(R.layout.activity_popup);

        //팝업 창 크기
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int)(display.getWidth() * 0.8);
        int height = (int)(display.getHeight() * 0.9);
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        //리싸이클러뷰 값 가져오기
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String addr = intent.getExtras().getString("addr");
        String remain_stat = intent.getExtras().getString("remain_stat");
        String stock_at = intent.getExtras().getString("stock_at");

        //화면 내용 설정
        textView6 = findViewById(R.id.textView6);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textView6.setText(name);
        textView8.setText(addr);
        textView10.setText(stock_at);

        if (remain_stat != null) {
            if (remain_stat.equals("plenty")) {
                textView9.setText("100개 이상");
            } else if (remain_stat.equals("some")) {
                textView9.setText("30~100개");
            } else if (remain_stat.equals("few")) {
                textView9.setText("2~30개");
            } else if (remain_stat.equals("empty")) {
                textView9.setText("1개");
            } else {
                textView9.setText("판매중지");
            }
        } else {
            textView9.setText("정보없음");
        }

        btnClose = findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
            카카오톡 지도 API 호출
         */

        //Intent로 가져온 함수
        float lan =  intent.getExtras().getFloat("lag");
        float lng = intent.getExtras().getFloat("lng");

        //지도 호출
        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup)findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        //중심점 변경
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(lan, lng), true);

        //줌 레벨 견경 <level이 낮을 수록 확대해서 볼 수 있음>
        mapView.setZoomLevel(3, true);

        //마커 표시
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(lan, lng);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(name);
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);
    }

    //팝업 바깥 눌러도 닫히지 않게 하기
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    //백버튼 막기
    @Override
    public void onBackPressed() {
        return;
    }
}
