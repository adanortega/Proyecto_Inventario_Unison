package mx.unison.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "almacenes")
public class Almacen {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String nombre;

    @DatabaseField
    private String ubicacion;

    // Constructor vacío obligatorio para ORMLite
    public Almacen() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    /**
     * Este método es vital para la interfaz gráfica.
     * Permite que los ComboBox y listas muestren el nombre
     * en lugar de la dirección de memoria del objeto.
     */
    @Override
    public String toString() {
        return (nombre != null) ? nombre : "Almacén sin nombre";
    }
}