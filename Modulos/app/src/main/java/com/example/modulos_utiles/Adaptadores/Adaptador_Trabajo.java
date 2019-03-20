package com.example.modulos_utiles.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.modulos_utiles.Objetos.listaTrabajos;
import com.example.modulos_utiles.R;

import java.util.List;

public class Adaptador_Trabajo extends RecyclerView.Adapter<Adaptador_Trabajo.ViewHolder> {

    //Declaración de elementos que se van a utilizar en el adaptador desde listas de datos
    //hasta elementos gráficos para mostrar la información, para la lista podemos utilizar
    //una lista de String básica o podemos utilizar un objeto ejemplo:
    //List<String>
    //List<nombreDelObjeto>.

    private Context contexto;
    private List<listaTrabajos> lista;
    private int layout;
    private Adaptador_Trabajo.OnItemClickListener itemClickListener;

    //////////////////////////////////////////////////////////////////////////////////////

    //Constructor que recibe los parámetros para el tratamiento de las variables de campo.
    public Adaptador_Trabajo(Context contexto, List lista, int layout, Adaptador_Trabajo.
            OnItemClickListener itemClickListener) {
        this.contexto = contexto;
        this.lista = lista;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    //Método en el cual se va a inflar el layout que vamos a utilizar para mostrar los
    //elementos visuales que contendra nuestro recyclerview
    @NonNull
    @Override
    public Adaptador_Trabajo.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        Adaptador_Trabajo.ViewHolder viewHolder = new Adaptador_Trabajo.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_Trabajo.ViewHolder viewHolder, int i) {
        viewHolder.enlazar(
                contexto,
                lista.get(i).getDescripcion(),
                lista.get(i).getAccion(),
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

        private TextView accion;
        private TextView descripcion;
        private CheckBox checkBoxRecibi;
        private TextView tv_recibi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.accion = (TextView) itemView.findViewById(R.id.tv_Accion);
            this.descripcion = (TextView) itemView.findViewById(R.id.tv_Descripcion);
            this.checkBoxRecibi = (CheckBox) itemView.findViewById(R.id.check_recibi);
            this.tv_recibi = (TextView) itemView.findViewById(R.id.tv_recibi);
        }

        public void enlazar(
                final Context context,
                final String accion,
                final String descripcion,
                final Adaptador_Trabajo.OnItemClickListener click) {
            if (!descripcion.equals("RAPAG - Retiro de aparato FTTH")) {
                this.checkBoxRecibi.setEnabled(false);
                this.checkBoxRecibi.setVisibility(View.INVISIBLE);
                this.tv_recibi.setVisibility(View.INVISIBLE);
            }

            this.accion.setText(accion);
            this.descripcion.setText(descripcion);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.click(descripcion, accion, checkBoxRecibi.isChecked());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void click(String descripcion, String accion, boolean recibi);
    }
}