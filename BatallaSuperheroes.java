import java.util.Random;

abstract class Personaje {
    protected String nombre;
    protected int fuerza;
    protected int velocidad;
    protected int vidaHp;

    public Personaje(String nombre, int fuerza, int velocidad, int vidaHp) {
        this.nombre = nombre;
        this.fuerza = fuerza;
        this.velocidad = velocidad;
        this.vidaHp = vidaHp;
    }

    public void mostrarEstadisticas() {
        System.out.println("---- " + nombre + " ----");
        System.out.println("Fuerza: " + fuerza + " | Velocidad: " + velocidad + " | Vida HP: " + vidaHp);
        System.out.println("-------------------------");
    }

    public abstract void ataqueEspecial(Personaje oponente);

    public void atacar(Personaje oponente) {
        int dano = obtenerDanoAleatorio();
        oponente.recibirDanio(dano);
        System.out.println(nombre + " ataca a " + oponente.nombre + " y causa " + dano + " puntos de daño.");
    }

    public void defender(int dano) {
        int danoReducido = dano / 2;
        vidaHp -= danoReducido;
        System.out.println(nombre + " se defiende y solo recibe " + danoReducido + " puntos de daño.");
    }

    public void recuperar() {
        int vidaRecuperada = obtenerDanoAleatorio() / 2;
        vidaHp = Math.min(vidaHp + vidaRecuperada, 500);  // No sobrepasa los 500 HP.
        System.out.println(nombre + " recupera " + vidaRecuperada + " puntos de vida. Vida actual: " + vidaHp);
    }

    public void recibirDanio(int dano) {
        vidaHp -= dano;
        if (vidaHp < 0) vidaHp = 0;
    }

    // Cambiado de private a protected para que las subclases tengan acceso
    protected int obtenerDanoAleatorio() {
        Random rand = new Random();
        return rand.nextInt(fuerza) + 1;  // Genera un daño aleatorio basado en la fuerza.
    }
}

class SuperHeroe extends Personaje {
    public SuperHeroe(String nombre, int fuerza, int velocidad, int vidaHp) {
        super(nombre, fuerza, velocidad, vidaHp);
    }

    @Override
    public void ataqueEspecial(Personaje oponente) {
        int dano = obtenerDanoAleatorio() * 2;  // Ataque especial hace el doble de daño.
        oponente.recibirDanio(dano);
        System.out.println(nombre + " usa su ATAQUE ESPECIAL contra " + oponente.nombre + " causando " + dano + " puntos de daño.");
    }
}

class Villano extends Personaje {
    public Villano(String nombre, int fuerza, int velocidad, int vidaHp) {
        super(nombre, fuerza, velocidad, vidaHp);
    }

    @Override
    public void ataqueEspecial(Personaje oponente) {
        int dano = obtenerDanoAleatorio() * 2;  // El villano hace trampa y duplica el ataque.
        oponente.recibirDanio(dano);
        System.out.println(nombre + " usa su ATAQUE ESPECIAL haciendo trampa contra " + oponente.nombre + " causando " + dano + " puntos de daño.");
    }
}

public class BatallaSuperheroes {
    public static void main(String[] args) {
        SuperHeroe spiderman = new SuperHeroe("Spiderman", 50, 30, 500);
        Villano thanos = new Villano("Thanos", 60, 25, 500);

        System.out.println("=== Estadísticas Iniciales ===");
        spiderman.mostrarEstadisticas();
        thanos.mostrarEstadisticas();

        System.out.println("\n=== ¡La Batalla Comienza! ===");

        Random rand = new Random();

        // Simulación de varios turnos con decisiones y acciones aleatorias
        for (int turno = 1; turno <= 5; turno++) {
            System.out.println("\n--- Turno " + turno + " ---");
            // Spiderman elige entre ataque normal o especial
            if (rand.nextBoolean()) {
                spiderman.atacar(thanos);
            } else {
                spiderman.ataqueEspecial(thanos);
            }

            // Thanos elige entre defender o recuperar
            if (rand.nextBoolean()) {
                int dano = spiderman.obtenerDanoAleatorio();
                thanos.defender(dano);
            } else {
                thanos.recuperar();
            }

            // Thanos elige entre ataque normal o especial
            if (rand.nextBoolean()) {
                thanos.atacar(spiderman);
            } else {
                thanos.ataqueEspecial(spiderman);
            }

            // Spiderman elige entre defender o recuperar
            if (rand.nextBoolean()) {
                int dano = thanos.obtenerDanoAleatorio();
                spiderman.defender(dano);
            } else {
                spiderman.recuperar();
            }
        }

        System.out.println("\n=== Estadísticas Finales ===");
        spiderman.mostrarEstadisticas();
        thanos.mostrarEstadisticas();

        System.out.println("\n=== Resultado de la Batalla ===");
        if (spiderman.vidaHp > thanos.vidaHp) {
            System.out.println("¡Spiderman es el ganador!");
        } else if (thanos.vidaHp > spiderman.vidaHp) {
            System.out.println("¡Thanos es el ganador!");
        } else {
            System.out.println("¡Es un empate!");
        }
    }
}
