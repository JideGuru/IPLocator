package com.jideguru.iplocator.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jideguru.iplocator.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

import static com.jideguru.iplocator.Util.APILinks.IP_BASE_URL;

public class MainActivity extends AppCompatActivity {

    private TextView city, ip_address, region, region_code, country, country_name, continent_code, in_eu, postal, latlong, timezone, country_calling_code, currency, languages, asn, org, myip;
    private EditText ed_ip;
    private Button submit_btn, btn_map;
    public AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ed_ip = (EditText) findViewById(R.id.ip_et);
        ip_address = (TextView) findViewById(R.id.ip_ip);
        city = (TextView) findViewById(R.id.ip_city);
        region = (TextView) findViewById(R.id.ip_region);
        region_code = (TextView) findViewById(R.id.ip_region_code);
        country = (TextView) findViewById(R.id.ip_country);
        country_name= (TextView) findViewById(R.id.ip_country_name);
        continent_code = (TextView) findViewById(R.id.ip_continent);
        country = (TextView) findViewById(R.id.ip_country);
        in_eu = (TextView) findViewById(R.id.ip_eu);
        postal = (TextView) findViewById(R.id.ip_postal);
        latlong = (TextView) findViewById(R.id.ip_longlat);
        timezone = (TextView) findViewById(R.id.ip_timezone);
        postal = (TextView) findViewById(R.id.ip_postal);
        country_calling_code = (TextView) findViewById(R.id.ip_callcode);
        currency = (TextView) findViewById(R.id.ip_curr);
        languages = (TextView) findViewById(R.id.ip_lang);
        asn = (TextView) findViewById(R.id.ip_asn);
        org = (TextView) findViewById(R.id.ip_org);

        myip = (TextView)findViewById(R.id.myip);
        firstip();


