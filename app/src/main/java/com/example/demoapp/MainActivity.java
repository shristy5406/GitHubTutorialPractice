package com.example.demoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.transition.Scene;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.drawerlayout.widget.DrawerLayout;


import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.concurrent.ListenableFuture;
import com.esri.arcgisruntime.data.Feature;
import com.esri.arcgisruntime.data.FeatureEditResult;
import com.esri.arcgisruntime.data.ServiceFeatureTable;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.mapping.view.SketchEditor;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MapView mMapView;
    Context context=this;
    FeatureLayer featureLayer ,featureLayer1;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private ServiceFeatureTable mServiceFeatureTable,mServiceFeatureTable1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArcGISRuntimeEnvironment.setApiKey("AAPKd7240f83832149b2aefb6629ca5b5222guPjxS9RbUkCrvyBcRCofLWfhi8ygd3OLEH5_KLd91jpTXAkreMeZMfGzeGeOdns");
        //ArcGISRuntimeEnvironment.setApiKey("AAPK5058bd66f99643a9ae14d8177d0a3be3nF6PHfHORCHm1ICbjjdQafDzz9rUVLFXvhNkirIRnkCJ_VL6yt8mkpBlytVDVNH8");
        mMapView = findViewById(R.id.mapView);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationview = (NavigationView) findViewById(R.id.navview);
        // Setup drawer view

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BitmapDrawable pinStarBlueDrawable = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.marker);
        PictureMarkerSymbol pinStarBlueSymbol = new PictureMarkerSymbol(pinStarBlueDrawable);
        //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
        //its appearance would then differ across devices with different resolutions.
        pinStarBlueSymbol.setHeight(40);
        pinStarBlueSymbol.setWidth(40);

        BitmapDrawable pinStarBlueDrawable1 = (BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.marker1);
        PictureMarkerSymbol pinStarBlueSymbol1 = new PictureMarkerSymbol(pinStarBlueDrawable1);
        //Optionally set the size, if not set the image will be auto sized based on its size in pixels,
        //its appearance would then differ across devices with different resolutions.
        pinStarBlueSymbol1.setHeight(40);
        pinStarBlueSymbol1.setWidth(40);

        SimpleMarkerSymbol markerSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFF0000FF, 5 );
        SimpleRenderer blueRenderer = new SimpleRenderer(pinStarBlueSymbol);
        SimpleRenderer blueRenderer1 = new SimpleRenderer(pinStarBlueSymbol1);

        // create renderer toggle switch
      /*  ToggleButton rendererSwitch = new ToggleButton(context);
        rendererSwitch.setText("Blue Renderer");*/

        // set the render if the switch is selected
        /*rendererSwitch.addListener((observable, oldValue, newValue) -> {*/
       /*     if (rendererSwitch.isSelected()) {*/
            /*    rendererSwitch.setText("Show Original Renderer");
            } else {
                // reset the renderer if not selected
                featureLayer.resetRenderer();
                rendererSwitch.setText("Blue Renderer");
            }*/
      /*  });*/

        // create a map with streets basemap
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_STREETS);
        mMapView.setMap(map);
        Point topLeftPoint = new Point(-9177811, 4247000);
        Point bottomRightPoint = new Point(-9176791, 4247784);
        mMapView.setViewpoint(new Viewpoint(new Envelope(topLeftPoint, bottomRightPoint)));
       // mMapView.setViewpointCenterAsync(new Point( -26.449923, 80.331871, SpatialReference.create(4326)));

        // create service feature table from URL
        mServiceFeatureTable = new ServiceFeatureTable("https://services1.arcgis.com/66nhw5JlTiUUCje2/arcgis/rest/services/feature/FeatureServer/0");

        https://services.arcgis.com/V6ZHFr6zdgNZuVG0/arcgis/rest/services/Landscape_Trees/FeatureServer/0
        // create a feature layer from table

        mServiceFeatureTable1 = new ServiceFeatureTable("https://services.arcgis.com/V6ZHFr6zdgNZuVG0/arcgis/rest/services/Landscape_Trees/FeatureServer/0");


         featureLayer = new FeatureLayer(mServiceFeatureTable);
        featureLayer.setRenderer(blueRenderer);
        featureLayer1 = new FeatureLayer(mServiceFeatureTable1);
        featureLayer1.setRenderer(blueRenderer1);
        map.getOperationalLayers().add(featureLayer);
        map.getOperationalLayers().add(featureLayer1);



      /*  featureLayer.loadAsync();
        featureLayer.addDoneLoadingListener(()->{
            if (featureLayer.getLoadStatus() == LoadStatus.LOADED) {
                // add feature layer to ArcGISMap
                map.getOperationalLayers().add(featureLayer);
            } else {
                //new Alert(Alert.AlertType.ERROR, "Error loading Feature Table from service").show();
            }
        });

        featureLayer1.loadAsync();
        featureLayer1.addDoneLoadingListener(()->{
            if (featureLayer1.getLoadStatus() == LoadStatus.LOADED) {
                // add feature layer to ArcGISMap
                map.getOperationalLayers().add(featureLayer1);
            } else {
                //new Alert(Alert.AlertType.ERROR, "Error loading Feature Table from service").show();
            }
        });*/

        // add the layer to the map
        //map.getOperationalLayers().add(featureLayer);

        // add a listener to the MapView to detect when a user has performed a single tap to add a new feature to
        // the service feature table
        /*mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {
            @Override public boolean onSingleTapConfirmed(MotionEvent event) {
                // create a point from where the user clicked
                android.graphics.Point point = new android.graphics.Point((int) event.getX(), (int) event.getY());

                // create a map point from a point
                Point mapPoint = mMapView.screenToLocation(point);

                // add a new feature to the service feature table
                addFeature(mapPoint, mServiceFeatureTable);
                return super.onSingleTapConfirmed(event);
            }
        });*/

        // set map to be displayed in map view


