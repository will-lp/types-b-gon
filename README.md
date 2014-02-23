types-b-gon
===========

A Groovy AST to erase types, resetting them to `Object`, or `def`. It can be used to provide duck-typing without losing the refactor ability from the IDE or the `@TypeChecked` benefits. `@CompileStatic` is currently unsupported. types-b-gon AST transformation happens after `@TypeChecked` phase.

types-b-gon provides a set of 3 annotations to erase types from methods. They can either erase a method return type, the method parameters type, or everything. 

### `@Erase`

`@Erase` erases the type of a parameter. It can be applied individually to a parameter:

	import org.typesbgon.Erase
	
	class A {
		def up(@Erase String s) {
			s.toUpperCase()
		}
	}
	
	assert new A().up([ toUpperCase : { 'AAA' } ]) == 'AAA'

Or to a a method, erasing all the parameter types:

	import org.typesbgon.Erase as E

	class Foo {
		@E
		def multiply(String a, Number n) {
			a * n
		}
	}

	assert new Foo().multiply(9, 9) == 81
	assert new Foo().multiply('a', 3) == 'aaa'

Or to a class, erasing every parameter type from every method.

### `@EraseReturn`

Erase the return type of a method. When applied to a class, erase the return type from all methods:

	import org.typesbgon.EraseReturn
	
	class Foo {
		def date
		@EraseReturn Date getDate() {
			date
		}
	}
	
	assert new Foo(date: 90).date == 90

### `@EraseAll`

Erase return and parameter types from a method. When applied to a class, erase return and parameter types from every method : 

	class Foo {
		@org.typesbgon.EraseAll
		String multiply(String a, BigDecimal b) {
			a * b
		}
	}

	def foo = new Foo()
	assert foo.multiply(5, 4) == 20

------

TODO stuff:

* A `skip` option, a la `@TypeChecked`
* Local AST annotations are not triggered in closure parameters
* `@CompileStatic` is unsupported
* Enable `@Erase` to fields
