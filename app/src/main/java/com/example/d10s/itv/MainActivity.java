package com.example.d10s.itv;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private String Youtube_Api_Key = "AIzaSyCws0kKoA_QxAZmsjiSqR1KFxGjLzfwkG4";
    private static String urlString;
    public String Base_Uri = "http://thetvdb.com/api/";
    public String tvdbkey = "94421EDA0B4A37C8";
    public String what = "/series/80379/es.xml";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.tv);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText("Holaaa!!!");
                urlString = Base_Uri + tvdbkey + what;
                new ProcessJSON().execute(urlString);
                Toast.makeText(getApplicationContext(), "Acción del boton", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class ProcessJSON extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream){
            TextView tv = (TextView) findViewById(R.id.tv);
            tv.setText("Adios!!!");
            //tv.setText(stream);

            /*
                Important in JSON DATA
                -------------------------
                * Square bracket ([) represents a JSON array
                * Curly bracket ({) represents a JSON object
                * JSON object contains key/value pairs
                * Each key is a String and value may be different data types
             */

            //..........Process JSON DATA................
            if(stream !=null){
                try{
                    tv.setText("Stream es no nulo!!!!");

                    int lon11 = stream.indexOf("<id>");
                    int lon12 = stream.indexOf("</id>");
                    String id = stream.substring(lon11+4,lon12);
                    tv.append("\nSerie Id: "+id);

                    int lon21 = stream.indexOf("<SeriesName>");
                    int lon22 = stream.indexOf("</SeriesName>");
                    String serie = stream.substring(lon21+12,lon22);
                    tv.append("\nNombre: "+serie);

                    int lon31 = stream.indexOf("<Actors>");
                    int lon32 = stream.indexOf("</Actors>");
                    String actores = stream.substring(lon31+8,lon32);
                    tv.append("\nActores: "+actores);

                    int lon41 = stream.indexOf("<Overview>");
                    int lon42 = stream.indexOf("</Overview>");
                    String over = stream.substring(lon41+10,lon42);
                    tv.append("\nSinopsis: "+over);

                    int lon51 = stream.indexOf("<Rating>");
                    int lon52 = stream.indexOf("</Rating>");
                    String rating = stream.substring(lon51+8,lon52);
                    tv.append("\nRating: "+rating);



                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);

                }catch(JSONException e){
                    e.printStackTrace();
                }

            } // if statement end
            else if (stream == null)
                tv.setText("Stream nulo!!!");
        } // onPostExecute() end
    } // ProcessJSON class end

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
