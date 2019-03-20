package com.example.modulos_utiles.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.modulos_utiles.Adaptadores.Adaptador_RecyclerView;
import com.example.modulos_utiles.Objetos.Peliculas;
import com.example.modulos_utiles.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String url = "https://i5.walmartimages.com/asr/cb205672-887b-4a98-a8e9-58043efe7740_1.5a29df2e9cacc80b5ef85eeefe7564fb.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF";
    private Peliculas peliculas;
    private List<Peliculas> listaPeliculas;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager manejador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        peliculas = new Peliculas();
        listaPeliculas = new ArrayList<>();
        listaPeliculas = llenarObjeto();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        manejador = new LinearLayoutManager(this);
        adaptador = new Adaptador_RecyclerView(this, listaPeliculas, R.layout.disenio_recyclerview, new Adaptador_RecyclerView.OnItemClickListener() {
            @Override
            public void click(String mensaje) {
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setLayoutManager(manejador);
        recyclerView.setAdapter(adaptador);
    }

    private ArrayList<Peliculas> llenarObjeto() {
        return new ArrayList<Peliculas>() {{
            add(new Peliculas("Space JAM", "Pelicula animada de baloncesto", 3.5f, url));
            add(new Peliculas("Space JAM", "Pelicula animada de baloncesto", 3.5f, url));
            add(new Peliculas("Space JAM", "Pelicula animada de baloncesto", 3.5f, url));
            add(new Peliculas("Space JAM", "Pelicula animada de baloncesto", 3.5f, url));
        }};
    }
}
