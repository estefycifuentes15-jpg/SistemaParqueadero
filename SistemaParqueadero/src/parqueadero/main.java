package parqueadero;

import java.io.IOException;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);

        try {
            parqueadero p = new parqueadero();

            int opcion;

            do {
                System.out.println("\n..........................");
                System.out.println("  SISTEMA DE PARQUEADERO");
                System.out.println(".............................");
                System.out.println("1. Registrar ingreso de vehículo");
                System.out.println("2. Registrar salida de vehículo");
                System.out.println("3. Listar vehículos en el parqueadero");
                System.out.println("4. Consultar caja");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opción: ");

                String entrada = teclado.nextLine().trim();

                try {
                    opcion = Integer.parseInt(entrada);
                } catch (NumberFormatException e) {
                    System.out.println(
                            "Ingrese una opción numérica válida.");
                    opcion = -1;
                    continue;
                }

                try {
                    switch (opcion) {

                        case 1:
                            System.out.print("Ingrese la placa: ");
                            String placa = teclado.nextLine();

                            System.out.print(
                                    "Ingrese el tipo de vehículo: ");
                            String tipo = teclado.nextLine();

                            p.ingresarVehiculo(placa, tipo);
                            break;

                        case 2:
                            System.out.print(
                                    "Ingrese la placa del vehículo: ");
                            String placaSalida = teclado.nextLine();

                            p.registrarSalida(placaSalida);
                            break;

                        case 3:
                            p.listarVehiculos();
                            break;

                        case 4:
                            p.mostrarCaja();
                            break;

                        case 0:
                            System.out.println(
                                    "Gracias por utilizar el sistema.");
                            break;

                        default:
                            System.out.println("Opción no válida.");
                    }

                } catch (IOException e) {
                    System.out.println(
                            "Error al trabajar con archivos: "
                            + e.getMessage());
                }

            } while (opcion != 0);

        } catch (IOException e) {
            System.out.println(
                    "No se pudo cargar la información: "
                    + e.getMessage());
        } finally {
            teclado.close();
        }
    }
}