package org.typesbgon

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

import org.codehaus.groovy.transform.GroovyASTTransformationClass


/**
 * Erases the return type of a method.
 * 
 * Can be applied to:
 * - Method (erases the return type of the method)
 * - Class (erases the return type from every method in the class)
 * 
 * @author will_lp
 *
 */
@Target([ElementType.TYPE, ElementType.METHOD])
@Retention(RetentionPolicy.SOURCE)
@GroovyASTTransformationClass(['org.typesbgon.ast.EraseReturnAST'])
@interface EraseReturn {

}