/*
        mMapView.setViewpointCenterAsync(new Point(-118.805, 34.027, 144447.638572));
*/


/*
        Geometry startingEnvelope = new Envelope(-10995912.335747, 5267868.874421, -9880363.974046, 5960699.183877,
        SpatialReferences.getWebMercator());
    mMapView.setViewpointGeometryAsync(startingEnvelope);*/

    // create graphics to show the input location
    GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
    mMapView.getGraphicsOverlays().add(graphicsOverlay);

    // create a red marker symbol for the input point
    final SimpleMarkerSymbol markerSymbol1 = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, 0xFFFF0000, 5);
    final Graphic inputPointGraphic = new Graphic();
    inputPointGraphic.setSymbol(markerSymbol1);
    graphicsOverlay.getGraphics().add(inputPointGraphic);

    final DecimalFormat decimalFormat = new DecimalFormat("#.00000");

    // show the input location where the user clicks on the map
    mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView) {

      @Override
      public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        android.graphics.Point clickedLocation = new android.graphics.Point(Math.round(motionEvent.getX()),
            Math.round(motionEvent.getY()));
        Point originalPoint = mMapView.screenToLocation(clickedLocation);
        inputPointGraphic.setGeometry(originalPoint);
        // project the web mercator point to WGS84 (WKID 4236)
        Point projectedPoint = (Point) GeometryEngine.project(originalPoint, SpatialReferences.getWgs84());

        // show the original and projected point coordinates in a callout from the graphic
        String ox = decimalFormat.format(originalPoint.getX());
        String oy = decimalFormat.format(originalPoint.getY());
        String px = decimalFormat.format(projectedPoint.getX());
        String py = decimalFormat.format(projectedPoint.getY());
        // create a textView for the content of the callout
        TextView calloutContent = new TextView(getApplicationContext());
        calloutContent.setTextColor(Color.BLACK);
        calloutContent.setText(String.format("Coordinates\nOriginal: %s, %s\nProjected: %s, %s", ox, oy, px, py));
        // create callout
        final Callout callout = mMapView.getCallout();
        callout.setLocation(originalPoint);
        callout.setContent(calloutContent);
        callout.show();

        addFeature(originalPoint, mServiceFeatureTable);
        return true;
      }


        public boolean onDoubleTapEvent​(MotionEvent event)
        {
            // create a point from where the user clicked
            android.graphics.Point point = new android.graphics.Point((int) event.getX(), (int) event.getY());

            // create a map point from a point
            Point mapPoint = mMapView.screenToLocation(point);

            // add a new feature to the service feature table
            addFeature(mapPoint, mServiceFeatureTable);
            return super.onSingleTapConfirmed(event);
        }
    });
/*
    mMapView.setViewpointCenterAsync(new Point( -26.449923, 80.331871, SpatialReference.create(4326)));
*/
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        NavigationView navigationView=findViewById(R.id.navview);
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } navigationView.setVisibility(View.GONE);

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds a new Feature to a ServiceFeatureTable and applies the changes to the
     * server.
     *
     * @param mapPoint     location to add feature
     * @param featureTable service feature table to add feature
     */
    private void addFeature(Point mapPoint, final ServiceFeatureTable featureTable) {

        // create default attributes for the feature
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("name", "point_name");
        //attributes.put("primcause", "Earthquake");

        // creates a new feature using default attributes and point
        Feature feature = featureTable.createFeature(attributes, mapPoint);

        // check if feature can be added to feature table
        if (featureTable.canAdd()) {
            // add the new feature to the feature table and to server
            featureTable.addFeatureAsync(feature).addDoneListener(() -> applyEdits(featureTable));
            runOnUiThread(() -> logToUser(true, "feature added to the table successfully."));
        } else {
            runOnUiThread(() -> logToUser(true, "cannot add feature to the table"));
        }
    }

    /**
     * Sends any edits on the ServiceFeatureTable to the server.
     *
     * @param featureTable service feature table
     */
    private void applyEdits(ServiceFeatureTable featureTable) {

        // apply the changes to the server
        final ListenableFuture<List<FeatureEditResult>> editResult = featureTable.applyEditsAsync();
        editResult.addDoneListener(() -> {
            try {
                List<FeatureEditResult> editResults = editResult.get();
                // check if the server edit was successful
                if (editResults != null && !editResults.isEmpty()) {
                    if (!editResults.get(0).hasCompletedWithErrors()) {
                        runOnUiThread(() -> logToUser(false, "feature added"));
                    } else {
                        throw editResults.get(0).getError();
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                runOnUiThread(() -> logToUser(true, "error applying edits"));
            }
        });
    }

    /**
     * Shows a Toast to user and logs to logcat.
     *
     * @param isError whether message is an error. Determines log level.
     * @param message message to display
     */
    private void logToUser(boolean isError, String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        if (isError) {
            Log.e(TAG, message);
        } else {
            Log.d(TAG, message);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override protected void onPause() {
        mMapView.pause();
        super.onPause();
    }

    @Override protected void onDestroy() {
        mMapView.dispose();
        super.onDestroy();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

    }


}
