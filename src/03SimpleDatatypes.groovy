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


// 3.3.1 Overview of overridable operators
def a = 1
a=a.next()
assert a == 2
println a.unaryMinus()

def name = "kai"
assert name.getAt(1) == "a"

def b = 3
println  a.compareTo(b)
println  b.compareTo(a)


// 3.3.2 Overridden operators in action
import groovy.transform.Immutable

@Immutable class Money { // #1 overrides == operator
    int amount
    String currency

    Money plus (Money other) { // #2a implements + operator
        if (null == other) return this
        if (other.currency != currency) {
            throw new IllegalArgumentException(
                "cannot add $other.currency to $currency")
        }
        return new Money(amount + other.amount, currency)
    }

    Money plus (Integer other) { // #2b overload
        return new Money(amount + other, currency)
    }
}

Money buck = new Money(1, 'USD')
assert buck
assert buck == new Money(1, 'USD') // #3 use overridden ==
assert buck + buck == new Money(2, 'USD') // #4 use implemented +
assert buck + 1 == new Money(2, 'USD') // #4 use implemented +


// 3.4.1 Varieties of string literals
def myString = "hello\nthere"
def myChar = 'x'
def myChar2 = "x"

// Listing 3.4 Working with GStrings
def me = 'Tarzan'   //|#1 Abbreviated
def you = 'Jane'    //|#1 dollar syntax
def line = "me $me - you $you" //|#1
assert line == 'me Tarzan - you Jane'

def date = new Date(0)      //|#2 Extended
def out = "Year $date.year Month $date.month Day $date.date" //|#2 abbreviation
def out2 = "Year ${date.getYear()} Month ${date.getMonth()} Day ${date.getDate()}" //|#2 abbreviation
println out
println out2
// assert out == 'Year 70 Month 0 Day 1'
assert out == 'Year 69 Month 11 Day 31'     // ?? different than book

out = "Date is ${date.toGMTString()} !"     //|#3 Full syntax with
assert out == 'Date is 1 Jan 1970 00:00:00 GMT !' //|#3 braces

//#4 Multiline GStrings start
def sql = """
SELECT FROM MyTable
WHERE Year = $date.year
"""

/*  doesn't assert
assert sql ==
        """
SELECT FROM MyTable
WHERE Year = 70
"""     //#4 Multiline GStrings end
*/

assert sql ==
        """
SELECT FROM MyTable
WHERE Year = 69
"""     //#4 Multiline GStrings end


out = "my 0.02\$"           //#5 Escaped dollar sign
assert out == 'my 0.02$'    //#6 Literal dollar sign


def me2 = 'Tarzan'
def you2 = 'Jane'
def line2 = "me $me2 - you $you2"
assert line2 == 'me Tarzan - you Jane'
assert line2 instanceof GString
assert line2.strings[0] == 'me '
assert line2.strings[1] == ' - you '
assert line2.values[0] == 'Tarzan'
assert line2.values[1] == 'Jane'

// Listing 3.5 A miscellany of string operations
String greeting = 'Hello Groovy!'
assert greeting.startsWith('Hello')
assert greeting.getAt(0) == 'H'
assert greeting[0] == 'H'
assert greeting.indexOf('Groovy') >= 0
assert greeting.contains('Groovy')
assert greeting[6..11] == 'Groovy'  // wow!
assert 'Hi' + greeting - 'Hello' == 'Hi Groovy!'
assert greeting.count('o') == 3
assert 'x'.padLeft(3) == '  x'
assert 'x'.padRight(3,'_') == 'x__'
assert 'x'.center(3) == ' x '
assert 'x' * 3 == 'xxx'

// some more demoing StringBuffer usage
def greeting2 = 'Hello'
greeting2 <<= ' Groovy' // #1 Leftshift and assign
assert greeting2 instanceof java.lang.StringBuffer
greeting2 << '!' //#2 Leftshift on StringBuffer
assert greeting2.toString() == 'Hello Groovy!'
greeting2[1..4] = 'i' //#3 Substring 'ello' becomes 'i'   (surprised this doesn't become 'iiii'
assert greeting2.toString() == 'Hi Groovy!'

// Listing 3.6 Regular expression GStrings
assert "abc" == /abc/
assert "\\d" == /\d/
def reference = "hello"
assert reference == /$reference/    // $someExpression
assert reference == (/$reference/)  // force parser to interpret expression

// Listing 3.7 Regular expressions
def twister = 'she sells sea shells at the sea shore of seychelles'
// twister must contain a substring of size 3
// that starts with s and ends with a
assert twister =~ /s.a/ // #1 Regex find operator as

def finder = (twister =~ /s.a/) // #2 Find expression evaluates
assert finder instanceof java.util.regex.Matcher // #2 matcher object
println '1st match: ' + finder[0]
println '2nd match: ' + finder[1]

// twister must contain only words delimited by single spaces
assert twister ==~ /(\w+ \w+)*/ // #3 Regex match operator

def WORD = /\w+/
matches = (twister ==~ /($WORD $WORD)*/) // #4 Match expression evaluates
assert matches instanceof java.lang.Boolean // #4 to a boolean


// I don't get this.  Wouldn't 'she' match?
assert (twister ==~ /s.e/) == false // #5 Match is full unlike find
// Or is ==~ supposedly covers the whole string, why wouldn't this work?
// assert (twister ==~ /s.s/) == true // #5 Match is full unlike find

def wordsByX = twister.replaceAll(WORD, 'x')
assert wordsByX == 'x x x x x x x x x x'

def words = twister.split(/ /) // #6 Split returns a list of
assert words.size() == 10
assert words[0] == 'she'

