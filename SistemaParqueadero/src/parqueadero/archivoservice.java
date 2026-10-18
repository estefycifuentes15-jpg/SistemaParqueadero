package parqueadero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class archivoservice {

    private static final Path ARCHIVO_VEHICULOS =
            Paths.get("vehiculos.txt");

    private static final Path ARCHIVO_CAJA =
            Paths.get("caja.txt");

    private static void escribirLinea(
            Path archivo, String linea) throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            escritor.write(linea);
            escritor.newLine();
        }
    }

    public static void registrarIngreso(vehiculo v)
            throws IOException {

        escribirLinea(ARCHIVO_VEHICULOS,
                "E;" + v.getPlaca()
                + ";" + v.getTipo()
                + ";" + v.getFechaIngreso().toString());
    }

    public static void registrarSalida(
            vehiculo v,
            LocalDateTime fechaSalida,
            long minutos,
            long horas,
            double valor) throws IOException {

        escribirLinea(ARCHIVO_VEHICULOS,
                "S;" + v.getPlaca()
                + ";" + v.getTipo()
                + ";" + fechaSalida
                + ";" + minutos
                + ";" + horas
                + ";" + String.format(
                        java.util.Locale.US, "%.2f", valor));

        escribirLinea(ARCHIVO_CAJA,
                fechaSalida + ";"
                + v.getPlaca() + ";"
                + horas + ";"
                + String.format(
                        java.util.Locale.US, "%.2f", valor));
    }

    public static Map<String, vehiculo> cargarVehiculos()
            throws IOException {

        Map<String, vehiculo> activos = new LinkedHashMap<>();

        if (!Files.exists(ARCHIVO_VEHICULOS)) {
            return activos;
        }

        try (BufferedReader lector = Files.newBufferedReader(
                ARCHIVO_VEHICULOS, StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                try {
                    if (datos[0].equals("E")
                            && datos.length >= 4) {

                        vehiculo v = new vehiculo(
                                datos[1],
                                datos[2],
                                LocalDateTime.parse(datos[3]));

                        activos.put(datos[1], v);

                    } else if (datos[0].equals("S")
                            && datos.length >= 7) {

                        activos.remove(datos[1]);
                    }

                } catch (RuntimeException e) {
                    System.err.println(
                            "Registro inválido: " + linea);
                }
            }
        }

        return activos;
    }

    public static double calcularTotalCaja()
            throws IOException {

        double total = 0;

        if (!Files.exists(ARCHIVO_CAJA)) {
            return total;
        }

        try (BufferedReader lector = Files.newBufferedReader(
                ARCHIVO_CAJA, StandardCharsets.UTF_8)) {

            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";", -1);

                if (datos.length >= 4) {
                    try {
                        total += Double.parseDouble(datos[3]);
                    } catch (NumberFormatException e) {
                        System.err.println(
                                "Cobro inválido: " + linea);
                    }
                }
            }
        }

        return total;
    }
}