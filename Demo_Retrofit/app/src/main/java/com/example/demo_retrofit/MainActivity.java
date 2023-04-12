package com.example.demo_retrofit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycle=findViewById(R.id.recycle);


        listingdata();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recycle.setLayoutManager(linearLayoutManager);

    }

    private void listingdata() {

        ApiInterface apiInterface=Retrofit.getRetrofit().create(ApiInterface.class);
        Call<Pojo>listingdata=apiInterface.getData();

        listingdata.enqueue(new Callback<Pojo>() {
            @Override
            public void onResponse(Call<Pojo> call, Response<Pojo> response) {
                if(response.isSuccessful()){


                    recycleadapter adapter=new recycleadapter(response.body().getData());
                    recycle.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Pojo> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failure",Toast.LENGTH_SHORT);



            }
        });
    }


    class recycleadapter extends RecyclerView.Adapter<recycleadapter.MyViewholder>{

        List<Pojo.Datum> list;
        Context context;

        public recycleadapter(List<Pojo.Datum> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public recycleadapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,null);
            recycleadapter.MyViewholder viewholder=new recycleadapter.MyViewholder(view);
            return viewholder;
        }

        @Override
        public void onBindViewHolder(@NonNull recycleadapter.MyViewholder holder, int position) {

            holder.email.setText(list.get(position).getEmail());
            holder.first_name.setText(list.get(position).getFirstName());
            holder.last_name.setText(list.get(position).getLastName());


            Picasso.with(getApplicationContext())
                    .load(list.get(position).getAvatar())
                    .placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .into(holder.imgs);

        }

        @Override
        public int getItemCount() {
            return list.size() ;
        }
        class MyViewholder extends RecyclerView.ViewHolder{


            TextView userid,email,first_name,last_name;
            ImageView imgs;

            public MyViewholder(@NonNull View itemView) {
                super(itemView);


                userid=itemView.findViewById(R.id.userid);
                email=itemView.findViewById(R.id.email);
                first_name=itemView.findViewById(R.id.first_name);
                last_name=itemView.findViewById(R.id.last_name);
                imgs=itemView.findViewById(R.id.imgs);


            }
        }
    }
}