twister.eachMatch( /s.e/, { println it })       // outputs all matched strings
twister.eachMatch( /s.*?e/, { println it })     // outputs all matched strings
twister.eachMatch( /s(.*?)e/, { println it })   // outputs array for each match (1st group is entire regex; 2nd is the group
// twister.eachMatch( /s(.*?)e/ ) { println it }   // same

// Listing 3.8 Working on each match of a pattern
def myFairStringy = 'The rain in Spain stays mainly in the plain!'
// words that end with 'ain': \b\w*ain\b
def wordEnding = /\w*ain/
def rhyme = /\b$wordEnding\b/
def found = ''
myFairStringy.eachMatch(rhyme) { match -> // #1 String.eachMatch(Pattern)
    found += match + ' '
}
assert found == 'rain Spain plain '

// Same but written another way
found = ''
(myFairStringy =~ rhyme).each { match -> // #2 Matcher.each()
    found += match + ' '
}
assert found == 'rain Spain plain '

def cloze = myFairStringy.replaceAll(rhyme){ it-'ain'+'___' } //#3 it represents the matching substring
assert cloze == 'The r___ in Sp___ stays mainly in the pl___!'
def holder = ''
myFairStringy.replaceAll(rhyme){ holder += it-'ain'+'___' }
println holder  // weird; 'holder' only gets the matched pieces w/substitutions
println myFairStringy.replaceAll(rhyme){ it-'ain'+'___' }   // whereas this is the whole sentence w/the substitutions

def matcher = 'a b c' =~ /\S/
assert matcher[0] == 'a'
assert matcher[1..2] == ['b','c']
assert matcher.size() == 3

def matcher2 = 'a:1 b:2 c:3' =~ /(\S+):(\S+)/
assert matcher2.hasGroup()
assert matcher2[0] == ['a:1', 'a', '1'] // 1st match
assert matcher2[1][2] == '2' // 2nd match, 2nd group

def matcher3 = 'a:1 b:2 c:3' =~ /(\S+):(\S+)/
matcher3.each { full, key, value ->
    assert full.size() == 3
    assert key.size() == 1 // a,b,c
    assert value.size() == 1 // 1,2,3
}

// Listing 3.9 Increase performance with pattern reuse.
def twister2 = 'she sells sea shells at the sea shore of seychelles'
// some more complicated regex:
// word that starts and ends with same letter
def regex = /\b(\w)\w*\1\b/
def matcher4 = twister2 =~ regex
println "demo " + matcher4.class.name + " object"
matcher4.each { match, group ->
    println "   match: "+ match + " / group: " + group
}

def many = 100 * 1000
start = System.nanoTime()
many.times{
    twister2 =~ regex // #1
}
timeImplicit = System.nanoTime() - start

start = System.nanoTime()
pattern = ~regex    // #2   Convert string var into regex Pattern obj
println "same demo but w/" + pattern.class.name + " object (~regex) which utilizes 'finite state machine' for speed"
pattern.matcher(twister2).each { match, group ->
    println "   match: "+ match + " / group: " + group
}
many.times{
    pattern.matcher(twister2) // #3
}
timePredef = System.nanoTime() - start
assert timeImplicit > timePredef * 2 // #4
println "timeImplicit:"+timeImplicit
println "timePredef:"+timePredef

// Listing 3.10 Patterns for classification
def fourLetters = ~/\w{4}/
assert fourLetters.isCase('work')   // argument (candidate) is a case of fourletters (classifier)
assert 'love' in fourLetters
switch('beer'){
    case ~/\w{4}/ : assert true; break
    default : assert false
}
beasts = ['bear','wolf','tiger','regex']
assert beasts.grep(fourLetters) == ['bear','wolf']

// 3.6.1 Coercion with numeric operators
def num = 2147483647   // Integer max
assert num instanceof Integer
num++
assert num == -2147483648
assert num instanceof Integer

def num2 = 2147483647   // Integer max
assert num2 instanceof Integer
num2 = num2**2  // coerces to BigInteger
assert num2 instanceof BigInteger
println num2

// 3.6.2 GDK methods for numbers
assert 1 == (-1).abs()
assert 2 == 2.5.toInteger() // conversion
assert 2 == 2.5 as Integer // enforced coercion
assert 2 == (int) 2.5 // cast
assert 3 == 2.5f.round()
assert 3.142 == Math.PI.round(3)
assert 4 == 4.5f.trunc()
assert 2.718 == Math.E.trunc(3)
assert '2.718'.isNumber() // String methods
assert 5 == '5'.toInteger()
assert 5 == '5' as Integer
assert 53 == (int) '5' // gotcha!   GOTCHA!!!!   53 is the unicode value
assert 54 == (int) '6' // gotcha!   GOTCHA!!!!   54 is the unicode value
assert '6 times' == 6 + ' times' // Number + String

// Listing 3.11 GDK methods on numbers
def store = ''
10.times{ // #1 Repetition
    store += 'x'
}
assert store == 'xxxxxxxxxx'

store = ''
1.upto(5) { number -> // #2 Walking up with loop variable
    store += number
}
assert store == '12345'

store = 0
1.upto(5) { number -> // #2 Walking up with loop variable
    store += number
}
assert store == 15

store = ''
2.downto(-2) { number -> // #3 Walking down
    store += number + ' '
}
assert store == '2 1 0 -1 -2 '

store = ''
0.step(0.5, 0.1 ){ number -> // #4 Walking with step width
    store += number + ' '
}
assert store == '0 0.1 0.2 0.3 0.4 '

