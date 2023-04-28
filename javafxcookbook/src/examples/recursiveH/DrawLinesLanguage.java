package examples.recursiveH;

import javafx.scene.shape.Line;

public sealed interface DrawLinesLanguage {}

record DrawLine(Line line) implements DrawLinesLanguage {}
final class Stop implements DrawLinesLanguage {}
