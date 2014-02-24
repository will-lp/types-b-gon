package org.typesbgon.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.typesbgon.ast.util.ErrorHandler;
import org.typesbgon.ast.util.TypeEraser;

/**
 * AST to erase return types from methods
 *
 * @author will_lp
 *
 */
@GroovyASTTransformation
class EraseReturnAST implements ASTTransformation {

	@Override
	void visit(ASTNode[] nodes, SourceUnit source) {
		
		nodes[1].case {
			when({ it.getClass() in [MethodNode, ClassNode] }) {
				new TypeEraser().eraseReturn it
			}
			otherwise ErrorHandler.handleError(
				"@EraseReturn was not found in MethodNode or ClassNode",
				source)
		}
		
	}

}
