public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo")) {
            runDemo();
        } else {
            System.out.println("Run with: java -cp out Main --demo");
        }
    }

    private static void runDemo() {
        int passed = 0;

        Renderer vector = new VectorRenderer();
        Renderer raster = new RasterRenderer();
        Renderer ascii = new AsciiRenderer();

        // T1: Circle with VectorRenderer
        Circle circleVector = new Circle("circle-2", 2, vector);
        if (check("T1", "Circle + VectorRenderer", circleVector.execute(),
                "VECTOR circle radius=2")) {
            passed++;
        }

        // T2: Circle with RasterRenderer
        Circle circleRaster = new Circle("circle-2", 2, raster);
        if (check("T2", "Circle + RasterRenderer", circleRaster.execute(),
                "RASTER circle radius=2")) {
            passed++;
        }

        // T3: Square with VectorRenderer
        Square squareVector = new Square("square-3", 3, vector);
        if (check("T3", "Square + VectorRenderer", squareVector.execute(),
                "VECTOR square side=3")) {
            passed++;
        }

        // T4: Square with RasterRenderer
        Square squareRaster = new Square("square-3", 3, raster);
        if (check("T4", "Square + RasterRenderer", squareRaster.execute(),
                "RASTER square side=3")) {
            passed++;
        }

        // T5: change only the renderer on the same Circle object
        Circle switchCircle = new Circle("circle-switch", 2, vector);
        Circle referenceBefore = switchCircle;
        String idBefore = switchCircle.getId();
        int radiusBefore = switchCircle.getRadius();
        String before = switchCircle.execute();

        switchCircle.setImplementation(raster);

        Circle referenceAfter = switchCircle;
        String after = switchCircle.execute();

        boolean sameObject = referenceBefore == referenceAfter;
        boolean sameData = idBefore.equals(switchCircle.getId())
                && radiusBefore == switchCircle.getRadius();
        boolean correctResults = before.equals("VECTOR circle radius=2")
                && after.equals("RASTER circle radius=2");
        boolean t5Passed = sameObject && sameData && correctResults;

        String t5Status = "FAIL";
        if (t5Passed) {
            t5Status = "PASS";
            passed++;
        }

        System.out.println("T5 " + t5Status
                + " | sameObject=" + sameObject
                + " | stateUnchanged=" + sameData);
        System.out.println("  before=" + before + " | after=" + after);

        if (!t5Passed) {
            System.out.println("  expected: same object, same id/radius, Vector before, Raster after");
        }

        // T6: Circle with the new AsciiRenderer
        Circle circleAscii = new Circle("circle-2", 2, ascii);
        if (check("T6", "Circle + AsciiRenderer", circleAscii.execute(),
                "ASCII circle radius=2")) {
            passed++;
        }

        // T7: Square with the new AsciiRenderer
        Square squareAscii = new Square("square-3", 3, ascii);
        if (check("T7", "Square + AsciiRenderer", squareAscii.execute(),
                "ASCII square side=3")) {
            passed++;
        }

        System.out.println("SUMMARY: " + passed + "/7 PASS");
    }

    private static boolean check(String testId, String classes, String actual, String expected) {
        boolean passed = actual.equals(expected);
        String status = "FAIL";

        if (passed) {
            status = "PASS";
        }

        System.out.println(testId + " " + status
                + " | " + classes + " | result=" + actual);

        if (!passed) {
            System.out.println("  expected=" + expected);
        }

        return passed;
    }
}
