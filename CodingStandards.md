 
## Indentation
Use the `Tab` button.

## Line Length
Android Studio has a vertical line, by default. Let’s use this as a guideline.

## Commenting
Whenever possible, `methods` should be documented with a multiline comment above its signature to explain its purpose (with the exception of `accessors` and `mutators`).

	/* myMethod
	 *
	 * purpose: This is just an example.
	 */	

## Naming conventions

`variables`, `methods`, `classes` and `interfaces` use __camelCase__.

	myVariable
	myMethod 
	MyClass
	MyInterface

`Constants` are all __uppercase__ with words separated with an __underscore__. 

	MY_CONSTANT

The exception being in `strings.xml`, where all characters are __lowercase__.

	the_string

** *more info about strings.xml to come*

`Booleans` are __two words__. The first one being a verb like __*is*__ or __*has*__ or something similar.

	isFound vs. found
	hasValue vs. value
## { Braces }
The __opening brace__ is always on the __same line__ as the method’s signature, the class name the conditional statement, etc.

The __closing brace__ is on its __own line__, with the exception of simple `accessors` and `mutators`.

    public String myMethod(int x) {
        String s = “I am The Count”;
        for(int i = 0; i < x; i++) {
            s += “ ha”;
        }
        return s;
    }

    public String getSomething() { return this.something; }
 
 
## Method Order
Methods should be grouped by __functionality__, rather than scope or accessibility.

## Method Signatures

Method signatures should only have a space __before__ the opening brace.

	public int add(int x, int y) {
        return x + y;
	}

If there is __overloading__, new parameters are added to the __end__ of the signature.

	public void doSomething(int x) {
	    ...
	}

	public void doSomething(int x, int y, int z) {
	    ...
	}
 

## Exception Handling/Logging
Let's aim to fail gracefully. If an error is thrown, print the stack trace.

 
## Loops and Conditional Statements

Pulled straight from the [Java Code Conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-142311.html#430):

>Braces are used around __all statements__, even single statements, when they are part of a control structure, such as an if-else or for statement. This makes it easier to add statements without accidentally introducing bugs due to forgetting to add braces.
 
A switch statement should have the following form:

    switch (condition) {
        case ABC:
            statements;
            /* falls through */
    case DEF:
        statements;
        break;
    case XYZ:
        statements;
        break;
    default:
        statements;
        break;
    }
 
We will __refrain__ from using syntax like this for simple `if-else` statements:

    return (condition ? x : y); 

 
## Package and Import Statements
Pulled straight from the [Java Code Conventions](http://www.oracle.com/technetwork/java/javase/documentation/codeconventions-141855.html#277):

First __non-comment line__ in the file should be a __package statement__
After, __import__ statements can follow.
 
    package java.awt;
 
    import java.awt.peer.CanvasPeer;



## File Structure
Android's guidelines can be found [here](https://developer.android.com/guide/topics/resources/providing-resources).



__Things to note:__

Use strings.xml `res/values/strings.xml`
 
>All text displayed in the UI (i.e. needed in a View) should be a constant in strings.xml.

Images belong in one folder `res/drawables`.

>All images in the UI should be in one folder

Icons belong in one folder `res/mipmap`
>All icons in the UI should be in one folder.




