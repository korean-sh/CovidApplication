package com.project.mask;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.MyViewHolder> {

    private ArrayList<Pharmacy> items = new ArrayList<Pharmacy>();
    private Context context;

    public PharmacyAdapter(Context context){this.context = context;}

    public PharmacyAdapter(Context context, ArrayList<Pharmacy> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public PharmacyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pharm_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyAdapter.MyViewHolder holder, int position) {
        final Pharmacy item = items.get(position);
        holder.setItem(item);

        //자세히 보기
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //팝업창 열고 값 전달
                Context context = view.getContext();
                Intent intent = new Intent(context, PopupActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("addr",item.getAddr());
                intent.putExtra("remain_stat",item.getRemain_stat());
                intent.putExtra("lag",item.getLat());
                intent.putExtra("lng",item.getLng());
                intent.putExtra("stock_at",item.getStock_at());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItems() {
        this.items.clear();
    }

    public void addItem(Pharmacy item) {
        items.add(item);
    }

    public void setItems(ArrayList<Pharmacy> items) {
        this.items = items;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView,textView2, textView3, textView5;
        public LinearLayout container;
        public final View layout;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView5 = itemView.findViewById(R.id.textView5);
            container = itemView.findViewById(R.id.container);
            layout = itemView;
        }

        /*
          2020-05-13 (2일 소요)
          API 값으로 예상하지 못한 값이 있으므로 NULL값 처리를 해야함 강원도 춘천시 효자동
         */

        public void setItem(final Pharmacy item) {
            textView.setText(item.getName());
            textView3.setText(item.getAddr());
            String check = item.getRemain_stat();

            if (check != null) {
                if (check.equals("plenty")) {
                    textView5.setText("100개 이상");
                    textView5.setTextColor(Color.parseColor("#99cc00"));
                    textView2.setBackgroundColor(Color.parseColor("#99cc00"));
                } else if (check.equals("some")) {
                    textView5.setText("30~100개");
                    textView5.setTextColor(Color.parseColor("#FFcc00"));
                    textView2.setBackgroundColor(Color.parseColor("#FFcc00"));
                } else if (check.equals("few")) {
                    textView5.setText("2~30개");
                    textView5.setTextColor(Color.parseColor("#cc0033"));
                    textView2.setBackgroundColor(Color.parseColor("#cc0033"));
                } else if (check.equals("empty")) {
                    textView5.setText("1개");
                    textView5.setTextColor(Color.parseColor("#666666"));
                    textView2.setBackgroundColor(Color.parseColor("#666666"));
                } else {
                    textView5.setText("판매중지");
                    textView5.setTextColor(Color.parseColor("#cccccc"));
                    textView2.setBackgroundColor(Color.parseColor("#cccccc"));
                }
            } else {
                textView5.setText("정보없음");
                textView5.setTextColor(Color.parseColor("#cccccc"));
                textView2.setBackgroundColor(Color.parseColor("#cccccc"));
            }
        }
    }
}

