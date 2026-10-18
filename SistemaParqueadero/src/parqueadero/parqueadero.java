package parqueadero;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

public class parqueadero {

    private static final double TARIFA = 0.50;

    private Map<String, vehiculo> vehiculos;

    public parqueadero() throws IOException {
        vehiculos = archivoservice.cargarVehiculos();
    }

    public void ingresarVehiculo(String placa, String tipo)
            throws IOException {

        placa = placa.trim().toUpperCase();
        tipo = tipo.trim();

        if (placa.isEmpty() || tipo.isEmpty()) {
            System.out.println(
                    "La placa y el tipo son obligatorios.");
            return;
        }

        if (vehiculos.containsKey(placa)) {
            System.out.println(
                    "Este vehículo ya está en el parqueadero.");
            return;
        }

        vehiculo v = new vehiculo(
                placa, tipo, LocalDateTime.now());

        archivoservice.registrarIngreso(v);
        vehiculos.put(placa, v);

        System.out.println("Ingreso registrado correctamente.");
        System.out.println(v);
    }

    public void registrarSalida(String placa)
            throws IOException {

        placa = placa.trim().toUpperCase();

        vehiculo v = vehiculos.get(placa);

        if (v == null) {
            System.out.println(
                    "No se encontró un vehículo con esa placa.");
            return;
        }

        LocalDateTime fechaSalida = LocalDateTime.now();

        long segundos = Duration.between(
                v.getFechaIngreso(), fechaSalida).getSeconds();

        long minutos = Math.max(0, (segundos + 59) / 60);
        long horas = Math.max(1, (segundos + 3599) / 3600);

        double valor = horas * TARIFA;

        archivoservice.registrarSalida(
                v, fechaSalida, minutos, horas, valor);

        vehiculos.remove(placa);

        System.out.println("\n--- COMPROBANTE DE SALIDA ---");
        System.out.println("Placa: " + v.getPlaca());
        System.out.println("Tipo: " + v.getTipo());
        System.out.println("Ingreso: " + v.getFechaIngresoTexto());
        System.out.println("Salida: " + fechaSalida);
        System.out.println("Tiempo: " + minutos + " minutos");
        System.out.println("Horas o fracciones: " + horas);
        System.out.printf("Valor a cancelar: $%.2f%n", valor);
    }

    public void listarVehiculos() {

        if (vehiculos.isEmpty()) {
            System.out.println(
                    "No hay vehículos en el parqueadero.");
            return;
        }

        System.out.println("\n--- VEHÍCULOS EN EL PARQUEADERO ---");

        for (vehiculo v : vehiculos.values()) {
            System.out.println(v);
        }

        System.out.println("Total de vehículos: "
                + vehiculos.size());
    }

    public void mostrarCaja() throws IOException {

        double total = archivoservice.calcularTotalCaja();

        System.out.println("\n--- CAJA DEL PARQUEADERO ---");
        System.out.printf("Total recaudado: $%.2f%n", total);
    }
}