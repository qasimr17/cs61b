import edu.princeton.cs.algs4.StdRandom;
import org.reflections.vfs.Vfs;

import javax.servlet.http.Part;
import java.awt.*;
import java.util.Map;

public class Particle {
    public ParticleFlavor flavor;
    public int lifespan;

    public static final int PLANT_LIFESPAN = 150;
    public static final int FLOWER_LIFESPAN = 75;
    public static final int FIRE_LIFESPAN = 10;
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                   ParticleFlavor.PLANT, PLANT_LIFESPAN,
                   ParticleFlavor.FIRE, FIRE_LIFESPAN);

    public Particle(ParticleFlavor flavor) {
        this.flavor = flavor;
        this.lifespan = LIFESPANS.getOrDefault(this.flavor, -1);
    }

    public Color color() {
        switch (flavor) {
            case ParticleFlavor.EMPTY -> {
                return Color.BLACK;
            }
            case ParticleFlavor.SAND -> {
                return Color.YELLOW;
            }
            case ParticleFlavor.BARRIER -> {
                return Color.GRAY;
            }
            case ParticleFlavor.WATER -> {
                return Color.BLUE;
            }
            case ParticleFlavor.FOUNTAIN -> {
                return Color.CYAN;
            }
            case ParticleFlavor.PLANT -> {
                double ratio = (double) Math.clamp(lifespan, 0, PLANT_LIFESPAN) / PLANT_LIFESPAN;
                int g = 120 + (int) Math.round((255 - 120) * ratio);
                return new Color(0, g, 0);
            }
            case ParticleFlavor.FIRE -> {
                double ratio = (double) Math.clamp(lifespan, 0, FIRE_LIFESPAN) / FIRE_LIFESPAN;
                int r = (int) Math.round(255 * ratio);
                return new Color(r, 0, 0);
            }
            case ParticleFlavor.FLOWER -> {
                double ratio = (double) Math.clamp(lifespan, 0, FLOWER_LIFESPAN) / FLOWER_LIFESPAN;
                int r = 120 + (int) Math.round((255 - 120) * ratio);
                int g = 70 + (int) Math.round((141 - 70) * ratio);
                int b = 80 + (int) Math.round((161 - 80) * ratio);
                return new Color(r, g, b);
            }
            default -> {
                return Color.gray;
            }
        }
    }

    public void moveInto(Particle other) {
        // convert other particle into 'this' particle
        other.flavor = this.flavor;
        other.lifespan = this.lifespan;

        // destroy 'this' particle
        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void fall(Map<Direction, Particle> neighbors) {
        // Find neighbor below
        Particle neighbor = neighbors.get(Direction.DOWN);

        if (neighbor.flavor == ParticleFlavor.EMPTY) {
            this.moveInto(neighbor);
        }
    }

    public void flow(Map<Direction, Particle> neighbors) {
        int choice = StdRandom.uniformInt(3);

        if (choice == 1 && neighbors.get(Direction.LEFT).flavor == ParticleFlavor.EMPTY) {
            this.moveInto(neighbors.get(Direction.LEFT));
        } else if (choice == 2 && neighbors.get(Direction.RIGHT).flavor == ParticleFlavor.EMPTY) {
            this.moveInto(neighbors.get(Direction.RIGHT));
        }
    }

    public void grow(Map<Direction, Particle> neighbors) {
        int choice = StdRandom.uniformInt(10);

        Direction direction = switch (choice) {
            case 0 -> Direction.UP;
            case 1 -> Direction.LEFT;
            case 2 -> Direction.RIGHT;
            default -> null;
        };

        if (direction == null) {
            return;
        }

        Particle neighbor = neighbors.get(direction);

        if (neighbor.flavor == ParticleFlavor.EMPTY) {
            neighbor.flavor = this.flavor;
            neighbor.lifespan = LIFESPANS.get(this.flavor);
        }
    }

    public void burn(Map<Direction, Particle> neighbors) {

        for (Direction direction : Direction.values()) {
            int choice = StdRandom.uniformInt(10);
            Particle neighbor = neighbors.get(direction);

            if (neighbor.flavor == ParticleFlavor.PLANT || neighbor.flavor == ParticleFlavor.FLOWER) {
                if (choice < 4) {
                    neighbor.flavor = ParticleFlavor.FIRE;
                    neighbor.lifespan = LIFESPANS.get(ParticleFlavor.FIRE);
                }
            }
        }
    }

    public void action(Map<Direction, Particle> neighbors) {
        if (this.flavor == ParticleFlavor.EMPTY) {
            return;
        }
        if (this.flavor != ParticleFlavor.BARRIER) {
            this.fall(neighbors);
        }
        if (this.flavor == ParticleFlavor.WATER) {
            this.flow(neighbors);
        }
        if (this.flavor == ParticleFlavor.PLANT || this.flavor == ParticleFlavor.FLOWER) {
            this.grow(neighbors);
        }
        if (this.flavor == ParticleFlavor.FIRE) {
            this.burn(neighbors);
        }
    }

    public void decrementLifespan() {
        if (this.lifespan > 0) {
            this.lifespan -= 1;
        }
        if (this.lifespan == 0) {
            this.flavor = ParticleFlavor.EMPTY;
            this.lifespan = -1;
        }
    }
}