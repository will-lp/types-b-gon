package org.typesbgon.test;

import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.junit.Test

class EraseAllTest extends GroovyTestCase {

	@Test
	void testMethod() {
		assertScript '''
			class Foo {
				@org.typesbgon.EraseAll
				String multiply(String a, BigDecimal b) {
					a * b
				}
			}

			def foo = new Foo()
			assert foo.multiply(5, 4) == 20
		'''
	}
	
	
	void testWrongLocation() {
		try {
			assertScript '''
				class Foo {
					def echo(@org.typesbgon.EraseAll Integer a) {
	
					}
				}
			'''
			assert false
		} catch (e) {
			assert e in MultipleCompilationErrorsException
			assert e.message.contains('@EraseAll was not found in either MethodNode or ClassNode')
		}
	}
	
	
	void testReturn() {
		assertScript '''
			@groovy.transform.TypeChecked
			class Bar {
				def a
				Bar(Integer a) {
					this.a = a * 5
				}
			}

			class Foo {
				@org.typesbgon.EraseAll
				Date echo(Boolean b) {
					new Bar(b)
				}
			}

			def bar = new Foo().echo(10)
			assert bar in Bar
			assert bar.a == 50
		'''
	}
	
	void testCompiled() {
		
		/*
		 * FIXME: Surprise, @CompileStatic fails.
		 */
		assertScript '''
			import groovy.transform.CompileStatic as CS
			import org.typesbgon.EraseAll as EA

			@CS @EA
			class Foo {
				void plusNine( Number a ) {
					a + 9
				}
			}

			//assert new Foo().plusNine('a') == 'a9'
			
			def format = 'yyyy-MM-dd'
			//assert new Foo().plusNine(Date.parse(format, '2014-02-01')) == Date.parse(format, '2014-02-10')

			assert new Foo().plusNine(9) == 18
			
		'''
	}

}
