package marc.cinetracker_m8;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


public class Work_Fragment extends Fragment implements LocationListener {

    Firebase ref;

    private MapView map;
    private IMapController iMapController;
    private MyLocationNewOverlay mlno;
    //private MinimapOverlay minimapOverlay;
    private ScaleBarOverlay scaleBarOverlay;
    private CompassOverlay compassOverlay;
    private RadiusMarkerClusterer grupoMarkadores;
    private int radioAgrupacion = 100;


    boolean hayControlZoom = true;
    boolean hayControlMultiTouch = true;
    boolean hayCentradoInicial = true;
    boolean hayPrecisionOverlay = true;
    private int zoomInicial;


    public Work_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_work_lay, container, false);

        // Localizacion
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return view;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, this);
        ////////////
        createFirebase app = (createFirebase)getActivity().getApplication();
        Firebase ref = app.getRef();

        map = (MapView) view.findViewById(R.id.mapView);
        zoomInicial = 15;

        //Configuraciones iniciales
        setMap();
        setZoom(zoomInicial);
        setOverlays(getContext());
        drawMarkers(getContext());

        return view;

        //ref.child("hijo1").setValue("valor_1");
        //ref.child("hijo2").setValue("valor_2");

        //PojoForNote notaInicial = new PojoForNote("titol-a", "Institut Poblenou", 41.39834,2.20318);
        //PojoForNote notaTest = new PojoForNote("titol-b", "Institut lalala", 41.0,2.0);

        //ref.child("prueba").child("Notas").child("nota1").setValue(notaInicial);
        //ref.child("prueba").child("Notas").child("nota2").setValue(notaTest);

/*
        ref.child("hijo1").child("Notas").child("nota2")
                .addValueEventListener(new ValueEventListener() {
                    //@TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println("onDataChange:" + snapshot.getValue());
                        msgToast(getContext(), "titulo", snapshot.getValue().toString());
                    }

                    //@TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onCancelled(FirebaseError error) {
                        msgToast(getContext(), "Error", "Listener");
                    }
                });*/

        /*
        Button boton = (Button) view.findViewById(R.id.bCaptura);
        final EditText etTitulo = (EditText) view.findViewById(R.id.etTitolPeli);
        final EditText etDesc = (EditText) view.findViewById(R.id.etDescrip);

        //FALTA VER MAPA CON UBICACION
        // Listener button
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (localizacion != null) {
                    msgToast(v.getContext(), "", String.valueOf(localizacion.getLatitude()) + " - " + String.valueOf(localizacion.getLongitude()));

                }
                String tits = etTitulo.getText().toString();
                String descs = etDesc.getText().toString();
            }
        });*/
        ////////////
        //return view;
    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void msgToast (Context context, String tag, String msg){
        Toast.makeText(context, tag + ": " + msg, Toast.LENGTH_LONG).show();
    }

    public void setMap(){
        try {
            //Determinamos los tiles del mapa para pintarlo

            map.setTileSource(TileSourceFactory.MAPQUESTOSM);
            map.setTilesScaledToDpi(true);

            //Determinamos otras opciones del mapa
            map.setBuiltInZoomControls(hayControlZoom);
            map.setMultiTouchControls(hayControlMultiTouch);

        }catch(Exception ex){
            ex.printStackTrace();
            //msgToast(2, "Dibujando el mapa inicial");
        }
    }

    public void setZoom(int zoom){
        try {
            iMapController = map.getController();
            iMapController.setZoom(zoom);

        }catch (Exception ex){
            ex.printStackTrace();
            iMapController.setZoom(zoomInicial);
            //msgToast (2, "Ajustando el Zoom al inicial");
        }
    }

    public void setOverlays(Context context){
        final DisplayMetrics metricaDelMapa = getResources().getDisplayMetrics();
        mlno = new MyLocationNewOverlay(    context
                ,new GpsMyLocationProvider(context)
                ,map
        );

        mlno.enableMyLocation();
        mlno.setDrawAccuracyEnabled(hayPrecisionOverlay);
        mlno.runOnFirstFix(new Runnable() {
            @Override
            public void run() { iMapController.animateTo(mlno.getMyLocation()); }
        });

        //Setear la escala del mapa
        scaleBarOverlay = new ScaleBarOverlay(map);
        scaleBarOverlay.setCentred(hayCentradoInicial);
        scaleBarOverlay.setScaleBarOffset(metricaDelMapa.widthPixels / 2, 10);

        //Setear la Brujula de Overlays
        compassOverlay = new CompassOverlay( context
                ,new InternalCompassOrientationProvider(context)
                , map
        );
        compassOverlay.enableCompass();

        //Añadir los setters al overlay
        map.getOverlays().add(mlno);
        map.getOverlays().add(this.scaleBarOverlay);
        map.getOverlays().add(this.compassOverlay);
    }

    public void drawMarkers (Context context){
        setAgrupacionMarkers(context, radioAgrupacion);

        final Firebase notas = ref.child("prueba").child("Notas");
        notas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot notasFireBase: dataSnapshot.getChildren()){

                    PojoForNote pojoForNote = notasFireBase.getValue(PojoForNote.class);
                    Marker notaMarker = new Marker(map);
                    GeoPoint gp = new GeoPoint(pojoForNote.getLatitud() ,pojoForNote.getLongitud());

                    notaMarker.setPosition(gp);
                    notaMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    notaMarker.setIcon(getResources().getDrawable(R.drawable.marker_default));
                    notaMarker.setTitle(pojoForNote.getTitulo());
                    notaMarker.setAlpha(0.7f);//trasparencia

                    grupoMarkadores.add(notaMarker);
                    //msgToast(1, pojoForNote.getTitulo()+" añadida");
                }
                grupoMarkadores.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {  }
        });
    }

    public void setAgrupacionMarkers(Context context, int radio){
        grupoMarkadores = new RadiusMarkerClusterer(context);
        map.getOverlays().add(grupoMarkadores);

        Drawable clusterIconoDrawable = getResources().getDrawable(android.R.drawable.ic_input_add);
        int markerWidth = clusterIconoDrawable.getIntrinsicWidth();
        int markerHeight = clusterIconoDrawable.getIntrinsicHeight();
        clusterIconoDrawable.setBounds(0, markerHeight, markerWidth, 0);
        Bitmap clusterIconBm = ((BitmapDrawable) clusterIconoDrawable).getBitmap();

        grupoMarkadores.setIcon(clusterIconBm);
        grupoMarkadores.setRadius(radio);
    }


}
