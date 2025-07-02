package devops;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {

        port(8080); // localhost:8080

        // ---------- Página principal ----------
        get("/", (req, res) -> ""
            + "<!DOCTYPE html><html><head><meta charset='utf-8'><title>Peso Usuario</title></head><body>"
            + "<h2>Actualizar peso</h2>"
            + "<form action='/actualizar' method='post'>"
            + "<label>Nombre:</label> <input id='nombre' name='nombre'><br>"
            + "<label>Peso inicial:</label> <input id='peso' name='peso'><br>"
            + "<label>Nuevo peso:</label> <input id='nuevoPeso' name='nuevoPeso'><br>"
            + "<button type='submit'>Actualizar</button>"
            + "</form></body></html>");

        // ---------- Procesa formulario y muestra resultado ----------
        post("/actualizar", (req, res) -> {
            Usuario u = new Usuario(
                req.queryParams("nombre"),
                Double.parseDouble(req.queryParams("peso")));

            u.actualizarPeso(Double.parseDouble(req.queryParams("nuevoPeso")));

            return "<!DOCTYPE html><html><body>"
                 + "<h3>Resultado</h3>"
                 + "<p>Nombre: " + u.getNombre() + "</p>"
                 + "<p>pesoActual" + u.getPeso() + "</p>"
                 + "</body></html>";
        });
    }
}
