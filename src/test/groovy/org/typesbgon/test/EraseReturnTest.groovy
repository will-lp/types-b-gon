package org.typesbgon.test;

import static org.junit.Assert.*;

import org.junit.Test;

class EraseReturnTest extends GroovyTestCase {

	void testUnVoidReturn() {
		assertScript '''
			class A {
				@org.typesbgon.EraseReturn
				void foo () {
					return "test for echo"
				}
			}

			assert new A().foo() == 'test for echo'
		'''
	}
	
	void testReturn() {
		
		assertScript '''
			import org.typesbgon.Erase
			import org.typesbgon.EraseReturn
			
			class Bar {
				String name
			}
			
			class Foo {
				@EraseReturn
				String bar(@Erase Bar bar) {
					bar.name.toUpperCase()
				}
			}

			def foo = new Foo()
			assert foo.bar([name: 'john doe']) == 'JOHN DOE'
			
			assert foo.metaClass.methods.find { it.name == 'bar' }.returnType == Object
		'''
		
	}
	
	void testCheckedReturnErase() {
		assertScript '''
			import groovy.transform.TypeChecked as TC
			import org.typesbgon.EraseReturn as ER
			
			@TC @ER
			class A {
				void b() {
					return "doe, john"
				}
			}

			assert new A().b() == "doe, john"
		'''
	}
	
	void testReturnChange() {
		assertScript '''	
			import org.typesbgon.EraseReturn
			
			class Foo {
				def date
				@EraseReturn Date getDate() {
					date
				}
			}
			
			assert new Foo(date: 90).date == 90
			
		'''
	}

}
