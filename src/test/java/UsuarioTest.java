import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UsuarioTest {

    @Test
    void actualizarPeso_deberiaActualizarCorrectamente() {
        Usuario u = new Usuario("Manuel", 99.0);
        u.actualizarPeso(80.0);
        assertEquals(80.0, u.getPeso(), "El peso debería actualizarse al valor nuevo");
    }
}