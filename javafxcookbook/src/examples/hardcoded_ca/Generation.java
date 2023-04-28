package examples.hardcoded_ca;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Generation implements Iterable<Neighborhood> {
    public enum Cell {
        ALIVE {
            @Override
            public String toString() {
                return "1";
            }
        }, DEAD {
            @Override
            public String toString() {
                return "0";
            }
        };

        public static Cell fromChar(final char c) {
            if (c == '1') {
                return ALIVE;
            }
            else if (c == '0') {
                return DEAD;
            }
            throw new IllegalArgumentException("Argument, " + c + ", must be either 1 or 0");
        }
    }

    private final List<Cell> generation;

    public Generation(final List<Cell> generation) {
        this.generation = generation;
    }

    /**
     * Evolve this generation using rule 30
     * Rule 30 is given by the following binary string:
     * 00011110 (30 in 8 bit binary)
     * This can be interpreted as follows:
     * 111 -> 0
     * 110 -> 0
     * 101 -> 0
     * 100 -> 1
     * 011 -> 1
     * 010 -> 1
     * 001 -> 1
     * 000 -> 0
     * @return New generation evolved from old generation
     */
    public Generation evolve() {
        final List<Cell> newGen = new ArrayList<>();

        for (final Neighborhood neighborhood : this) {
            if (neighborhood.equals('1', '1', '1')) {
                newGen.add(Cell.DEAD);
            }
            else if (neighborhood.equals('1', '1', '0')) {
                newGen.add(Cell.DEAD);
            }
            else if (neighborhood.equals('1', '0', '1')) {
                newGen.add(Cell.DEAD);
            }
            else if (neighborhood.equals('1', '0', '0')) {
                newGen.add(Cell.ALIVE);
            }
            else if (neighborhood.equals('0', '1', '1')) {
                newGen.add(Cell.ALIVE);
            }
            else if (neighborhood.equals('0', '1', '0')) {
                newGen.add(Cell.ALIVE);
            }
            else if (neighborhood.equals('0', '0', '1')) {
                newGen.add(Cell.ALIVE);
            }
            else {
                newGen.add(Cell.DEAD);
            }
        }

        return new Generation(newGen);
    }

    public int size() {
        return generation.size();
    }

    private Cell get(final int index) {
        return generation.get(Math.floorMod(index, generation.size()));
    }

    @Override
    public Iterator<Neighborhood> iterator() {
        return new Iterator<>() {
            private int curIndex = 0;

            @Override
            public boolean hasNext() {
                return curIndex < generation.size();
            }

            @Override
            public Neighborhood next() {
                // Make generation.get mistake first
                final Cell left = Generation.this.get(curIndex - 1);
                final Cell middle = Generation.this.get(curIndex);
                final Cell right = Generation.this.get(curIndex + 1);

                curIndex++;

                return new Neighborhood(left, middle, right);
            }
        };
    }

    @Override
    public String toString() {
        return "Generation{" +
                "generation=" + generation +
                '}';
    }

    public static void main(final String[] args) {
        final List<Cell> testList = Arrays.asList(Cell.DEAD, Cell.DEAD, Cell.ALIVE, Cell.DEAD, Cell.DEAD);
        final Generation testGen = new Generation(testList);
        // Testing get
        System.out.println("testGen.generation.size() = " + testGen.generation.size());
        System.out.println("testGen.get(2) = " + testGen.get(2));
        System.out.println("testGen.get(-1) = " + testGen.get(-1));
        System.out.println("testGen.get(-3) = " + testGen.get(-3));
        System.out.println("testGen.get(5) = " + testGen.get(5));
        System.out.println("testGen.get(7) = " + testGen.get(7));
        // Testing Iterator
        for (final Neighborhood neighborhood : testGen) {
            System.out.println("neighborhood = " + neighborhood);
        }
        // Test evolving
        System.out.println("testGen.evolve() = " + testGen.evolve());
    }
}
