package modelo;

import java.util.*;

public class Consultoria {
    private static int contadorID = 110;
    private int codigoID;
    private String nombre;
    private String descripcion;
    private String estado; // "Inactivo", "En desarrollo", "Terminado"
    private int horasPresupAdm;
    private int horasPresupConsult;
    private double costoTotalPresupuestado;
    private double costoTotalReal;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipoConsultoria;
    private Trabajador responsable;
    private ArrayList<Trabajador> consultores;
    private ArrayList<ConsultoriaPago> pagos;

    public Consultoria(String nombre, String descripcion, int horasAdm, int horasConsult, Date inicio, Date fin, String tipo) {
        this.codigoID = contadorID++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = "Inactivo";
        this.horasPresupAdm = horasAdm;
        this.horasPresupConsult = horasConsult;
        this.costoTotalPresupuestado = (horasAdm * 15) + (horasConsult * 10);
        this.fechaInicio = inicio;
        this.fechaFin = fin;
        this.tipoConsultoria = tipo;
        this.consultores = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public int getCodigoID() { return codigoID; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getEstado() { return estado; }
    public int getHorasPresupAdm() { return horasPresupAdm; }
    public int getHorasPresupConsult() { return horasPresupConsult; }
    public double getCostoTotalPresupuestado() { return costoTotalPresupuestado; }
    public double getCostoTotalReal() { return costoTotalReal; }
    public Date getFechaInicio() { return fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public String getTipoConsultoria() { return tipoConsultoria; }
    public Trabajador getResponsable() { return responsable; }
    public ArrayList<Trabajador> getConsultores() { return consultores; }
    public ArrayList<ConsultoriaPago> getPagos() { return pagos; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setCostoTotalReal(double costoTotalReal) { this.costoTotalReal = costoTotalReal; }

    public void asignarResponsable(Trabajador admin) {
        if (admin.getCargo().equalsIgnoreCase("Administrador")) {
            this.responsable = admin;
        }
    }

    public void agregarConsultor(Trabajador t) {
        if (t.getCargo().equalsIgnoreCase("Consultor")) {
            consultores.add(t);
        }
    }

    public void agregarPago(ConsultoriaPago pago) {
        pagos.add(pago);
    }

    public double calcularCostoTotal() {
        double total = 0;
        for (ConsultoriaPago p : pagos) {
            String cargo = p.getTrabajadorPagado().getCargo();
            if (cargo.equalsIgnoreCase("Consultor")) {
                total += p.getHorasPagadas() * 10;
            } else if (cargo.equalsIgnoreCase("Administrador")) {
                total += p.getHorasPagadas() * 15;
            }
        }
        return total;
    }

    public double calcularCostoReal() {
        return calcularCostoTotal();
    }

    public double calcularCompletitud() {
        if (costoTotalPresupuestado == 0) return 0;
        return (calcularCostoReal() / costoTotalPresupuestado) * 100;
    }

    public int calcularHorasPorCargo(String cargo) {
        int total = 0;
        for (ConsultoriaPago p : pagos) {
            if (p.getTrabajadorPagado().getCargo().equalsIgnoreCase(cargo)) {
                total += p.getHorasPagadas();
            }
        }
        return total;
    }
}