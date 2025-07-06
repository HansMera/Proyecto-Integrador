package modelo;

import java.sql.Date;
import java.util.*;

public class Conector {
    private List<Trabajador> trabajadores = new ArrayList<>();
    private List<Consultoria> consultorias = new ArrayList<>();

    public List<Trabajador> getTrabajadores() { return trabajadores; }
    public List<Consultoria> getConsultorias() { return consultorias; }

    // --- Trabajadores ---
    public boolean existeTrabajador(String nombre, String cargo, String especializacion) {
        return trabajadores.stream().anyMatch(t ->
                t.getNombre().equalsIgnoreCase(nombre) &&
                        t.getCargo().equalsIgnoreCase(cargo) &&
                        t.getEspecializacion().equalsIgnoreCase(especializacion)
        );
    }

    public void agregarTrabajador(Trabajador t) {
        trabajadores.add(t);
    }

    public void editarTrabajador(int idx, String nombre, String cargo, String especializacion) {
        if (idx >= 0 && idx < trabajadores.size()) {
            Trabajador t = trabajadores.get(idx);
            if (nombre != null && !nombre.isEmpty()) t.setNombre(nombre);
            if (cargo != null && !cargo.isEmpty()) t.setCargo(cargo);
            if (especializacion != null && !especializacion.isEmpty()) t.setEspecializacion(especializacion);
        }
    }

    public void eliminarTrabajador(int idx) {
        if (idx >= 0 && idx < trabajadores.size()) {
            trabajadores.remove(idx);
        }
    }

    public Optional<Trabajador> buscarTrabajadorPorId(int id) {
        return trabajadores.stream().filter(t -> t.getCodigoID() == id).findFirst();
    }

    public int buscarTrabajadorPorNombre(String nombre) {
        for (int i = 0; i < trabajadores.size(); i++) {
            if (trabajadores.get(i).getNombre().equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    // --- Consultorías ---
    public void agregarConsultoria(Consultoria c) {
        consultorias.add(c);
    }

    public void editarConsultoria(int idx, String nombre, String descripcion) {
        if (idx >= 0 && idx < consultorias.size()) {
            Consultoria c = consultorias.get(idx);
            if (nombre != null && !nombre.isEmpty()) c.setNombre(nombre);
            if (descripcion != null && !descripcion.isEmpty()) c.setDescripcion(descripcion);
        }
    }

    public void eliminarConsultoria(int idx) {
        if (idx >= 0 && idx < consultorias.size()) {
            consultorias.remove(idx);
        }
    }

    public Optional<Consultoria> buscarConsultoriaPorId(int id) {
        return consultorias.stream().filter(c -> c.getCodigoID() == id).findFirst();
    }

    // --- Asignación de tiempos ---
    public boolean asignarTiempo(int idxTrab, int idxCons, int horas) {
        if (idxTrab >= 0 && idxTrab < trabajadores.size() && idxCons >= 0 && idxCons < consultorias.size()) {
            Trabajador t = trabajadores.get(idxTrab);
            Consultoria c = consultorias.get(idxCons);
            if (t.puedeAsignarse(horas)) {
                TiempoConsultoria tiempo = new TiempoConsultoria(t, c, horas);
                t.asignarTiempoConsultoria(tiempo);
                if (t.getCargo().equalsIgnoreCase("Administrador")) {
                    c.asignarResponsable(t);
                } else {
                    c.agregarConsultor(t);
                }
                return true;
            }
        }
        return false;
    }

    public void eliminarTiemposCero(int idxTrab) {
        if (idxTrab >= 0 && idxTrab < trabajadores.size()) {
            Trabajador t = trabajadores.get(idxTrab);
            t.getTiempoConsultorias().removeIf(tc -> tc.getHorasRestantes() == 0);
        }
    }

    // --- Pagos ---
    public String registrarPago(int idxTrab, int idxCons, int horas, Date fecha) {
        if (idxTrab >= 0 && idxTrab < trabajadores.size() && idxCons >= 0 && idxCons < consultorias.size()) {
            Trabajador t = trabajadores.get(idxTrab);
            Consultoria c = consultorias.get(idxCons);
            if (c.getEstado().equalsIgnoreCase("Inactivo") || c.getEstado().equalsIgnoreCase("Terminado")) {
                return "No se pueden realizar pagos en una consultoría Inactiva o Terminada.";
            }
            TiempoConsultoria tiempo = null;
            for (TiempoConsultoria tc : t.getTiempoConsultorias()) {
                if (tc.getConsultoria() == c) {
                    tiempo = tc;
                    break;
                }
            }
            if (tiempo == null || tiempo.getHorasRestantes() == 0) {
                return "El trabajador no tiene horas restantes en esta consultoría.";
            }
            if (horas > tiempo.getHorasRestantes()) {
                return "No puede pagar más horas de las que le quedan al trabajador.";
            }
            c.agregarPago(new ConsultoriaPago(fecha, c, horas, t));
            tiempo.reducirHoras(horas);
            return null; // éxito
        }
        return "Error en los índices de trabajador o consultoría.";
    }

    // --- Estado de consultoría ---
    public String cambiarEstadoConsultoria(int idxCons, String nuevoEstado) {
        if (idxCons >= 0 && idxCons < consultorias.size()) {
            Consultoria c = consultorias.get(idxCons);
            if (nuevoEstado.equalsIgnoreCase("Terminado") ||
                    nuevoEstado.equalsIgnoreCase("En desarrollo") ||
                    nuevoEstado.equalsIgnoreCase("Inactivo")) {
                c.setEstado(nuevoEstado);
                if (nuevoEstado.equalsIgnoreCase("Terminado")) {
                    c.setCostoTotalReal(c.calcularCostoReal());
                }
                return null; // éxito
            } else {
                return "El estado solo puede ser 'Terminado', 'En desarrollo' o 'Inactivo'.";
            }
        }
        return "Índice de consultoría inválido.";
    }

    // --- Utilidades para la interfaz ---
    public List<String> getTrabajadoresFormateados() {
        List<String> lista = new ArrayList<>();
        for (Trabajador t : trabajadores) {
            int horas = t.getHorasTotalesAsignadas();
            lista.add("ID: " + t.getCodigoID() + " | " + t.getNombre() + " | " + t.getCargo() + " | " + t.getEspecializacion() + " | " + horas + "h");
        }
        return lista;
    }

    public List<String> getConsultoriasFormateadas() {
        List<String> lista = new ArrayList<>();
        for (Consultoria c : consultorias) {
            lista.add(c.getNombre() + " | Estado: " + c.getEstado() + " | ID: " + c.getCodigoID());
        }
        return lista;
    }

    public List<String> getTiemposTrabajadorFormateados(int idxTrab) {
        List<String> lista = new ArrayList<>();
        if (idxTrab >= 0 && idxTrab < trabajadores.size()) {
            Trabajador t = trabajadores.get(idxTrab);
            for (TiempoConsultoria tc : t.getTiempoConsultorias()) {
                lista.add("Consultoría: " + tc.getConsultoria().getNombre() +
                        " \n| Horas restantes: " + tc.getHorasRestantes() +
                        " \n| Horas pagadas: " + tc.getHorasPagadas());
            }
        }
        return lista;
    }
}