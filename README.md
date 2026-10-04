# Assignment 3 | Bridge Pattern

**Student:** Zhassyn Zhalynuly  
**Group:** SE-2527  
**Topic:** A - Drawing  

> The code is intentionally small and beginner-friendly. Each class has one clear job, and the demo uses simple fixed values from the assignment.

## 1. Simple idea

There are two independent sides:

1. **Shapes:** `Shape` -> `Circle`, `Square`.
2. **Renderers:** `Renderer` -> `VectorRenderer`, `RasterRenderer`, `AsciiRenderer`.

The Bridge is the `Renderer implementation` field inside `Shape`. A shape does not know a concrete renderer class. It only calls methods from the `Renderer` interface.

This means we can combine any shape with any renderer without creating classes such as `VectorCircle` or `RasterSquare`.

## 2. Role map

| Bridge role | Class | Source file |
|---|---|---|
| Abstraction | `Shape` | `src/Shape.java` |
| A1 | `Circle` | `src/Circle.java` |
| A2 | `Square` | `src/Square.java` |
| Implementor | `Renderer` | `src/Renderer.java` |
| I1 | `VectorRenderer` | `src/VectorRenderer.java` |
| I2 | `RasterRenderer` | `src/RasterRenderer.java` |
| I3 | `AsciiRenderer` | `src/AsciiRenderer.java` |
| Client | `Main` | `src/Main.java` |

## 3. Important places in the code

- Bridge field: `src/Shape.java:3`
- Constructor receives a `Renderer`: `src/Shape.java:5-8`
- Runtime replacement: `src/Shape.java:14-16`
- `execute()` contract: `src/Shape.java:22`
- Circle delegation: `src/Circle.java:14-15`
- Square delegation: `src/Square.java:14-15`
- T5 same-object runtime switch: `src/Main.java:46-76`

## 4. Build and run

Run these commands from the project folder:

```bash
javac --release 17 -encoding UTF-8 -d out -sourcepath src src/Main.java
java -cp out Main --demo
```

The final line should be:

```text
SUMMARY: 7/7 PASS
```

## 5. Expected checks

| Check | What is tested | Expected result |
|---|---|---|
| T1 | Circle + VectorRenderer | `VECTOR circle radius=2` |
| T2 | Circle + RasterRenderer | `RASTER circle radius=2` |
| T3 | Square + VectorRenderer | `VECTOR square side=3` |
| T4 | Square + RasterRenderer | `RASTER square side=3` |
| T5 | Same Circle: Vector -> Raster | same object, same ID/radius, different renderer output |
| T6 | Circle + AsciiRenderer | `ASCII circle radius=2` |
| T7 | Square + AsciiRenderer | `ASCII square side=3` |

## 6. T5 in simple words

The program creates one `Circle` object. It first uses `VectorRenderer`. Then it calls:

```java
switchCircle.setImplementation(raster);
```

Only the renderer reference changes. The Circle object, its ID, and its radius stay the same. The `==` comparison proves that the reference before and after still points to the same object.

## 7. Independent extension

The base solution has Vector and Raster renderers. The extension adds only:

- new `src/AsciiRenderer.java`;
- T6 and T7 code in `src/Main.java`.

The existing `Shape`, `Circle`, `Square`, `Renderer`, `VectorRenderer`, and `RasterRenderer` files are unchanged by the extension.

## 8. Bridge vs Adapter

**Bridge:** designed from the start to separate abstraction from implementation so both can change independently.

**Adapter:** used when an existing class has an incompatible interface and we need to make it work with another interface.

This project uses Bridge because shapes and renderers are two planned independent hierarchies.

## 9. Files

- `src/` - Java source files.
- `uml.png` - UML diagram matching the code.
- `report.pdf` - short English report.
