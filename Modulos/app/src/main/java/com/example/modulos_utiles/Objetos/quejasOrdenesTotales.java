package com.example.modulos_utiles.Objetos;

import java.util.List;

public class quejasOrdenesTotales {

    private int BaseIdUser;
    private String BaseRemoteIp;
    private List<OrdSer> ordenServicio;
    private List<Queja> ordenQueja;

    public int getBaseIdUser() {
        return BaseIdUser;
    }

    public void setBaseIdUser(int baseIdUser) {
        BaseIdUser = baseIdUser;
    }

    public String getBaseRemoteIp() {
        return BaseRemoteIp;
    }

    public void setBaseRemoteIp(String baseRemoteIp) {
        BaseRemoteIp = baseRemoteIp;
    }

    public List<OrdSer> getOrdenServicio() {
        return ordenServicio;
    }

    public void setOrdenServicio(List<OrdSer> ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    public List<Queja> getOrdenQueja() {
        return ordenQueja;
    }

    public void setOrdenQueja(List<Queja> ordenQueja) {
        this.ordenQueja = ordenQueja;
    }

    public static class OrdSer {

        private String BaseIdUser;
        private String BaseRemoteIp;
        private String Status;
        int Total;

        public String getBaseIdUser() {
            return BaseIdUser;
        }

        public void setBaseIdUser(String baseIdUser) {
            BaseIdUser = baseIdUser;
        }

        public String getBaseRemoteIp() {
            return BaseRemoteIp;
        }

        public void setBaseRemoteIp(String baseRemoteIp) {
            BaseRemoteIp = baseRemoteIp;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int total) {
            Total = total;
        }
    }

    public static class Queja {

        private String BaseIdUser;
        private String BaseRemoteIp;
        private String Status;
        private int Total;

        public String getBaseIdUser() {
            return BaseIdUser;
        }

        public void setBaseIdUser(String baseIdUser) {
            BaseIdUser = baseIdUser;
        }

        public String getBaseRemoteIp() {
            return BaseRemoteIp;
        }

        public void setBaseRemoteIp(String baseRemoteIp) {
            BaseRemoteIp = baseRemoteIp;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int total) {
            Total = total;
        }
    }
}
