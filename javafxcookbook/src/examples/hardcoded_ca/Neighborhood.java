package examples.hardcoded_ca;

public record Neighborhood(Generation.Cell left, Generation.Cell middle, Generation.Cell right) {
    @Override
    public String toString() {
        return left + " <- " + middle + " -> " + right;
    }

    public boolean equals(final char leftCell, final char middleCell, final char rightCell) {
        return this.equals(new Neighborhood(
                Generation.Cell.fromChar(leftCell),
                Generation.Cell.fromChar(middleCell),
                Generation.Cell.fromChar(rightCell)));
    }
}

//import java.util.Objects;
//
//public class Neighborhood {
//    private final Generation.Cell left;
//    private final Generation.Cell middle;
//    private final Generation.Cell right;
//
//
//    public Neighborhood(Generation.Cell left, Generation.Cell middle, Generation.Cell right) {
//        this.left = left;
//        this.middle = middle;
//        this.right = right;
//    }
//
//    @Override
//    public String toString() {
//        return "Neighborhood{" +
//                "left=" + left +
//                ", middle=" + middle +
//                ", right=" + right +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Neighborhood that = (Neighborhood) o;
//        return left == that.left && middle == that.middle && right == that.right;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(left, middle, right);
//    }
//}
