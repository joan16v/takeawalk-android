package com.takeawalk;

import java.util.List;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class viewruta extends MapActivity {
	
    private List<Overlay> mapOverlays;
    private String puntos;
    private MapView mapView;
    private MapController mc;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewruta);
        setTitle(this.getIntent().getExtras().get("titulo").toString());        
        puntos = this.getIntent().getExtras().get("puntos").toString();        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);        
        mapOverlays = mapView.getOverlays();        
        mapOverlays.add(new MyOverlay(puntos));        
        mc = mapView.getController();        
        
        //center map and route zoom
        int minLat = Integer.MAX_VALUE;
        int maxLat = Integer.MIN_VALUE;
        int minLon = Integer.MAX_VALUE;
        int maxLon = Integer.MIN_VALUE;
        String[] array_puntos=puntos.split(";");
        for (Integer i=0; i<array_puntos.length; i++) {
            String[] array_coord = array_puntos[i].split(",");
            Float lat_t = Float.parseFloat(array_coord[0]);
            lat_t = lat_t*1000000;
            Float lon_t = Float.parseFloat(array_coord[1]);
            lon_t = lon_t*1000000;
            int lat = Math.round(lat_t);
            int lon = Math.round(lon_t);        	
            maxLat = Math.max(lat, maxLat);
            minLat = Math.min(lat, minLat);
            maxLon = Math.max(lon, maxLon);
            minLon = Math.min(lon, minLon);        	
        }
        mc.zoomToSpan(Math.abs(maxLat - minLat), Math.abs(maxLon - minLon));
        mc.animateTo(new GeoPoint( (maxLat + minLat)/2, (maxLon + minLon)/2 ));        
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }   
    
    class MyOverlay extends Overlay {

        public MyOverlay(String puntos) {
        }   

        public void draw(Canvas canvas, MapView mapv, boolean shadow) {
            super.draw(canvas, mapv, shadow);
            Paint mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(2);

            Integer lat = 0;
            Integer lon = 0;
            Integer ant_lat = 0;
            Integer ant_lon = 0;
            
            String[] array_puntos=puntos.split(";");
            for (Integer i=0;i<array_puntos.length;i++) {
            	String[] array_coord=array_puntos[i].split(",");            	
            	Float lat_t=Float.parseFloat(array_coord[0]);
            	lat_t=lat_t*1000000;
            	Float lon_t=Float.parseFloat(array_coord[1]);
            	lon_t=lon_t*1000000;
            	lat=Math.round(lat_t);
            	lon=Math.round(lon_t);
            	
            	if (i == 0) {
            	    GeoPoint gInicio = new GeoPoint(lat,lon);
                    Point scrnPoint = new Point();
                    mapv.getProjection().toPixels(gInicio, scrnPoint);
                    Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.marker_rounded_green);
                    canvas.drawBitmap(marker, scrnPoint.x - marker.getWidth() / 2, scrnPoint.y - marker.getHeight() / 2, null);            		
            	}
            	if (i == (array_puntos.length-1)) {
            	    GeoPoint gFin = new GeoPoint(lat,lon);
                    Point scrnPoint = new Point();
                    mapv.getProjection().toPixels(gFin, scrnPoint);
                    Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.marker_rounded_red);
                    canvas.drawBitmap(marker, scrnPoint.x - marker.getWidth() / 2, scrnPoint.y - marker.getHeight() / 2, null);            		
            	}            	
            	
            	if (i > 0) {
                    GeoPoint gP1 = new GeoPoint(ant_lat,ant_lon);
                    GeoPoint gP2 = new GeoPoint(lat,lon);
                    Point p1 = new Point();
                    Point p2 = new Point();
                    Path path = new Path();
                    Projection projection = mapv.getProjection();
                    projection.toPixels(gP1, p1);
                    projection.toPixels(gP2, p2);
                    path.moveTo(p2.x, p2.y);
                    path.lineTo(p1.x,p1.y);
                    canvas.drawPath(path, mPaint);  
            	}          	
            	
            	ant_lat = lat;
            	ant_lon = lon;            	
            }            
        }  
        
    }
    
    class MyOverlay2 extends Overlay {
    	
    	private GeoPoint punto;

        public MyOverlay2(GeoPoint equis) {
            punto = equis;
        }   

        public void draw(Canvas canvas, MapView mapv, boolean shadow) {
            super.draw(canvas, mapv, shadow);
            Paint mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setColor(Color.RED);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            mPaint.setStrokeWidth(2);           	
            Point scrnPoint = new Point();
            mapv.getProjection().toPixels(punto, scrnPoint);
            Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.live);
            canvas.drawBitmap(marker, scrnPoint.x - marker.getWidth() / 2, scrnPoint.y - marker.getHeight() / 2, null);            
        }
    }
    
    private static final int MNU_OPC1 = 1;
    private static final int MNU_OPC2 = 2;
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, MNU_OPC1, Menu.NONE, "Mi ubicación").setIcon(R.drawable.gps);
        menu.add(Menu.NONE, MNU_OPC2, Menu.NONE, "Atrás").setIcon(R.drawable.back);
        return true;
    }  
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:                
            	Log.i("menu","1");
            	mi_ubicacion();
                return true;
            case 2:
            	Log.i("menu","2");
            	finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }   
    
    public void mi_ubicacion() {    	
    	LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	LocationListener mlocListener = new MyLocationListener();
    	mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
    }
    
    public class MyLocationListener implements LocationListener {    

	    public void onLocationChanged(Location loc) {	
        	GeoPoint point = new GeoPoint((int) (loc.getLatitude() * 1E6), (int) (loc.getLongitude() * 1E6));
        	mc.animateTo(point);        	
        	mapOverlays.add(new MyOverlay2(point));
	    }    

	    public void onProviderDisabled(String provider)	{	
		Toast.makeText( getApplicationContext(), "Tienes el GPS apagado!", Toast.LENGTH_SHORT ).show();	
	    }
    
	    public void onProviderEnabled(String provider) {	
	    	Toast.makeText( getApplicationContext(), "GPS OK",	Toast.LENGTH_SHORT).show();	
	    }    

	    public void onStatusChanged(String provider, int status, Bundle extras) {	
	    }

    }    
}
