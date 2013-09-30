class Book {
    private String title
    Book (String theTitle) {
        title = theTitle
    }
    String getTitle(){
        return title
    }
}

Book gina = new Book('Groovy in Action')

assert gina.getTitle() == 'Groovy in Action'

assert getTitleBackwards(gina) == 'noitcA ni yvoorG'

String getTitleBackwards(book) {
    String title = book.getTitle()
    return title.reverse()
}

println gina.dump()
println gina.title
println getTitleBackwards(gina)


// GROOVY BEAN
class BookBean {
    String title // #1 Property declaration

    // override implicit getter
    String getTitle(){
        return title + ' (' + title.length() + ')'
    }

}

def groovyBook = new BookBean()
groovyBook.setTitle('Groovy conquers the world') // #2 Property use with explicit method
assert groovyBook.getTitle() == 'Groovy conquers the world (25)'
println groovyBook.getTitle()

groovyBook.title = 'Groovy in Action' // #3 Property use with Groovy shortcuts
assert groovyBook.title == 'Groovy in Action (16)'
println groovyBook.title

// ANNOTATIONS
@groovy.transform.Immutable class FixedBook { // #1 AST annotation
    String title
}
def gina2 = new FixedBook('Groovy in Action') // #2 positional ctor
def regina = new FixedBook(title:'Groovy in Action') // #3 named arg ctor

assert gina2.title == 'Groovy in Action'
assert gina2 == regina // #4 standard equals()

try {
    gina2.title = "Oops!" /* #5 not allowed! */
    assert false, "should not reach here"
} catch (ReadOnlyPropertyException e) {}

// GSTRING
def nick = 'ReGina'
def book = 'Groovy in Action, 2nd ed.'
assert "$nick is $book" == 'ReGina is Groovy in Action, 2nd ed.'
println "$nick is $book"

// REGEX
assert '12345' =~ /\d+/            //  =~ find operator
assert 'xxxxx' == '12345'.replaceAll(/\d/,'x')
println '12Y45'.replaceAll(/\d/,'x')

// NUMBERS
def x = 1
def y = 2
assert x + y == 3
assert x.plus(y) == 3
assert x instanceof Integer

// LISTS
def roman = ['', 'I', 'II', 'III', 'IV', 'V', 'VI', 'VII'] // #1 List of Roman numerals
assert roman[4] == 'IV' // #2 List access
roman[8] = 'VIII' // #3 List expansion assert
assert roman.size() == 9
roman[18] = 'XVIII' // #3 List expansion assert
assert roman.size() == 19
assert roman[16] == null

// MAPS
def http = [
        100 : 'CONTINUE',
        200 : 'OK',
        400 : 'BAD REQUEST'
]
assert http[200] == 'OK'
http[500] = 'INTERNAL SERVER ERROR'
assert http.size() == 4

// RANGES
def x2 = 1..10
assert x2.contains(5)
assert x2.contains(15) == false
assert x2.size() == 10
assert x2.from == 1
assert x2.to == 10
assert x2.reverse() == 10..1