        submit_btn = (Button) findViewById(R.id.ip_btn);
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip();
            }
        });

        btn_map = (Button)findViewById(R.id.btn_map);

        MobileAds.initialize(this, "ca-app-pub-2477128915159039~6238114919");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
        if (id == R.id.action_contact) {
            Uri uri = Uri.parse("http://wa.me/22994684468");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://wa.me/22994684468")
                ));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void ip() {
        if(ed_ip.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Please input an IP", Toast.LENGTH_SHORT).show();
        }else {


            final String ipinput = ed_ip.getText().toString();
            String url = IP_BASE_URL + ipinput + "/json";
            Log.i("JResponse", ipinput);
            Log.i("JResponse", url);


            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, null, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.i("JResponse", new String(responseBody));
                    String res = new String(responseBody);

                    try {
                        JSONObject jsonObject = new JSONObject(res);
                        try {
                            String ipadd = jsonObject.getString("ip");
                            ip_address.setText(ipadd);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get IP", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String city1 = jsonObject.getString("city");
                            city.setText(city1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get City", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String region1 = jsonObject.getString("region");
                            region.setText(region1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Region", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String r_code1 = jsonObject.getString("region_code");
                            region_code.setText(r_code1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Region Code", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String country1 = jsonObject.getString("country");
                            country.setText(country1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Country Code", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String country_name1 = jsonObject.getString("country_name");
                            country_name.setText(country_name1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Country", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String continent_code1 = jsonObject.getString("continent_code");
                            continent_code.setText(continent_code1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Continent Code", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String in_eu1 = jsonObject.getString("in_eu");
                            in_eu.setText(in_eu1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get European Union status", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String postal1 = jsonObject.getString("postal");
                            postal.setText(postal1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Postal Code", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String lat1 = jsonObject.getString("latitude");
                            String longt1 = jsonObject.getString("longitude");
                            String latlong1 = lat1 + "," + longt1;
                            latlong.setText(latlong1);

                            final String maplink = "https://www.google.com/maps/search/?api=1&query="+latlong1;
                            Log.i("MapLink", maplink);
                            if (lat1.equals("") || longt1.equals("")){
                                btn_map.setVisibility(View.GONE);
                            }else {
                                btn_map.setVisibility(View.VISIBLE);
                                btn_map.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Uri uri = Uri.parse(maplink);
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Location", Toast.LENGTH_SHORT).show();
                        }


                        try {
                            String timezone1 = jsonObject.getString("timezone");
                            timezone.setText(timezone1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Time Zone", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String country_calling_code1 = jsonObject.getString("country_calling_code");
                            country_calling_code.setText(country_calling_code1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Calling Code", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String currency1 = jsonObject.getString("currency");
                            currency.setText(currency1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Currency", Toast.LENGTH_SHORT).show();
                        }


                        try {
                            String languages1 = jsonObject.getString("languages");
                            languages.setText(languages1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Languages", Toast.LENGTH_SHORT).show();
                        }


                        try {
                            String asn1 = jsonObject.getString("asn");
                            asn.setText(asn1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get ASN", Toast.LENGTH_SHORT).show();
                        }

                        try {
                            String org1 = jsonObject.getString("org");
                            org.setText(org1);
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Couldnt Get Organization", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        Log.i("JResponsee", String.valueOf(e));
                        Toast.makeText(MainActivity.this, "Please input a valid IP", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    Log.i("JResponse", String.valueOf(error));
                }
            });
        }

    }


    public void firstip() {

        final String ipinput = ed_ip.getText().toString();
        String url = IP_BASE_URL + ipinput + "/json";
        Log.i("JResponse", ipinput);
        Log.i("JResponse", url);


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.i("JResponse", new String(responseBody));
                String res = new String(responseBody);

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    String ipadd = jsonObject.getString("ip");
                    String city1 = jsonObject.getString("city");
                    String region1 = jsonObject.getString("region");
                    String r_code1 = jsonObject.getString("region_code");
                    String country1 = jsonObject.getString("country");
                    String country_name1 = jsonObject.getString("country_name");
                    String continent_code1 = jsonObject.getString("continent_code");
                    String in_eu1 = jsonObject.getString("in_eu");
                    String postal1 = jsonObject.getString("postal");
                    String lat1 = jsonObject.getString("latitude");
                    String longt1 = jsonObject.getString("longitude");
                    String timezone1 = jsonObject.getString("timezone");
                    String country_calling_code1 = jsonObject.getString("country_calling_code");
                    String currency1 = jsonObject.getString("currency");
                    String languages1 = jsonObject.getString("languages");
                    String asn1 = jsonObject.getString("asn");
                    String org1 = jsonObject.getString("org");


                    ip_address.setText(ipadd);
                    city.setText(city1);
                    region.setText(region1);
                    region_code.setText(r_code1);
                    country.setText(country1);
                    country_name.setText(country_name1);
                    continent_code.setText(continent_code1);
                    in_eu.setText(in_eu1);
                    postal.setText(postal1);
                    String latlong1 = lat1 + "," + longt1;
                    latlong.setText(latlong1);
                    timezone.setText(timezone1);
                    country_calling_code.setText(country_calling_code1);
                    currency.setText(currency1);
                    languages.setText(languages1);
                    asn.setText(asn1);
                    org.setText(org1);

                    String yourip  = "Your Ip is: " + ipadd;
                    myip.setText(yourip);

                    final String maplink = "https://www.google.com/maps/search/?api=1&query="+latlong1;
                    Log.i("MapLink", maplink);
                    if (lat1.equals("") || longt1.equals("")){
                        btn_map.setVisibility(View.GONE);
                    }else {
                        btn_map.setVisibility(View.VISIBLE);
                        btn_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse(maplink);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                    }

                } catch (Exception e) {
                    Log.i("JResponsee", String.valueOf(e));
                    Toast.makeText(MainActivity.this, "Please input a valid IP", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.i("JResponse", String.valueOf(error));
            }
        });

    }

}
