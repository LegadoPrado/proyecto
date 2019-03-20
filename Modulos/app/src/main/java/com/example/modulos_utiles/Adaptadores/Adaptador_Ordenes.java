package com.example.modulos_utiles.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.modulos_utiles.Objetos.OrdenesAgendadas;
import com.example.modulos_utiles.R;

import java.util.List;

public class Adaptador_Ordenes extends RecyclerView.Adapter<Adaptador_Ordenes.ViewHolder> {

//Declaración de elementos que se van a utilizar en el adaptador desde listas de datos
    //hasta elementos gráficos para mostrar la información, para la lista podemos utilizar
    //una lista de String básica o podemos utilizar un objeto ejemplo:
    //List<String>
    //List<nombreDelObjeto>.

    private Context contexto;
    private List<OrdenesAgendadas> lista;
    private int layout;
    private Adaptador_Ordenes.OnItemClickListener itemClickListener;

    //////////////////////////////////////////////////////////////////////////////////////

    //Constructor que recibe los parámetros para el tratamiento de las variables de campo.
    public Adaptador_Ordenes(Context contexto, List lista, int layout, Adaptador_Ordenes.OnItemClickListener itemClickListener) {
        this.contexto = contexto;
        this.lista = lista;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    //Método en el cual se va a inflar el layout que vamos a utilizar para mostrar los
    //elementos visuales que contendra nuestro recyclerview
    @NonNull
    @Override
    public Adaptador_Ordenes.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        Adaptador_Ordenes.ViewHolder viewHolder = new Adaptador_Ordenes.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_Ordenes.ViewHolder viewHolder, int i) {
        viewHolder.enlazar(
                contexto,
                lista.get(i).getBaseIdUser(),
                lista.get(i).getBaseRemoteIp(),
                lista.get(i).getClv_Orden(),
                lista.get(i).getContrato(),
                lista.get(i).getNombre(),
                lista.get(i).getStatus(),
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

        private TextView tv_NContrato;
        private TextView tv_Nombre;
        private TextView tv_NOrden;
        private TextView tv_estatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_estatus = (TextView) itemView.findViewById(R.id.tv_estatus);
            this.tv_NContrato = (TextView) itemView.findViewById(R.id.tv_NContrato);
            this.tv_Nombre = (TextView) itemView.findViewById(R.id.tv_Nombre);
            this.tv_NOrden = (TextView) itemView.findViewById(R.id.tv_NOrden);
        }

        public void enlazar(
                final Context context,
                final int baseIdUser,
                final String baseRemoteIp,
                final int clv_Orden,
                final String contrato,
                final String nombre,
                final String status,
                final Adaptador_Ordenes.OnItemClickListener click) {

            this.tv_NContrato.setText(contrato);
            this.tv_Nombre.setText(nombre);
            this.tv_NOrden.setText(String.valueOf(clv_Orden));
            this.tv_estatus.setText(estatus(status));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.click(contrato, status, String.valueOf(clv_Orden));
                }
            });
        }
    }

    private String estatus(String status) {
        switch (status) {
            case "P":
                return "Pendiente";
            case "V":
                return "Visita";
            case "E":
                return "Ejecutada";
        }
        return null;
    }

    public interface OnItemClickListener {
        void click(String contrato, String status, String orden);
    }
}
