package com.takeawalk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TakeawalkActivity extends Activity {
	
    private ImageView logo;
    private TextView enlace1;
    private TextView enlace2;
    private TextView enlace3;
    private TextView enlace4;
    private TextView enlace5;	
    private Button boton;
    private Button botonMenos;
	
    Integer idruta1=0;
    String title1="";
    String desc1="";
    String long1="";
    String puntos1="";
    
    Integer idruta2=0;
    String title2="";
    String desc2="";
    String long2="";
    String puntos2="";
    
    Integer idruta3=0;
    String title3="";
    String desc3="";
    String long3="";
    String puntos3="";
    
    Integer idruta4=0;
    String title4="";
    String desc4="";
    String long4="";
    String puntos4="";
    
    Integer idruta5=0;
    String title5="";
    String desc5="";
    String long5="";
    String puntos5="";
    
    Integer last_result=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.principal_linear);
        
        logo = (ImageView) findViewById(R.id.imageView1);
        logo.setPadding(50, 10, 0, 10);
        
        enlace1 = (TextView) findViewById(R.id.textView1);       
        enlace1.setPadding(10, 20, 0, 0);
        enlace2 = (TextView) findViewById(R.id.textView2);
        enlace2.setPadding(10, 20, 0, 0);
        enlace3 = (TextView) findViewById(R.id.textView3);
        enlace3.setPadding(10, 20, 0, 0);
        enlace4 = (TextView) findViewById(R.id.textView4);
        enlace4.setPadding(10, 20, 0, 0);
        enlace5 = (TextView) findViewById(R.id.textView5);
        enlace5.setPadding(10, 20, 0, 20);
        
        boton = (Button) findViewById(R.id.button1);
        botonMenos = (Button) findViewById(R.id.button2);
        
        logo.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	String url = "http://www.takeawalk.es";  
            	Intent i = new Intent(Intent.ACTION_VIEW);  
            	i.setData(Uri.parse(url));  
            	startActivity(i);            	
            }
        });  
        
        enlace1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	Intent intent = new Intent(TakeawalkActivity.this, 
            			viewruta.class);
            			intent.putExtra("idruta",idruta1);
            			intent.putExtra("titulo",title1);
            			intent.putExtra("puntos",puntos1);
            			startActivity(intent);            	
            }
        });  
        enlace2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	Intent intent = new Intent(TakeawalkActivity.this, 
            			viewruta.class);
            			intent.putExtra("idruta",idruta2);
            			intent.putExtra("titulo",title2);
            			intent.putExtra("puntos",puntos2);
            			startActivity(intent);            	
            }
        }); 
        enlace3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	Intent intent = new Intent(TakeawalkActivity.this, 
            			viewruta.class);
            			intent.putExtra("idruta",idruta3);
            			intent.putExtra("titulo",title3);
            			intent.putExtra("puntos",puntos3);
            			startActivity(intent);            	
            }
        }); 
        enlace4.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	Intent intent = new Intent(TakeawalkActivity.this, 
            			viewruta.class);
            			intent.putExtra("idruta",idruta4);
            			intent.putExtra("titulo",title4);
            			intent.putExtra("puntos",puntos4);
            			startActivity(intent);            	
            }
        }); 
        enlace5.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {            	
            	Intent intent = new Intent(TakeawalkActivity.this, 
            			viewruta.class);
            			intent.putExtra("idruta",idruta5);
            			intent.putExtra("titulo",title5);
            			intent.putExtra("puntos",puntos5);
            			startActivity(intent);            	
            }
        }); 
        
        boton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {    
            	Log.i("last_result",last_result.toString());
            	get_results(last_result);
            }
        });   
        
        botonMenos.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {    
            	Log.i("last_result",last_result.toString());
            	get_results(last_result+10);
            }
        });         
        
        get_results(0);
        
    }
    
    private void get_results(Integer start) {
    	
	      String result = "";	      
	      ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	      nameValuePairs.add(new BasicNameValuePair("results", start.toString() ));
	       
	      //Hachettp post
	      try{
	              HttpClient httpclient = new DefaultHttpClient();
	              HttpPost httppost = new HttpPost("http://www.takeawalk.es/android/get_rutas.php");
	              httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	              HttpResponse response = httpclient.execute(httppost);
	              HttpEntity entity = response.getEntity();
	              InputStream is = entity.getContent();
	              //convert response to string
	              BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
	              StringBuilder sb = new StringBuilder();
	              String line = null;
	              while ((line = reader.readLine()) != null) {
	                      sb.append(line + "\n");
	              }
	              is.close();	       
	              result=sb.toString();	              
	      }catch(Exception e){
	              Log.e("log_tag", "Error in http connection "+e.toString());
	      }
	       
	      //parse json data
	      try{
	              JSONArray jArray = new JSONArray(result);
	              for(int i=0;i<jArray.length();i++){
	                      JSONObject json_data = jArray.getJSONObject(i);
	                      Log.i("log_tag","id: "+json_data.getInt("id")+
	                              ", ciudad: "+json_data.getString("ciudad")
	                      );

	                      DecimalFormat df = new DecimalFormat("0.00");	                        
	                      if( i==0 ) {
	                    	  idruta1=Integer.parseInt(json_data.getString("id"));
	                    	  title1="Ruta en "+json_data.getString("ciudad");
	                    	  Float temp=Float.parseFloat(json_data.getString("longitud").toString());
	                    	  if( temp>1000 ) {
	                    		  temp=temp/1000;	                    		  
	                    		  long1=(df.format(temp).toString())+" km";
	                    	  } else {
	                    		  long1=temp.toString()+" m";
	                    	  }	                    	  
	                    	  desc1=json_data.getString("descripcion");
	                    	  if( desc1!="" && desc1!="null" ) {
	                    		  enlace1.setText("Ruta en "+json_data.getString("ciudad")+" - "+json_data.getString("descripcion")+" ("+long1+").");
	                    	  } else {
	                    		  enlace1.setText("Ruta en "+json_data.getString("ciudad")+" ("+long1+").");
	                    	  }	  
	                    	  puntos1=json_data.getString("ruta");
	                      }
	                      if( i==1 ) {
	                    	  idruta2=Integer.parseInt(json_data.getString("id"));
	                    	  title2="Ruta en "+json_data.getString("ciudad");
	                    	  Float temp=Float.parseFloat(json_data.getString("longitud").toString());
	                    	  if( temp>1000 ) {
	                    		  temp=temp/1000;	                    		  
	                    		  long2=(df.format(temp).toString())+" km";
	                    	  } else {
	                    		  long2=temp.toString()+" m";
	                    	  }	 
	                    	  desc2=json_data.getString("descripcion");
	                    	  if( desc2!="" && desc2!="null" ) {
	                    		  enlace2.setText("Ruta en "+json_data.getString("ciudad")+" - "+json_data.getString("descripcion")+" ("+long2+").");
	                    	  } else {
	                    		  enlace2.setText("Ruta en "+json_data.getString("ciudad")+" ("+long2+").");
	                    	  }	   
	                    	  puntos2=json_data.getString("ruta");
	                      }
	                      if( i==2 ) {
	                    	  idruta3=Integer.parseInt(json_data.getString("id"));
	                    	  title3="Ruta en "+json_data.getString("ciudad");
	                    	  Float temp=Float.parseFloat(json_data.getString("longitud").toString());
	                    	  if( temp>1000 ) {
	                    		  temp=temp/1000;	                    		  
	                    		  long3=(df.format(temp).toString())+" km";
	                    	  } else {
	                    		  long3=temp.toString()+" m";
	                    	  }	 	                    	  
	                    	  desc3=json_data.getString("descripcion");
	                    	  if( desc3!="" && desc3!="null" ) {
	                    		  enlace3.setText("Ruta en "+json_data.getString("ciudad")+" - "+json_data.getString("descripcion")+" ("+long3+").");
	                    	  } else {
	                    		  enlace3.setText("Ruta en "+json_data.getString("ciudad")+" ("+long3+").");
	                    	  }	 
	                    	  puntos3=json_data.getString("ruta");
	                      }
	                      if( i==3 ) {
	                    	  idruta4=Integer.parseInt(json_data.getString("id"));
	                    	  title4="Ruta en "+json_data.getString("ciudad");
	                    	  Float temp=Float.parseFloat(json_data.getString("longitud").toString());
	                    	  if( temp>1000 ) {
	                    		  temp=temp/1000;	                    		  
	                    		  long4=(df.format(temp).toString())+" km";
	                    	  } else {
	                    		  long4=temp.toString()+" m";
	                    	  }	 
	                    	  desc4=json_data.getString("descripcion");
	                    	  if( desc4!="" && desc4!="null" ) {
	                    		  enlace4.setText("Ruta en "+json_data.getString("ciudad")+" - "+json_data.getString("descripcion")+" ("+long4+").");
	                    	  } else {
	                    		  enlace4.setText("Ruta en "+json_data.getString("ciudad")+" ("+long4+").");
	                    	  }
	                    	  puntos4=json_data.getString("ruta");
	                      }
	                      if( i==4 ) {
	                    	  idruta5=Integer.parseInt(json_data.getString("id"));
	                    	  last_result=idruta5;
	                    	  title5="Ruta en "+json_data.getString("ciudad");
	                    	  Float temp=Float.parseFloat(json_data.getString("longitud").toString());
	                    	  if( temp>1000 ) {
	                    		  temp=temp/1000;	                    		  
	                    		  long5=(df.format(temp).toString())+" km";
	                    	  } else {
	                    		  long5=temp.toString()+" m";
	                    	  }	 
	                    	  desc5=json_data.getString("descripcion");
	                    	  if( desc5!="" && desc5!="null" ) {
	                    		  enlace5.setText("Ruta en "+json_data.getString("ciudad")+" - "+json_data.getString("descripcion")+" ("+long5+").");
	                    	  } else {
	                    		  enlace5.setText("Ruta en "+json_data.getString("ciudad")+" ("+long5+").");
	                    	  }	  
	                    	  puntos5=json_data.getString("ruta");
	                      }	                      
	              }	      
	      }catch(JSONException e){
	              Log.e("log_tag", "Error parsing data "+e.toString());
	      }      	
    }
    
}
