package com.dynamicsoftco.dynamiclinks;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public class MainActivity extends AppCompatActivity {

    TextView textView1, textView2;

    WebView webView;

    //Deeplinking URL Chilo = https://www.example.com/?email=https://www.kalerkantho.com/&password=12345


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.tv1);
        textView2 = findViewById(R.id.tv2);


        webView = findViewById(R.id.web) ;

        webView.getSettings().setJavaScriptEnabled(true);


        webView.setWebViewClient(new WebViewClient(){

        });



        getdynamiclinkfromfirebase(); //this is for deeplink
    }

    //This is for deepLink
    private void getdynamiclinkfromfirebase() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                        Log.i(TAG, "We Have Dynamic Link");

                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        if(deepLink!=null){
                            Log.i(TAG, "onSuccess: We Have Dynamic Link \n" + deepLink.toString());

                            String email= deepLink.getQueryParameter("email");
                            String password = deepLink.getQueryParameter("password");

                            textView1.setText(email);
                            textView2.setText(password);

                            webView = findViewById(R.id.web) ;
                            webView.loadUrl(email);
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.w(TAG, "getDynamicLink:onFailure", e);

                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}