package org.typesbgon.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.typesbgon.ast.util.ErrorHandler;
import org.typesbgon.ast.util.TypeEraser;

/**
 * AST to erase parameter types from methods
 *
 * @author will_lp
 *
 */
@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
class EraseAST implements ASTTransformation {

	@Override
	void visit(ASTNode[] nodes, SourceUnit source) {
		
		def types = nodes[1].case {
			when({ it.getClass() in [Parameter, MethodNode, ClassNode] }) {
				new TypeEraser().eraseParam it
			}
			
			otherwise ErrorHandler.handleError(
				"@Erase was not found in Parameter, MethodNode or ClassNode",
				source)
		}
		
	}
	
	
	
}
