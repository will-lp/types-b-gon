package org.typesbgon.test

import org.junit.Test

class EraseClosureTest extends GroovyTestCase {

	@Test
	void testClosure() {
		
		/*
		 * FIXME: AST doesn't trigger for closure parameters???
		 * 
		 * http://www.jroller.com/aalmiray/entry/annotations_on_closure_parameters
		 */
		assertScript '''
			
			import org.typesbgon.Erase as E
			
			list = ["a", [toUpperCase: { "AA" }]]
			
			def result = list.collect { 
					@groovy.transform.ASTTest({ println "AAAAAAAAAH! $node.declaringClass" })
					@E 
					String it ->
				it.toUpperCase()
			}

			assert result == ['A', 'AA']
		'''
	}
	
	void testAnonymousClass() {
		
		assertScript '''
			list = [90.0, [intValue: { 10 }]]
			
			result = list.collect new Closure(this) {
				def doCall(@org.typesbgon.Erase BigDecimal it) {
					it.intValue()
				}
			}

			assert result == [90, 10]
		'''
		
	}
	
}
