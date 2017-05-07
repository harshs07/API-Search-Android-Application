package com.mobile_computing;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ResultDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_display);

      TextView text = (TextView) findViewById(R.id.text);
        TextView title = (TextView) findViewById(R.id.title);
        TextView date = (TextView) findViewById(R.id.date);
        NetworkImageView image = (NetworkImageView) findViewById(R.id.img);
       // TextView heading = (TextView) findViewById(R.id.text);
        ImageLoader imgLoad = VolleySingleton.getInstance(this).getImageLoader();

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
       Datum temp = (Datum) i.getSerializableExtra("sampleObject");
        text.setText(temp.text());
        title.setText(temp.title());
        date.setText(temp.date());
        image.setImageUrl(temp.imageUrl(),imgLoad);
       // getActionBar().setTitle("Book: "+temp.title());
        setTitle("Book: "+temp.title());
        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String tem=String.valueOf(temp.id());
        int ID=sharedPreferences.getInt(tem,0);
        if(ID==1){

            CheckBox myHiddenView = (CheckBox)findViewById(R.id.starButton);
           myHiddenView.setChecked(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                String msg = "Taking you back " ;

                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        Intent i = getIntent();
        Datum temp = (Datum) i.getSerializableExtra("sampleObject");

        if(checked){
            favorite(temp.id(),1);
        }
        else{
            favorite(temp.id(),0);
        }

    }

    public void favorite(int x,int a) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(x), a);

        editor.commit();
        if (a == 0) {
            Toast toast = Toast.makeText(this, "Removed " + String.valueOf(x) + " from favorites", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Toast toast = Toast.makeText(this, "Added " + String.valueOf(x) + " to favorites", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    @Override
    public void onBackPressed() {

        this.finish();
    }

}
