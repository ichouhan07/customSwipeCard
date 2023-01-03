package com.applligent.swipecard2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    int windowWidth;
    int windowHeight;
    int screenCenterWidth;
    int screenCenterHeight;
    int x_cord, y_cord, x, y;
    int Likes = 0;
    public RelativeLayout parentView;
    float alphaValue = 0;
    private Context context;

    ArrayList<UserDataModel> userDataModelArrayList;

    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        parentView = (RelativeLayout) findViewById(R.id.main_layoutview);
        windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        screenCenterWidth = windowWidth / 2;
        screenCenterHeight = windowHeight / 2;
        Log.i("onCreate8bjg8", String.valueOf(screenCenterHeight));
        userDataModelArrayList = new ArrayList<>();
        getArrayData();

        for (int i = 0; i < userDataModelArrayList.size(); i++) {
            LayoutInflater inflate = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") final View containerView = inflate.inflate(R.layout.custom_tinder_layout, null);
            ImageView userIMG = containerView.findViewById(R.id.userIMG);

            RelativeLayout relativeLayoutContainer = (RelativeLayout) containerView.findViewById(R.id.relative_container);
            relativeLayoutContainer.setBackgroundResource(R.color.white);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            containerView.setLayoutParams(layoutParams);
            containerView.setTag(i);
            userIMG.setBackgroundResource(userDataModelArrayList.get(i).getPhoto());

//            m_view.setRotation(i);
//            containerView.setPadding(0, i, 0, 0);
            LinearLayout.LayoutParams layoutTvParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //ADD dynamically like TextView on image.
            final TextView tvLike = new TextView(context);
            tvLike.setLayoutParams(layoutTvParams);
            tvLike.setPadding(10, 10, 10, 10);
            tvLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvLike.setText("LIKE");
            tvLike.setGravity(Gravity.CENTER);
            tvLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvLike.setTextSize(30);
            tvLike.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvLike.setX(20);
            tvLike.setY(100);
            tvLike.setRotation(-50);
            tvLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvLike);

            //ADD dynamically dislike TextView on image.
            final TextView tvUnLike = new TextView(context);
            tvUnLike.setLayoutParams(layoutTvParams);
            tvUnLike.setPadding(10, 10, 10, 10);
            tvUnLike.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvUnLike.setText("UNLIKE");
            tvUnLike.setGravity(Gravity.RIGHT);
            tvUnLike.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvUnLike.setTextSize(30);
            tvUnLike.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvUnLike.setX(480);
            tvUnLike.setY(150);
            tvUnLike.setRotation(50);
            tvUnLike.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvUnLike);

            //ADD dynamically shared TextView on image.
            final TextView tvShared = new TextView(context);
            tvShared.setLayoutParams(layoutTvParams);
            tvShared.setPadding(10, 10, 10, 10);
            tvShared.setBackgroundDrawable(getResources().getDrawable(R.drawable.btnlikeback));
            tvShared.setText("SHARED");
            tvShared.setGravity(Gravity.CENTER);
            tvShared.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvShared.setTextSize(35);
            tvShared.setTextColor(ContextCompat.getColor(context, R.color.white));
            tvShared.setX(200);
            tvShared.setY(700);
            tvShared.setRotation(0);
            tvShared.setAlpha(alphaValue);
            relativeLayoutContainer.addView(tvShared);

            TextView tvName = (TextView) containerView.findViewById(R.id.tvName);
            TextView tvTotLikes = (TextView) containerView.findViewById(R.id.tvTotalLikes);

            tvName.setText(userDataModelArrayList.get(i).getName());
            tvTotLikes.setText(userDataModelArrayList.get(i).getTotalLikes());

            relativeLayoutContainer.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    x_cord = (int) event.getRawX();
                    y_cord = (int) event.getRawY();

                    containerView.setX(0);
                    containerView.setY(0);

                    switch (event.getAction()){
                        //when user press screen
                        case MotionEvent.ACTION_DOWN:
                            x = (int) event.getX();
                            y = (int) event.getY();
                            Log.v("On touch", x + " " + y);
                            break;
                        //when user drive press screen
                        case MotionEvent.ACTION_MOVE:
                            x_cord = (int) event.getRawX();
                            // smoother animation.
                            y_cord = (int) event.getRawY();

                            containerView.setX(x_cord - x);
                            containerView.setY(y_cord - y);

                            //Log.i("onCreate12345", String.valueOf(x_cord));
                            Log.i("onCreate123456", String.valueOf(y_cord));

                            //for liked
                            if (x_cord >= screenCenterWidth) {
                                containerView.setRotation((float) ((x_cord - screenCenterWidth) * (Math.E / 120)));
                                if (x_cord > (screenCenterWidth + (screenCenterWidth / 2))) {
                                    tvLike.setAlpha(1);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorBlue2);
                                    /*if (x_cord >590){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorBlue1);
                                    }
                                    if (x_cord >640){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorBlue2);
                                    }
                                    if (x_cord >690){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorBlue3);
                                    }*/
                                    if (x_cord > (windowWidth - (screenCenterWidth / 4))) {
                                        Likes = 2;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvLike.setAlpha(0);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorBlue);
                                }
                                tvUnLike.setAlpha(0);
                                tvShared.setAlpha(0);
                            }
                            //for shared
                            else if (y_cord < screenCenterHeight && x_cord > screenCenterWidth / 4){
                                //containerView.setRotation((float) ((y_cord - screenCenterHeight) * (Math.PI / 99)));
                                if (y_cord < screenCenterHeight/2 + screenCenterHeight /4 && x_cord > screenCenterWidth / 4 ) {
                                    tvShared.setAlpha(1);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorPurple1);
                                    /*if (x_cord <500){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorPurple1);
                                    }*/
                                    if (y_cord < screenCenterHeight /4 && x_cord > screenCenterWidth / 4) {
                                        Likes = 4;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvShared.setAlpha(0);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorPurple);
                                }
                                tvUnLike.setAlpha(0);
                                tvLike.setAlpha(0);
                            }
                            //for unliked
                            else {
                                // rotate image while moving
                                containerView.setRotation((float) ((x_cord - screenCenterWidth) * (Math.E / 120)));
                                if (x_cord < (screenCenterWidth / 2)) {
                                    tvUnLike.setAlpha(1);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorRed3);
                                    /*if (x_cord <120){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorRed1);
                                    }
                                    if (x_cord <80){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorRed2);
                                    }
                                    if (x_cord<40){
                                        relativeLayoutContainer.setBackgroundResource(R.color.colorRed3);
                                    }*/
                                    if (x_cord < screenCenterWidth / 4) {
                                        Likes = 1;
                                    } else {
                                        Likes = 0;
                                    }
                                } else {
                                    Likes = 0;
                                    tvUnLike.setAlpha(0);
                                    relativeLayoutContainer.setBackgroundResource(R.color.colorRed);
                                }
                                tvLike.setAlpha(0);
                                tvShared.setAlpha(0);
                            }
                            break;
                        //when user press leave screen
                        case MotionEvent.ACTION_UP:
                            x_cord = (int) event.getRawX();
                            y_cord = (int) event.getRawY();
                            Log.e("X Point", "" + x_cord + " , Y " + y_cord);
                            tvUnLike.setAlpha(0);
                            tvLike.setAlpha(0);
                            tvShared.setAlpha(0);
                            relativeLayoutContainer.setBackgroundResource(R.color.white);
                            if (Likes == 0) {
                                Toast.makeText(context, "NOTHING", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Nothing");
                                containerView.setX(0);
                                containerView.setY(0);
                                containerView.setRotation(0);
                            } else if (Likes == 1) {
                                Toast.makeText(context, "UNLIKE", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "UNLIKE");
                                parentView.removeView(containerView);
                            } else if (Likes == 2) {
                                Toast.makeText(context, "LIKED", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "Liked");
                                parentView.removeView(containerView);
                            }else if (Likes == 4){
                                Toast.makeText(context, "SHARED", Toast.LENGTH_SHORT).show();
                                Log.e("Event_Status :-> ", "SHARED");
                                parentView.removeView(containerView);
                            }
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            parentView.addView(containerView);
        }
    }
    private void getArrayData(){
        UserDataModel model = new UserDataModel();
        model.setName("Nimblechapps 1 ");
        model.setTotalLikes("3 M");
        model.setPhoto(R.drawable.image1);
        userDataModelArrayList.add(model);


        UserDataModel model2 = new UserDataModel();
        model2.setName("Nimblechapps 2 ");
        model2.setTotalLikes("2 M");
        model2.setPhoto(R.drawable.image2);
        userDataModelArrayList.add(model2);

        UserDataModel model3 = new UserDataModel();
        model3.setName("Nimblechapps 3 ");
        model3.setTotalLikes("3 M");
        model3.setPhoto(R.drawable.image3);
        userDataModelArrayList.add(model3);


        UserDataModel model4 = new UserDataModel();
        model4.setName("Nimblechapps 4 ");
        model4.setTotalLikes("4 M");
        model4.setPhoto(R.drawable.image1);
        userDataModelArrayList.add(model4);


        UserDataModel model5 = new UserDataModel();
        model5.setName("Nimblechapps 5 ");
        model5.setTotalLikes("5 M");
        model5.setPhoto(R.drawable.image2);
        userDataModelArrayList.add(model5);

        UserDataModel model6 = new UserDataModel();
        model6.setName("Nimblechapps 6 ");
        model6.setTotalLikes("6 M");
        model6.setPhoto(R.drawable.image3);
        userDataModelArrayList.add(model6);


        UserDataModel model7 = new UserDataModel();
        model7.setName("Nimblechapps 7 ");
        model7.setTotalLikes("7 M");
        model7.setPhoto(R.drawable.image1);
        userDataModelArrayList.add(model7);


        UserDataModel model8 = new UserDataModel();
        model8.setName("Nimblechapps 8 ");
        model8.setTotalLikes("8 M");
        model8.setPhoto(R.drawable.image2);
        userDataModelArrayList.add(model8);

        UserDataModel model9 = new UserDataModel();
        model9.setName("Nimblechapps 9 ");
        model9.setTotalLikes("9 M");
        model9.setPhoto(R.drawable.image3);
        userDataModelArrayList.add(model9);

        Collections.reverse(userDataModelArrayList);
    }
}