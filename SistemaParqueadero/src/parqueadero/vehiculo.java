package parqueadero;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class vehiculo {

    private String placa;
    private String tipo;
    private LocalDateTime fechaIngreso;

    public vehiculo(String placa, String tipo,
                    LocalDateTime fechaIngreso) {
        this.placa = placa;
        this.tipo = tipo;
        this.fechaIngreso = fechaIngreso;
    }

    public String getPlaca() {
        return placa;
    }

    public String getTipo() {
        return tipo;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public String getFechaIngresoTexto() {
        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return fechaIngreso.format(formato);
    }

    @Override
    public String toString() {
        return "Placa: " + placa
                + " | Tipo: " + tipo
                + " | Ingreso: " + getFechaIngresoTexto();
    }
}