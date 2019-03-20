package com.example.modulos_utiles.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.modulos_utiles.Objetos.DetalleOrdServList;
import com.example.modulos_utiles.Objetos.listaDeRecibidos;
import com.example.modulos_utiles.R;

import java.util.ArrayList;
import java.util.List;

public class Adaptador_DetalleOrdServ extends RecyclerView.Adapter<Adaptador_DetalleOrdServ.ViewHolder> {

//Declaración de elementos que se van a utilizar en el adaptador desde listas de datos
//hasta elementos gráficos para mostrar la información, para la lista podemos utilizar
//una lista de String básica o podemos utilizar un objeto ejemplo:
//List<String>
//List<nombreDelObjeto>.

    private Context contexto;
    private List<DetalleOrdServList> lista;
    private int layout;
    private Adaptador_DetalleOrdServ.OnItemClickListener itemClickListener;
    private List<listaDeRecibidos> listaDeRecibidos;

//////////////////////////////////////////////////////////////////////////////////////

    //Constructor que recibe los parámetros para el tratamiento de las variables de campo.
    public Adaptador_DetalleOrdServ(Context contexto, List lista, int layout, Adaptador_DetalleOrdServ.OnItemClickListener itemClickListener) {

        this.listaDeRecibidos = new ArrayList<>();
        this.contexto = contexto;
        this.lista = lista;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    //Método en el cual se va a inflar el layout que vamos a utilizar para mostrar los
//elementos visuales que contendra nuestro recyclerview
    @NonNull
    @Override
    public Adaptador_DetalleOrdServ.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        Adaptador_DetalleOrdServ.ViewHolder viewHolder = new Adaptador_DetalleOrdServ.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_DetalleOrdServ.ViewHolder viewHolder, int i) {
        viewHolder.enlazar(
                i,
                contexto,
                lista.get(i).getAccion(),
                lista.get(i).getClave(),
                lista.get(i).getClv_Orden(),
                lista.get(i).getClv_Trabajo(),
                lista.get(i).getDescripcion(),
                lista.get(i).getObs(),
                lista.get(i).isSeRealiza(),
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

        private TextView tv_Descripcion;
        private TextView tv_Accion;
        private TextView tv_recibi;
        private CheckBox checkBoxRecibi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_Descripcion = (TextView) itemView.findViewById(R.id.tv_Descripcion);
            this.tv_Accion = (TextView) itemView.findViewById(R.id.tv_Accion);
            this.checkBoxRecibi = (CheckBox) itemView.findViewById(R.id.check_recibi);
            this.tv_recibi = (TextView) itemView.findViewById(R.id.tv_recibi);
        }

        public void enlazar(
                final int posicion,
                final Context context,
                final String accion,
                final int clave,
                final int clv_Orden,
                final int clv_Trabajo,
                final String descripcion,
                final String obs,
                final boolean seRealiza,
                final Adaptador_DetalleOrdServ.OnItemClickListener click) {

            if (clv_Trabajo != 1203) {
                this.checkBoxRecibi.setEnabled(false);
                this.checkBoxRecibi.setVisibility(View.INVISIBLE);
                this.tv_recibi.setVisibility(View.INVISIBLE);
            }

            this.tv_Descripcion.setText(descripcion);
            this.tv_Accion.setText(accion);

            listaDeRecibidos.add(new listaDeRecibidos(clave, clv_Orden, clv_Trabajo, descripcion, obs, seRealiza, false));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBoxRecibi.isChecked()) {
                        checkBoxRecibi.setChecked(false);
                        listaDeRecibidos.get(posicion).setRecibi(checkBoxRecibi.isChecked());
                        click.click(clave, checkBoxRecibi.isChecked(), listaDeRecibidos, posicion);
                    }else {
                        checkBoxRecibi.setChecked(true);
                        listaDeRecibidos.get(posicion).setRecibi(checkBoxRecibi.isChecked());
                        click.click(clave, checkBoxRecibi.isChecked(), listaDeRecibidos, posicion);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void click(int clave, boolean recibi, List<listaDeRecibidos> lista, int posicion);
    }
}
        
