// 3.1.1 Java's type system—primitives and references
println (60 * 60 * 24 * 365).toString(); // invalid Java

println 60 * 60 * 24 * 365               // invalid Java

int secondsPerYear = 60 * 60 * 24 * 365;
secondsPerYear.toString(); // invalid Java

new Integer(secondsPerYear).toString();  // this is how it could work in Java

assert "abc" - "a" == "bc" // invalid Java
assert "abaca" - "a" == "baca"

// 3.1.2 Groovy's answer—everything's an object
assert 15.class.name == "java.lang.Integer"
assert 100L.class.name == "java.lang.Long"
assert 1.23f.class.name == "java.lang.Float"
assert 1.23d.class.name == "java.lang.Double"
assert 123g.class.name == "java.math.BigInteger"
assert (1.23).class.name == "java.math.BigDecimal"
assert 1.23.class.name == "java.math.BigDecimal"
assert 1.4E4.class.name == "java.math.BigDecimal"

// 3.2 Casting lists and maps to arbitrary classes
import java.awt.*

Point topLeft = new Point(0, 0) // classic
Point botRight = [100, 100] // List cast
Point center = [x:50, y:50] // Map cast
assert botRight instanceof Point
assert center instanceof Point
def rect = new Rectangle()
rect.location = [0, 0] // Point
rect.size = [width:100, height:100] // Dimension

