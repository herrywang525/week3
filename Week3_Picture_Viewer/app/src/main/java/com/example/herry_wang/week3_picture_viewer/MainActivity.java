package com.example.herry_wang.week3_picture_viewer;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    private ImageButton mountainButton,sunsetButton,roadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Picture Viewer");
        mountainButton = (ImageButton) findViewById(R.id.mountain_button);
        sunsetButton = (ImageButton) findViewById(R.id.sunset_button);
        roadButton = (ImageButton) findViewById(R.id.road_button);

        mountainButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ImageView a = (ImageView)findViewById(R.id.big);
                a.setImageDrawable(getResources().getDrawable( R.drawable.wallpaper_mountain ));
            }

        });
        sunsetButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ImageView a = (ImageView)findViewById(R.id.big);
                a.setImageDrawable(getResources().getDrawable( R.drawable.wallpaper_sunset ));
            }

        });
        roadButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ImageView a = (ImageView)findViewById(R.id.big);
                a.setImageDrawable(getResources().getDrawable( R.drawable.wallpaper_road ));
            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
