package org.typesbgon

import java.lang.annotation.*

import org.codehaus.groovy.transform.GroovyASTTransformationClass


/**
 * Erases parameter types.
 * 
 * The transform of this annotation happens after TypeChecked events.
 * 
 * Can be applied to:
 * - Parameters (erase the parameter type)
 * - Methods (erase all the parameters types)
 * - Classes (erase all parameters from every method)
 * 
 * @author will_lp
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE])
@GroovyASTTransformationClass(['org.typesbgon.ast.EraseAST'])
@interface Erase {
}
