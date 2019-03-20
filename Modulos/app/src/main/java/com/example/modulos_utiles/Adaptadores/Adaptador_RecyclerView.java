package com.example.modulos_utiles.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.modulos_utiles.Objetos.Peliculas;
import com.example.modulos_utiles.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptador_RecyclerView extends RecyclerView.Adapter<Adaptador_RecyclerView.ViewHolder> {

    //Declaración de elementos que se van a utilizar en el adaptador desde listas de datos
    //hasta elementos gráficos para mostrar la información, para la lista podemos utilizar
    //una lista de String básica o podemos utilizar un objeto ejemplo:
    //List<String>
    //List<nombreDelObjeto>.

    private Context contexto;
    private List<Peliculas> lista;
    private int layout;
    private OnItemClickListener itemClickListener;

    //////////////////////////////////////////////////////////////////////////////////////

    //Constructor que recibe los parámetros para el tratamiento de las variables de campo.
    public Adaptador_RecyclerView(Context contexto, List lista, int layout, OnItemClickListener itemClickListener) {
        this.contexto = contexto;
        this.lista = lista;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    //Método en el cual se va a inflar el layout que vamos a utilizar para mostrar los
    //elementos visuales que contendra nuestro recyclerview
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.enlazar(
                contexto,
                lista.get(i).getTitulo(),
                lista.get(i).getDescripcion(),
                lista.get(i).getPortada(),
                lista.get(i).getCalificacion(),
                itemClickListener);
    }

    //Método que obtiene el numero de elementos que contiene la lista que se va a manejar
    //en el reciclerview y lo retorna.
    @Override
    public int getItemCount() {
        return lista.size();
    }

    //Creamos una clase estática para optimizar la carga de los elementos gráficos
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView titulo;
        private TextView descripcion;
        private RatingBar calificacion;
        private ImageView imagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.titulo = (TextView) itemView.findViewById(R.id.tv_NContrato);
            this.descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            this.calificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            this.imagen = (ImageView) itemView.findViewById(R.id.imagen);
        }

        public void enlazar(
                final Context context,
                final String titulo,
                final String descripcion,
                final String urlImagen,
                final float calificacion,
                final OnItemClickListener click) {

            this.titulo.setText(titulo);
            this.descripcion.setText(descripcion);
            this.calificacion.setRating(calificacion);
            Picasso
                    .with(context)
                    .load(urlImagen)
                    .resize(153, 156)
                    .centerCrop()
                    .into(this.imagen);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.click(descripcion);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void click(String mensaje);
    }
}
