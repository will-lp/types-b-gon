package org.typesbgon.test

import static org.junit.Assert.*;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.junit.Test;

class EraseTest extends GroovyTestCase {

	void testEraseSingleArg() {
		
		assertScript '''
			import org.typesbgon.Erase
			
			class Bar {
				String name
			}
			
			class Foo {
				def bar(@Erase Bar bar) {
					bar.name.toUpperCase()
				}
			}

			def foo = new Foo()
			assert foo.bar([name: 'john doe']) == 'JOHN DOE'
			assert foo.bar(new Bar(name: 'geddy lee')) == 'GEDDY LEE'
		'''
		
	}
	
	
	
	void testReturnTyped() {
		try {
			assertScript '''
				import org.typesbgon.Erase
				import org.typesbgon.EraseReturn
				
				@groovy.transform.TypeChecked
				class Foo {
					def bar(@Erase String gee) {
						gee.toUpperCase()
					}
					
					def baz() {
						bar([toUpperCase: {'bazinga'}])
					}
				}
			'''
			
		} catch (e) {
			assert e in MultipleCompilationErrorsException
			assert e.message.contains('Cannot find matching method Foo#bar(java.util.LinkedHashMap')
		}
	}
	
	void testEraseOnClass() {
		assertScript '''
			@org.typesbgon.Erase
			class Foo {
				def baz(String a, Number b) {
					
				}
			}

			def baz = Foo.metaClass.methods.find { it.name == 'baz' }
			def types = baz.cachedMethod.parameterTypes
			assert types[0] == Object
			assert types[1] == Object
		'''
	}
	
	
	void testOnlyOneErase() {
		assertScript '''
			import org.typesbgon.Erase

			class Foo {
				def mult(String s1, @Erase String s2) {
					s1 * s2
				}

				def mult(String s1, String s2) {
					s1 * s2
				}
			}
			
			assert new Foo().mult("a", 3) == "aaa"
			
			try { 
				new Foo().mult("a", "a") 
			} catch (e) {
				assert e in MissingMethodException
			}
		'''
	}
	
	void testInheritance() {
		assertScript '''
			class Mammal {
				def move(@org.typesbgon.Erase Long distance) {}
			}

			class Cheetah extends Mammal {
				
			}

			def cheetah = new Cheetah()
			def move = cheetah.metaClass.methods.find { it.name == 'move' }
			assert move.cachedMethod.parameterTypes[0] == Object
		'''
	}
	
	void testRepeatedSignature() {
		try {
			assertScript '''
				import org.typesbgon.Erase as E
				
				class Foo {
					def foo(@E String s) {}
					def foo(@E Date d) {}
				}
			'''
			assert false, "should have failed"
		} catch (e) {
			assert e in MultipleCompilationErrorsException
			assert e.message.contains('duplicates another method of the same signature')
		}
	}
	
	void testInnerClass() {
		assertScript '''
			class Foo {
				static class FooChild {
					def cube(@org.typesbgon.Erase BigDecimal n) { n ** 3 }
				}
			}
			
			assert new Foo.FooChild().cube(3) == 27
			assert new Foo.FooChild().cube(3.0f) == 27
		'''
	}
	
	void testEraseInterface() {
		shouldFail {
			assertScript '''
				interface Vehicle {
					def move(BigDecimal distance)
				}
	
				class Car implements Vehicle {
					@Override
					def move(@org.typesbgon.Erase BigDecimal distance) { }
				}
			'''
		}
	}
	
}
