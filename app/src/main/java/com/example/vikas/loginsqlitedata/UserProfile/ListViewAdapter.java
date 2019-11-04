package com.example.vikas.loginsqlitedata.UserProfile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vikas.loginsqlitedata.R;


public class ListViewAdapter extends BaseAdapter {

    Context context;
    private int[] images;
    private String[] profileDataObject;
    private String[] profileDataSubject;


    public ListViewAdapter(Context context, int[] images, String[] profileDataObject, String[] profileDataSubject) {
        this.context = context;
        this.images = images;
        this.profileDataObject = profileDataObject;
        this.profileDataSubject = profileDataSubject;
    }

    @Override
    public int getCount() {
        return profileDataObject.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        TextView object, subject;
        //----------To get services of llayoutInflater this following line is required--------------//
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.user_profile_item_fetch_data, null);

        imageView = view.findViewById(R.id.image_view_list_view_profile);
        subject = view.findViewById(R.id.subject_profile_listview_text_view);
        object = view.findViewById(R.id.object_name_profile_list_view_text);

        imageView.setImageResource(images[position]);
        subject.setText(profileDataSubject[position]);
        object.setText(profileDataObject[position]);


        return view;
    }
}
