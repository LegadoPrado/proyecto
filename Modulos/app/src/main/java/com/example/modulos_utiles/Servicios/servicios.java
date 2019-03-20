package com.example.modulos_utiles.Servicios;

public class servicios {

    private  String urlServidor="http://192.168.50.100:8082/SoftvWCFService.svc/";

    //////////////////////////////////////////////////////////////////
    private String userLogin="Usuario/LogOn";
    private String ClaveTecnico="AplicacionMovil/Get_ClvTecnico";
    //////////////////PANTALLA PRINCIPAL GRAFICA Y SIGUIENTE TRABAJO//////////////////////////
    private String siguienteTrabajo="AplicacionMovil/GetDameSiguienteCita";
    private String graficaTrabajos="AplicacionMovil/GetDameOrdenesQuejasTotales";
    /////////////////TRABAJO CAMBIO DE APARATO//////////////////
    private String apaDispCliente="MuestraAparatosDisponibles/GetListClienteAparatos";
    private String statusAparato="MuestraAparatosDisponibles/GetSP_StatusAparatosList"; //get
    private String tipoAparato="MuestraAparatosDisponibles/GetListTipoAparatosByIdArticulo";
    private String apaDisponible="MuestraAparatosDisponibles/GetListAparatosDisponiblesByIdArticulo";//aparatos disponibles por el tecnico
    ///////////////////////////////////////////////////////////
    private String cambioDomicilio="AplicacionMovil/GetDameDatosCAMDO";
    private String arbolApaPorInstalar="MuestraArbolServiciosAparatosPorinstalar/GetMuestraArbolServiciosAparatosPorinstalarList";
    private String medioServicio="MuestraMedioPorServicoContratado/GetMuestraMedioPorServicoContratadoList";
    private String aparatoTipo="MuestraTipoAparato/GetMuestraTipoAparatoList";
    private String apaDispTec="MuestraAparatosDisponibles/GetMuestraAparatosDisponiblesList";
    private String servDeAparato="MuestraServiciosRelTipoAparato/GetMuestraServiciosRelTipoAparatoList";
    private String asignaApaServicio="AsignaAparatosAlServicio/GetAsignaAparatosAlServicioList";
    private String extAdicionales="OrdSer/GetCONCONEX";
    /////////////ORDENES///////////////////
    private String listaOrdenesTecnico="AplicacionMovil/GetDameListadoOrdenesAgendadas";
    private String datosOrden="ConsultaOrdSer/GetDeepConsultaOrdSer";
    private String infoClienteOrden="BUSCLIPORCONTRATO_OrdSer/GetDeepBUSCLIPORCONTRATO_OrdSer";
    private String serviciosCliente="AplicacionMovil/GetdameSerDELCliresumen";
    private String listaTrabajos="BUSCADetOrdSer/GetBUSCADetOrdSerList";
    private String tecSecundario="MuestraRelOrdenesTecnicos/GetMuestraRelOrdenesTecnicosList";
    /////////////REPORTES//////////////////
    private String listaReportesTecnico="AplicacionMovil/GetDameListadoQuejasAgendadas";
    private String infoClienteReporte="uspBuscaContratoSeparado2/GetuspBuscaContratoSeparado2List";
    private String tipoSolucion="MUESTRATRABAJOSQUEJAS/GetMUESTRATRABAJOSQUEJASList";
    private String detalleReporte="Quejas/GetQuejasList";
    private String nombreTecReporte="OrdSer/GetConTecnicoAgenda"; //recibe nombre de tecnico y clave tecnico
    private String serviClientRepo="DameSerDelCliFac/GetDameSerDelCliFacList";
    private String tecSecQueja="Muestra_Tecnicos_Almacen/GetMuestra_Tecnicos_AlmacenList";

    /////////////EJECUTAR ORDEN INSTALACION/////////////
    private String validaGuardaOrdSerAparatos="OrdSer/GetSP_ValidaGuardaOrdSerAparatos";
    private String chacaCamdo="Checa_si_tiene_camdo/GetCheca_si_tiene_camdo";
    private String addNuevaOrdUsuario="NueRelOrdenUsuario/AddNueRelOrdenUsuario";
    private String modorser="MODORDSER/GetDeepMODORDSER";
    private String guardaHoraOrden="OrdSer/GetGuardaHoraOrden";
    private String guardaOrdSerAparatos="SP_GuardaOrdSerAparatos/GetDeepSP_GuardaOrdSerAparatos";
    private String guardaCoordenadas="CLIENTES_New/GetGuardaCoordendasCli";
    private String llenaBitacoraOrd="SP_LLena_Bitacora_Ordenes/AddSP_LLena_Bitacora_Ordenes";
    private String consIpPorCont="OrdSer/GetConsultaIpPorContrato";
    private String reintentaCamdo="OrdSer/GetReintentarComando";

    ///////////////////////////EJECUTAT REPORTES/////////////////
    private String guardaHoraRepo="OrdSer/GetGuardaHoraOrden";
    private String enviarQueja="Quejas/UpdateQuejas";
    private String validaReporte="ValidaQuejaCompaniaAdic/GetDeepValidaQuejaCompaniaAdic";

    ///////////////////ENVIA APARATOS NO ENTREGADOS///////////////////////////
    private String aparatNoEntregados="SP_InsertaTbl_NoEntregados/GetSP_InsertaTbl_NoEntregados";

