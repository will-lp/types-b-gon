package org.typesbgon.ast.util

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;

class TypeEraser {

	def getObjectType() { new ClassNode(Object) }
	
	def eraseReturn(MethodNode m) {
		m.returnType = objectType
	}
	
	def eraseReturn(ClassNode c) {
		c.methods.each { eraseReturn it }
	}
	
	def eraseParam(Parameter p) {
		p.type = objectType
	}
	
	def eraseParam(MethodNode m) {
		m.parameters.each { eraseParam it }
	}
	
	def eraseParam(ClassNode c) {
		c.methods.each { eraseParam it }
	}
	
	def eraseAll(ClassNode c) {
		c.methods.each { eraseAll it }
	}
	
	def eraseAll(MethodNode m) {
		eraseParam m
		eraseReturn m
	}
	
}
