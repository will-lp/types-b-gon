package org.typesbgon.ast.util

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.LocatedMessage
import org.codehaus.groovy.syntax.Token

class ErrorHandler {
	
	static handleError(String message, SourceUnit source) {
		{ ASTNode node ->
			def token = Token.newString(message, node.lineNumber, node.columnNumber)
			new LocatedMessage(message, token, source)
			source.errorCollector.addError( message, token, source )
		}
	}
	
}
