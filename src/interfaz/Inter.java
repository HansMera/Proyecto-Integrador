package interfaz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelo.*;

import java.sql.Date;

public class Inter extends Application {
    private Conector conector = new Conector();

    private ListView<String> listaTrabajadores = new ListView<>();
    private ListView<String> listaConsultorias = new ListView<>();
    private ListView<String> listaTiempos = new ListView<>();

    private VBox panelDetalleConsultoria = new VBox(10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        cargarDatosDePrueba();

        BorderPane root = new BorderPane();

        // --- Panel de búsqueda y listas ---
        TextField campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por nombre o ID");
        Button btnBuscar = new Button("Buscar por ID");
        HBox panelBusqueda = new HBox(8, campoBusqueda, btnBuscar);
        panelBusqueda.setAlignment(Pos.CENTER_LEFT);

        Label labelTrab = new Label("Trabajadores");
        labelTrab.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label labelCons = new Label("Consultorías");
        labelCons.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label labelTiempos = new Label("Tiempos Asignados");
        labelTiempos.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        listaTrabajadores.setPrefHeight(140);
        listaConsultorias.setPrefHeight(100);
        listaTiempos.setPrefHeight(100);

        VBox panelListas = new VBox(12,
                labelTrab, panelBusqueda, listaTrabajadores,
                labelCons, listaConsultorias,
                labelTiempos, listaTiempos
        );
        panelListas.setPadding(new Insets(16));
        panelListas.setPrefWidth(340);
        panelListas.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 1 0 0;");

        // --- Panel de gestión de trabajadores ---
        Label tituloTrab = new Label("Gestión de Personal");
        tituloTrab.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nombreTrab = new TextField(); nombreTrab.setPromptText("Nombre");
        TextField cargoTrab = new TextField(); cargoTrab.setPromptText("Cargo");
        TextField especTrab = new TextField(); especTrab.setPromptText("Especialización");
        Button btnAgregarTrab = new Button("Registrar");
        Button btnEditarTrab = new Button("Editar");
        Button btnEliminarTrab = new Button("Eliminar");
        Button btnImprimirDetalleTrab = new Button("Imprimir Detalle Trabajador");

        HBox botonesTrab = new HBox(10, btnAgregarTrab, btnEditarTrab, btnEliminarTrab, btnImprimirDetalleTrab);
        botonesTrab.setAlignment(Pos.CENTER);

        VBox panelTrabajadores = new VBox(10, tituloTrab, nombreTrab, cargoTrab, especTrab, botonesTrab);
        panelTrabajadores.setPadding(new Insets(16));
        panelTrabajadores.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de gestión de consultorías ---
        Label tituloCons = new Label("Gestión de Consultorías");
        tituloCons.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField nombreCons = new TextField(); nombreCons.setPromptText("Nombre");
        TextField descCons = new TextField(); descCons.setPromptText("Descripción");
        TextField horasAdm = new TextField(); horasAdm.setPromptText("Horas Adm");
        TextField horasCons = new TextField(); horasCons.setPromptText("Horas Cons");
        TextField inicio = new TextField(); inicio.setPromptText("Inicio (yyyy-mm-dd)");
        TextField fin = new TextField(); fin.setPromptText("Fin (yyyy-mm-dd)");
        TextField tipo = new TextField(); tipo.setPromptText("Tipo");
        TextField estadoCons = new TextField(); estadoCons.setPromptText("Estado");
        Button btnAgregarCons = new Button("Registrar");
        Button btnEditarCons = new Button("Editar");
        Button btnEstadoCons = new Button("Cambiar Estado");
        Button btnEliminarCons = new Button("Eliminar");

        HBox botonesCons1 = new HBox(10, btnAgregarCons, btnEditarCons, btnEliminarCons);
        botonesCons1.setAlignment(Pos.CENTER);
        HBox botonesCons2 = new HBox(10, btnEstadoCons, estadoCons);
        botonesCons2.setAlignment(Pos.CENTER_LEFT);

        VBox panelConsultorias = new VBox(10, tituloCons, nombreCons, descCons, horasAdm, horasCons, inicio, fin, tipo, botonesCons1, botonesCons2);
        panelConsultorias.setPadding(new Insets(16));
        panelConsultorias.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de asignación ---
        Label tituloAsig = new Label("Asignar Trabajador a Consultoría");
        tituloAsig.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField horasAsig = new TextField(); horasAsig.setPromptText("Horas a asignar");
        Button btnAsignar = new Button("Asignar");
        VBox panelAsignacion = new VBox(10, tituloAsig, horasAsig, btnAsignar);
        panelAsignacion.setPadding(new Insets(16));
        panelAsignacion.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de pagos ---
        Label tituloPago = new Label("Registrar Pago");
        tituloPago.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField fechaDePago = new TextField(); fechaDePago.setPromptText("Fecha (yyyy-mm-dd)");
        TextField horasPago = new TextField(); horasPago.setPromptText("Horas a pagar");
        Button btnPagar = new Button("Registrar Pago");
        VBox panelPagos = new VBox(10, tituloPago, fechaDePago, horasPago, btnPagar);
        panelPagos.setPadding(new Insets(16));
        panelPagos.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de indicadores ---
        Label tituloIndicadores = new Label("Indicadores de Consultoría");
        tituloIndicadores.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Button btnIndicadores = new Button("Ver Indicadores");
        VBox panelIndicadores = new VBox(10, tituloIndicadores, btnIndicadores);
        panelIndicadores.setPadding(new Insets(16));
        panelIndicadores.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de recomendaciones ---
        Label tituloRec = new Label("Recomendaciones");
        tituloRec.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        TextField especRec = new TextField(); especRec.setPromptText("Especialización");
        Button btnRecomendar = new Button("Sugerir Trabajadores");
        Button btnDisponibilidad = new Button("Mostrar Disponibilidad");
        HBox botonesRec = new HBox(10, btnRecomendar, btnDisponibilidad);
        botonesRec.setAlignment(Pos.CENTER);
        VBox panelRecomendaciones = new VBox(10, tituloRec, especRec, botonesRec);
        panelRecomendaciones.setPadding(new Insets(16));
        panelRecomendaciones.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel de exportación ---
        Label tituloExp = new Label("Exportar Reportes");
        tituloExp.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Button btnExportarUna = new Button("Exportar Consultoría");
        Button btnExportarTodas = new Button("Exportar Todas");
        Button btnImprimirRegistos = new Button("Imprimir Registros");
        HBox botonesExp = new HBox(10, btnExportarUna, btnExportarTodas, btnImprimirRegistos);
        botonesExp.setAlignment(Pos.CENTER);
        VBox panelExportacion = new VBox(10, tituloExp, botonesExp);
        panelExportacion.setPadding(new Insets(16));
        panelExportacion.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");

        // --- Panel derecho con pestañas ---
        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(
                new Tab("Personal", panelTrabajadores),
                new Tab("Consultorías", panelConsultorias),
                new Tab("Asignación", panelAsignacion),
                new Tab("Pagos", panelPagos),
                new Tab("Indicadores", panelIndicadores),
                new Tab("Recomendaciones", panelRecomendaciones),
                new Tab("Exportar", panelExportacion)
        );
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-background-color: #f9f9f9;");

        // --- Panel de detalle de consultoría ---
        panelDetalleConsultoria.setPadding(new Insets(16));
        panelDetalleConsultoria.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-width: 0 0 0 1;");
        panelDetalleConsultoria.getChildren().add(new Label("Seleccione una consultoría para ver detalles."));

        // --- Listeners y lógica ---
        actualizarListas();

        listaTrabajadores.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            mostrarTiemposTrabajador(newVal.intValue());
        });

        listaConsultorias.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
            mostrarDetalleConsultoria(newVal.intValue());
        });

        btnBuscar.setOnAction(e -> {
            String texto = campoBusqueda.getText().trim();
            if (texto.isEmpty()) return;
            int idx = -1;
            try {
                int id = Integer.parseInt(texto);
                idx = conector.getTrabajadores().indexOf(conector.buscarTrabajadorPorId(id).orElse(null));
            } catch (NumberFormatException ex) {
                idx = conector.buscarTrabajadorPorNombre(texto);
            }
            if (idx >= 0) {
                listaTrabajadores.getSelectionModel().select(idx);
                listaTrabajadores.scrollTo(idx);
                mostrarTiemposTrabajador(idx);
            } else {
                mostrarAlerta("Trabajador no encontrado.");
            }
        });

        btnAgregarTrab.setOnAction(e -> {
            String nombre = nombreTrab.getText().trim();
            String cargo = cargoTrab.getText().trim();
            String especializacion = especTrab.getText().trim();

            if (!nombre.isEmpty() && !cargo.isEmpty() && !especializacion.isEmpty()) {
                boolean existe = conector.existeTrabajador(nombre, cargo, especializacion);
                if (existe) {
                    mostrarAlerta("Ya existe un trabajador con el mismo nombre, cargo y especialización. No se creó el trabajador.");
                    return;
                }
                if (cargo.equalsIgnoreCase("Administrador") || cargo.equalsIgnoreCase("Consultor")) {
                    conector.agregarTrabajador(new Trabajador(nombre, cargo, especializacion));
                    actualizarListas();
                    nombreTrab.clear(); cargoTrab.clear(); especTrab.clear();
                } else {
                    mostrarAlerta("El cargo solo puede ser 'Administrador' o 'Consultor'.");
                }
            }
        });

        btnEditarTrab.setOnAction(e -> {
            int idx = listaTrabajadores.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                Trabajador t = conector.getTrabajadores().get(idx);
                String info = "ID: " + t.getCodigoID() +
                        "\nNombre: " + t.getNombre() +
                        "\nCargo: " + t.getCargo() +
                        "\nEspecialización: " + t.getEspecializacion();
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                        "¿Desea editar este trabajador?\n\n" + info,
                        ButtonType.YES, ButtonType.NO);
                confirm.setTitle("Confirmar edición");
                confirm.setHeaderText("Confirmar edición de trabajador");
                confirm.showAndWait().ifPresent(resp -> {
                    if (resp == ButtonType.YES) {
                        String nuevoCargo = cargoTrab.getText();
                        if (!nuevoCargo.isEmpty() && !(nuevoCargo.equalsIgnoreCase("Administrador") || nuevoCargo.equalsIgnoreCase("Consultor"))) {
                            mostrarAlerta("El cargo solo puede ser 'Administrador' o 'Consultor'.");
                            return;
                        }
                        conector.editarTrabajador(idx,
                                nombreTrab.getText(),
                                cargoTrab.getText(),
                                especTrab.getText());
                        actualizarListas();
                    }
                });
            }
        });

        btnEliminarTrab.setOnAction(e -> {
            int idx = listaTrabajadores.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                conector.eliminarTrabajador(idx);
                actualizarListas();
                listaTiempos.getItems().clear();
            }
        });

        btnImprimirDetalleTrab.setOnAction(e -> {
            int idx = listaTrabajadores.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
                java.io.PrintStream ps = new java.io.PrintStream(baos);
                java.io.PrintStream old = System.out;
                System.setOut(ps);
                conector.imprimirDetalleTrabajador(idx);
                System.out.flush();
                System.setOut(old);
                String detalle = baos.toString();
                mostrarAlerta(detalle);
            } else {
                mostrarAlerta("Seleccione un trabajador para imprimir su detalle.");
            }
        });

        btnAgregarCons.setOnAction(e -> {
            try {
                Consultoria c = new Consultoria(
                        nombreCons.getText(),
                        descCons.getText(),
                        Integer.parseInt(horasAdm.getText()),
                        Integer.parseInt(horasCons.getText()),
                        Date.valueOf(inicio.getText()),
                        Date.valueOf(fin.getText()),
                        tipo.getText()
                );
                conector.agregarConsultoria(c);
                actualizarListas();
                nombreCons.clear(); descCons.clear(); horasAdm.clear(); horasCons.clear();
                inicio.clear(); fin.clear(); tipo.clear();
            } catch (IllegalArgumentException ex) {
                mostrarAlerta("Datos inválidos para consultoría.");
            }
        });

        btnEditarCons.setOnAction(e -> {
            int idx = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                conector.editarConsultoria(idx, nombreCons.getText(), descCons.getText());
                actualizarListas();
            }
        });

        btnEstadoCons.setOnAction(e -> {
            int idx = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                String nuevoEstado = estadoCons.getText();
                String error = conector.cambiarEstadoConsultoria(idx, nuevoEstado);
                if (error != null) {
                    mostrarAlerta(error);
                } else {
                    actualizarListas();
                }
            }
        });

        btnEliminarCons.setOnAction(e -> {
            int idx = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idx >= 0) {
                conector.eliminarConsultoria(idx);
                actualizarListas();
            }
        });

        btnAsignar.setOnAction(e -> {
            int idxTrab = listaTrabajadores.getSelectionModel().getSelectedIndex();
            int idxCons = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idxTrab >= 0 && idxCons >= 0) {
                try {
                    int horas = Integer.parseInt(horasAsig.getText());
                    boolean ok = conector.asignarTiempo(idxTrab, idxCons, horas);
                    if (!ok) {
                        mostrarAlerta("Excede el límite de horas legales.");
                    } else {
                        // Actualizar listas si es necesario
                    }
                } catch (NumberFormatException ex) {
                    mostrarAlerta("Horas inválidas.");
                }
            }
        });

        btnPagar.setOnAction(e -> {
            int idxCons = listaConsultorias.getSelectionModel().getSelectedIndex();
            int idxTrab = listaTrabajadores.getSelectionModel().getSelectedIndex();
            if (idxCons >= 0 && idxTrab >= 0) {
                try {
                    int horas = Integer.parseInt(horasPago.getText());
                    Date fecha = Date.valueOf(fechaDePago.getText());
                    String error = conector.registrarPago(idxTrab, idxCons, horas, fecha);
                    if (error != null) {
                        mostrarAlerta(error);
                    } else {
                        // Actualizar listas si es necesario
                    }
                } catch (IllegalArgumentException ex) {
                    mostrarAlerta("Datos inválidos para el pago.");
                }
            }
        });

        btnIndicadores.setOnAction(e -> {
            int idxCons = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idxCons >= 0) {
                Consultoria c = conector.getConsultorias().get(idxCons);
                String msg = "\nCosto presupuestado: $" + c.getCostoTotalPresupuestado() +
                        "\nCosto real: $" + c.calcularCostoReal() +
                        "\nCompletitud: " + c.calcularCompletitud() + "%" +
                        "\nHoras Admin: " + c.calcularHorasPorCargo("Administrador") +
                        "\nHoras Consultores: " + c.calcularHorasPorCargo("Consultor");
                mostrarAlerta(msg);
            }
        });

        btnRecomendar.setOnAction(e -> {
            String especialidad = especRec.getText();
            StringBuilder sb = new StringBuilder("Sugeridos:\n");
            boolean haySugeridos = false;
            for (Trabajador t : conector.getTrabajadores()) {
                if (t.getEspecializacion().equalsIgnoreCase(especialidad) && t.getHorasTotalesAsignadas() < 160) {
                    int horasDisp = 160 - t.getHorasTotalesAsignadas();
                    sb.append(t.getNombre()).append(" | ").append(t.getCargo()).append(" | ").append(horasDisp).append("h\n");
                    haySugeridos = true;
                }
            }
            if (!haySugeridos) sb.append("No hay trabajadores disponibles con esa especialidad.");
            mostrarAlerta(sb.toString());
        });

        btnDisponibilidad.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("-- Disponibilidad de Trabajadores --\n");
            for (Trabajador t : conector.getTrabajadores()) {
                int horasAsignadas = t.getHorasTotalesAsignadas();
                int horasDisponibles = 160 - horasAsignadas;
                sb.append(t.getNombre()).append(" | ").append(t.getEspecializacion())
                        .append(" | ").append(horasDisponibles).append("h disponibles\n");
            }
            mostrarAlerta(sb.toString());
        });

        btnExportarUna.setOnAction(e -> {
            int idxCons = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idxCons >= 0) {
                Consultoria c = conector.getConsultorias().get(idxCons);
                if (!c.getEstado().equalsIgnoreCase("Terminado")) {
                    mostrarAlerta("La consultoría debe estar 'Terminado' para exportar.");
                    return;
                }
                StringBuilder sb = new StringBuilder("--- Reporte de Consultoría ---\n");
                sb.append("Nombre: ").append(c.getNombre()).append("\n");
                sb.append("Descripción: ").append(c.getDescripcion()).append("\n");
                sb.append("Presupuesto: $").append(c.getCostoTotalPresupuestado()).append("\n");
                sb.append("Costo final: $").append(c.calcularCostoReal()).append("\n");
                sb.append("Consultores asignados:\n");
                for (Trabajador t : c.getConsultores()) {
                    sb.append("- ").append(t.getNombre()).append("\n");
                }
                mostrarAlerta(sb.toString());
            }
        });

        btnExportarTodas.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("--- Reporte General de Consultorías ---\n");
            for (Consultoria c : conector.getConsultorias()) {
                if (c.getEstado().equalsIgnoreCase("Terminado")) {
                    sb.append("Nombre: ").append(c.getNombre()).append("\n");
                    sb.append("Descripción: ").append(c.getDescripcion()).append("\n");
                    sb.append("Presupuesto: $").append(c.getCostoTotalPresupuestado()).append("\n");
                    sb.append("Costo final: $").append(c.calcularCostoReal()).append("\n");
                    sb.append("Responsable: ").append(c.getResponsable() != null ? c.getResponsable().getNombre() : "No asignado").append("\n");
                    sb.append("-----------------------------\n");
                }
            }
            mostrarAlerta(sb.toString());
        });

        btnImprimirRegistos.setOnAction(e -> {
            int idxCons = listaConsultorias.getSelectionModel().getSelectedIndex();
            if (idxCons >= 0) {
                Consultoria c = conector.getConsultorias().get(idxCons);
                StringBuilder sb = new StringBuilder("--- Registros de pagos de la consultoría: " + c.getNombre() + " ---\n");
                for (ConsultoriaPago pago : c.getPagos()) {
                    sb.append("Fecha: ").append(pago.getFecha())
                            .append("\nConsultoría: ").append(pago.getConsultoria().getNombre())
                            .append("\nHoras Pagadas: ").append(pago.getHorasPagadas())
                            .append("\nTrabajador: ").append(pago.getTrabajadorPagado().getNombre())
                            .append("\n-----------------------------\n");
                }
                mostrarAlerta(sb.toString());
            } else {
                mostrarAlerta("Seleccione una consultoría para imprimir sus registros.");
            }
        });

        // --- Layout final ---
        root.setLeft(panelListas);
        root.setCenter(tabPane);
        root.setRight(panelDetalleConsultoria);

        Scene scene = new Scene(root, 1200, 720);
        scene.getStylesheets().add(getClass().getResource("estilo.css") == null ? "" : getClass().getResource("estilo.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Sistema de Consultorías");
        stage.show();
    }

    private void actualizarListas() {
        listaTrabajadores.getItems().setAll(conector.getTrabajadoresFormateados());
        listaConsultorias.getItems().setAll(conector.getConsultoriasFormateadas());
    }

    private void mostrarTiemposTrabajador(int idx) {
        listaTiempos.getItems().setAll(conector.getTiemposTrabajadorFormateados(idx));
    }

    private void mostrarDetalleConsultoria(int idx) {
        panelDetalleConsultoria.getChildren().clear();
        if (idx >= 0 && idx < conector.getConsultorias().size()) {
            Consultoria c = conector.getConsultorias().get(idx);
            VBox box = new VBox(8,
                    new Label("Nombre: " + c.getNombre()),
                    new Label("Estado: " + c.getEstado()),
                    new Label("ID: " + c.getCodigoID()),
                    new Label("Descripción: " + c.getDescripcion()),
                    new Label("Estado: " + c.getEstado()),
                    new Label("Tipo: " + c.getTipoConsultoria()),
                    new Label("Fecha inicio: " + c.getFechaInicio()),
                    new Label("Fecha fin: " + c.getFechaFin()),
                    new Label("Presupuesto: $" + c.getCostoTotalPresupuestado()),
                    new Label("Costo real: $" + c.calcularCostoReal()),
                    new Label("Responsable: " + (c.getResponsable() != null ? c.getResponsable().getNombre() : "No asignado")),
                    new Label("Consultores: " + c.getConsultores().stream().map(Trabajador::getNombre).reduce((a, b) -> a + ", " + b).orElse("Ninguno"))
            );
            box.setPadding(new Insets(8));
            box.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 6; -fx-background-radius: 6;");
            panelDetalleConsultoria.getChildren().add(box);
        } else {
            panelDetalleConsultoria.getChildren().add(new Label("Seleccione una consultoría para ver detalles."));
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }

    private void cargarDatosDePrueba() {
        Trabajador admin1 = new Trabajador("Lucía Pérez", "Administrador", "Finanzas");
        Trabajador admin2 = new Trabajador("Carlos Gómez", "Administrador", "Marketing");
        Trabajador consult1 = new Trabajador("Ana Torres", "Consultor", "TI");
        Trabajador consult2 = new Trabajador("Miguel Ruiz", "Consultor", "Logística");
        Trabajador consult3 = new Trabajador("Sofía Acosta", "Consultor", "TI");

        conector.agregarTrabajador(admin1);
        conector.agregarTrabajador(admin2);
        conector.agregarTrabajador(consult1);
        conector.agregarTrabajador(consult2);
        conector.agregarTrabajador(consult3);

        Consultoria c1 = new Consultoria(
                "Transformación Digital",
                "Implementación de sistema ERP en empresa logística",
                40,
                80,
                Date.valueOf("2025-07-01"),
                Date.valueOf("2025-08-01"),
                "Tecnología"
        );

        Consultoria c2 = new Consultoria(
                "Reingeniería de Marketing",
                "Optimización de canales digitales para mejorar ventas",
                30,
                60,
                Date.valueOf("2025-06-01"),
                Date.valueOf("2025-07-15"),
                "Marketing"
        );

        TiempoConsultoria tiempo1 = new TiempoConsultoria(admin1, c1, 40);
        TiempoConsultoria tiempo2 = new TiempoConsultoria(consult1, c1, 40);
        TiempoConsultoria tiempo3 = new TiempoConsultoria(consult2, c1, 40);

        admin1.asignarTiempoConsultoria(tiempo1);
        consult1.asignarTiempoConsultoria(tiempo2);
        consult2.asignarTiempoConsultoria(tiempo3);

        c1.asignarResponsable(admin1);
        c1.agregarConsultor(consult1);
        c1.agregarConsultor(consult2);

        conector.agregarConsultoria(c1);
        conector.agregarConsultoria(c2);
    }
}