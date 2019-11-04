package com.example.vikas.loginsqlitedata.HomeScreen;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.vikas.loginsqlitedata.BeautyProducts.BeautyProductsMainFragment;
import com.example.vikas.loginsqlitedata.DailyNeedProducts.DailyNeedProductsFragment;
import com.example.vikas.loginsqlitedata.ElectronicsProducts.ElectronicsProductsMainFragment;
import com.example.vikas.loginsqlitedata.FashionProduct.FashionProductsFragment;
import com.example.vikas.loginsqlitedata.MenProducts.MenProducsFragment;
import com.example.vikas.loginsqlitedata.R;
import com.example.vikas.loginsqlitedata.SportsProducts.SportsProductsMainFragment;
import com.example.vikas.loginsqlitedata.WomanProducts.WomansProductMainFragment;
import com.example.vikas.loginsqlitedata.kidsProducts.KidsProductsMainFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<String> images=new ArrayList<>();
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private int [] names={};
    private Context mContext;
    private String checkPhone;

    public HorizontalRecyclerViewAdapter(Context mContext, ArrayList<String> images, int[] names,String checkPhone) {
        this.images = images;
        this.names = names;
        this.mContext = mContext;
        this.checkPhone=checkPhone;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_view_items,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        holder.namesTextView.setText(names[position]);

        Glide.with(mContext)
                .asBitmap()
                .load(images.get(position))
                .into(holder.circleImageView);

        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //--------------this appCompatActivity is require to get getSupportFragmentManager() function--------------//
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                toolbar=activity.findViewById(R.id.action_toolbar);

                if(images.get(position).equals("https://image.ibb.co/dHsb2T/horizontal_recycler_view_women_jpg.jpg"))
                {
                    toolbar.setTitle("Women's Products");
                    WomansProductMainFragment myFragment = new WomansProductMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();
                }
                else if(images.get(position).equals("https://image.ibb.co/hnRpp8/men_horizontal_recycler_view.jpg"))
                {
                    toolbar.setTitle("Men Products");
                    MenProducsFragment myFragment=new  MenProducsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://preview.ibb.co/eg8Vwo/fashion_horizontal_recycler_view.jpg"))
                {
                    toolbar.setTitle("Fashion Products");
                    FashionProductsFragment myFragment=new FashionProductsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://preview.ibb.co/iuxUp8/sports_horizontal_recycler_view.jpg" ))
                {
                    toolbar.setTitle("Sports Products");
                    SportsProductsMainFragment myFragment= new SportsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://preview.ibb.co/cXrYGo/kids_horizontal_recycler_view.jpg" ))
                {
                    toolbar.setTitle("Kids Products");
                    KidsProductsMainFragment myFragment= new KidsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://preview.ibb.co/mDkwK8/electronics_products.jpg"))
                {
                    toolbar.setTitle("Electronics Products");
                    ElectronicsProductsMainFragment myFragment= new ElectronicsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://preview.ibb.co/bA7vU8/daily_need_horizontal_recycler_view.jpg"))
                {
                    toolbar.setTitle("Daily Need Products");
                    DailyNeedProductsFragment myFragment= new DailyNeedProductsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(images.get(position).equals("https://image.ibb.co/hnfPp8/beauty_horizontal_recycler_view.jpg"))
                {
                    toolbar.setTitle("Beauty Products");
                    BeautyProductsMainFragment myFragment= new BeautyProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }

            }
        });
        holder.namesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //--------------this appCompatActivity is require to get getSupportFragmentManager() function--------------//
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                toolbar=activity.findViewById(R.id.action_toolbar);


                if(names[position]==R.string.WomenHorizontal)
                {
                    toolbar.setTitle("Women Products");
                    WomansProductMainFragment myFragment=new WomansProductMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.MenHorizontal)
                {
                    toolbar.setTitle("Men Products");
                    MenProducsFragment myFragment=new MenProducsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.FashionHorizontal)
                {
                    toolbar.setTitle("Fashion Products");
                    FashionProductsFragment myFragment=new FashionProductsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.SportsHorizontal)
                {
                    toolbar.setTitle("Sports's Products");
                    SportsProductsMainFragment myFragment=new SportsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.KidsHorizontal)
                {
                    toolbar.setTitle("Kids Products");
                    KidsProductsMainFragment myFragment=new KidsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.ElectronicsHorizontal)
                {
                    toolbar.setTitle("Electronics Products");
                    ElectronicsProductsMainFragment myFragment=new ElectronicsProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();

                }
                else if(names[position]==R.string.DailyNeedHorizontal)
                {
                    toolbar.setTitle("Daily Need Products");
                    DailyNeedProductsFragment myFragment= new DailyNeedProductsFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();
                }
                else if(names[position]==R.string.BeautyHorizontal)
                {
                    toolbar.setTitle("Beauty Products");
                    BeautyProductsMainFragment myFragment= new BeautyProductsMainFragment(checkPhone);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment ).addToBackStack(null).commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private CircleImageView circleImageView;
        private TextView namesTextView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.horizontal_recycler_view_card_view);
            circleImageView=itemView.findViewById(R.id.circulerImageView);
            namesTextView=itemView.findViewById(R.id.name);
        }
    }
}
