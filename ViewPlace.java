package com.example.parkhappy3;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.parkhappy3.Model.Photos;
import com.example.parkhappy3.Model.PlaceDetail;
import com.example.parkhappy3.Remote.IGoogleAPIService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Random;


public class ViewPlace extends AppCompatActivity {

    public static ViewPlace newInstance() {
        ViewPlace fragment = new ViewPlace();
        return fragment;
    }


    ImageView photo;
    RatingBar ratingBar;
    TextView opening_hours, place_address, place_name, spaces_total, spaces_left;
    Button btnViewOnMap;

    IGoogleAPIService mService;

    PlaceDetail mPlace;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);

        mService = Common.getGoogleAPIService();

        photo = (ImageView)this.findViewById(R.id.photo);
        ratingBar = (RatingBar)this.findViewById(R.id.ratingBar);
        place_address = (TextView)this.findViewById(R.id.place_address);
        place_name = (TextView)this.findViewById(R.id.place_name);
        opening_hours = (TextView)this.findViewById(R.id.place_open_hour);
        spaces_total = (TextView)this.findViewById(R.id.spaces_total);
        spaces_left = (TextView)this.findViewById(R.id.spaces_left);
        btnViewOnMap = (Button)this.findViewById(R.id.btn_show_map);

        //empty view

        place_name.setText("");
        place_address.setText("");
        opening_hours.setText("");
        spaces_total.setText("");
        spaces_left.setText("");







        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });

        // Photo
        if(Common.currentResult.getPhotos() != null && Common.currentResult.getPhotos().length > 0)
        {
            Picasso.with(this)
                    .load(getPhotoOfPlace (Common.currentResult.getPhotos()[0].getPhoto_reference(),1000))// get photos is array so we will take first item
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .error(R.drawable.ic_error_black_24dp)

                    .into(photo);


        }
        
        // Rating
        if(Common.currentResult.getRating() != null && !TextUtils.isEmpty(Common.currentResult.getRating()))
        {
            ratingBar.setRating(Float.parseFloat(Common.currentResult.getRating()));
        }
        else
        {
            ratingBar.setVisibility(View.GONE);
        }
        
        // Opening hours
        if(Common.currentResult.getOpening_hours() != null)
        {
            opening_hours.setText("Open now : "+Common.currentResult.getOpening_hours().getOpen_now());
        }
        else
        {
            opening_hours.setText("Open now : unknown");
        }

        Random rand = new Random();

        int random1 = rand.nextInt(1000) + 100; //generate random numbers from 1-1000

        spaces_left.setText("Total Spaces : " + Integer.toString(random1)); //n1 is a TextView

        int random2 = rand.nextInt(100) + 100; //generate random numbers from 1-1000

        final int i = random1 - random2;


        spaces_total.setText("Spaces left: " + Integer.toString(i)); //n1 is a TextView




        //user service to fetch address and name
        mService.getDetailPlace(getPlaceDetailUrl(Common.currentResult.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
                        mPlace = response.body();

                        place_address.setText(mPlace.getResult().getFormatted_address());
                        place_name.setText(mPlace.getResult().getName());
                        
                    }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });

        return;

    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid="+place_id);
        url.append("&key="+"AIzaSyCcLAqsPjn7MqcJR_SlgZv4X81JEBJc_G8");
        return url.toString();

    }

    private String getPhotoOfPlace(String photo_reference, int maxWidth) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth="+maxWidth);
        url.append("&photoreference="+photo_reference);
        url.append("&key="+"AIzaSyCcLAqsPjn7MqcJR_SlgZv4X81JEBJc_G8");
        return url.toString();
    }
}