    ///////////////////////////////CAMBIO DE APARATO///////////////////////////////
    private String capatGet="MuestraAparatosDisponibles/GetSetCambioAparato";//ASIGNAR APARATO
    private String validApaDisp="MuestraAparatosDisponibles/GetCambioAparatoDeep";// VALIDA SI TIENE APARATO ASIGNADO

    //////////SELECCION DE MATERIAL///////////////
    private String muestraDetalleBitacora="Muestra_Detalle_Bitacora_2/GetMuestra_Detalle_Bitacora_2List";
    private String descripArt="Muestra_Descripcion_Articulo_2/GetMuestra_Descripcion_Articulo_2List";

    private String checkExt="UspChecaSiTieneExtensiones/GetUspChecaSiTieneExtensiones";
    private String campExtList="UspChecaSiTieneExtensiones/GetUspLlenaComboExtensionesList";
    private String tipoMaterial="OrdSer/GetSoftv_ObtenTipoMaterial";
    private String validPreDescarga="AplicacionMovil/ValidaExisteTblPreDescargaMaterial";
    /////TABLA DESCARGA MATERIAL//////////
    private String tblDescargaMaterial="AplicacionMovil/InsertaTblPreDescargaMaterial";

    public String getUserLogin(){
        return urlServidor+userLogin;
    }
    public String getClaveTecnico() {
        return urlServidor+ClaveTecnico;
    }
    public String getSiguienteTrabajo(){
        return  urlServidor+siguienteTrabajo;
    }
    public String getQuejasTotales() {
        return urlServidor+graficaTrabajos;
    }
    public String getListaOrdenesTecnico() {
        return urlServidor+listaOrdenesTecnico;
    }

    public String getDatosOrden() {
        return urlServidor+datosOrden;
    }

    public String getInfoClienteOrden() {
        return urlServidor+infoClienteOrden;
    }

    public String getServiciosCliente() {
        return urlServidor+serviciosCliente;
    }

    public String getListaTrabajos() {
        return urlServidor+listaTrabajos;
    }

    public String getTecSecundario() {
        return urlServidor+tecSecundario;
    }

    public String getApaDispCliente() {
        return urlServidor+apaDispCliente;
    }

    public String getStatusAparato() {
        return urlServidor+statusAparato;
    }

    public String getTipoAparato() {
        return urlServidor+tipoAparato;
    }

    public String getApaDisponible() {
        return urlServidor+apaDisponible;
    }

    public String getCambioDomicilio() {
        return urlServidor+cambioDomicilio;
    }

    public String getArbolApaPorInstalar() {
        return urlServidor+arbolApaPorInstalar;
    }

    public String getMedioServicio() {
        return urlServidor+medioServicio;
    }

    public String getAparatoTipo() {
        return urlServidor+aparatoTipo;
    }

    public String getApaDispTec() {
        return urlServidor+apaDispTec;
    }

    public String getServDeAparato() {
        return urlServidor+servDeAparato;
    }

    public String getAsignaApaServicio() {
        return urlServidor+asignaApaServicio;
    }

    public String getListaReportesTecnico() {
        return urlServidor+listaReportesTecnico;
    }

    public String getExtAdicionales() {
        return urlServidor+extAdicionales;
    }

    public String getInfoClienteReporte() {
        return urlServidor+infoClienteReporte;
    }

    public String getTipoSolucion() {
        return urlServidor+tipoSolucion;
    }

    public String getDetalleReporte() {
        return urlServidor+detalleReporte;
    }

    public String getNombreTecReporte() {
        return urlServidor+nombreTecReporte;
    }

    public String getServiClientRepo() {
        return urlServidor+serviClientRepo;
    }

    public String getTecSecQueja() {
        return urlServidor+tecSecQueja;
    }
    //////////////////////////

    public String getValidaGuardaOrdSerAparatos() {
        return urlServidor+validaGuardaOrdSerAparatos;
    }

    public String getChacaCamdo() {
        return  urlServidor+ chacaCamdo;
    }

    public String getAddNuevaOrdUsuario() {
        return urlServidor+ addNuevaOrdUsuario;
    }

    public String getModorser() {
        return urlServidor+modorser;
    }

    public String getGuardaHoraOrden() {
        return urlServidor+guardaHoraOrden;
    }

    public String getGuardaOrdSerAparatos() {
        return urlServidor+guardaOrdSerAparatos;
    }

    public String getGuardaCoordenadas() {
        return urlServidor+guardaCoordenadas;
    }

    public String getLlenaBitacoraOrd() {
        return urlServidor+llenaBitacoraOrd;
    }

    public String getConsIpPorCont() {
        return urlServidor+consIpPorCont;
    }

    public String getReintentaCamdo() {
        return urlServidor+reintentaCamdo;
    }

    public String getAparatNoEntregados() {
        return urlServidor+aparatNoEntregados;
    }

    public String getCapatGet() {
        return urlServidor+capatGet;
    }

    public String getValidApaDisp() {
        return urlServidor+validApaDisp;
    }

    public String getMuestraDetalleBitacora() {
        return urlServidor+muestraDetalleBitacora;
    }

    public String getDescripArt() {
        return urlServidor+descripArt;
    }

    public String getCheckExt() {
        return urlServidor+checkExt;
    }

    public String getCampExtList() {
        return urlServidor+campExtList;
    }

    public String getTipoMaterial() {
        return urlServidor+tipoMaterial;
    }

    public String getValidPreDescarga() {
        return urlServidor+validPreDescarga;
    }

    public String getTblDescargaMaterial() {
        return urlServidor+tblDescargaMaterial;
    }
}
