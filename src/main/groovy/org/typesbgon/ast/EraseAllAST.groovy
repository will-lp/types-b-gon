package org.typesbgon.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.messages.LocatedMessage
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.typesbgon.ast.util.ErrorHandler;
import org.typesbgon.ast.util.TypeEraser;

/**
 * AST to erase types from methods and parameters
 * 
 * @author will_lp
 *
 */
@GroovyASTTransformation
class EraseAllAST implements ASTTransformation {

	@Override
	void visit(ASTNode[] nodes, SourceUnit source) {
		
		nodes[1].case {
			when({ it.getClass() in [MethodNode, ClassNode] }) {
				new TypeEraser().eraseAll it
			}
			
			otherwise ErrorHandler.handleError(
				"@EraseAll was not found in either MethodNode or ClassNode",
				source)
		}
		
	}

}
