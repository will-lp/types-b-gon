package org.typesbgon

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;


/**
 * Erases all types from both return and parameters
 * 
 * Can be applied to:
 * - Method (erases both parameter types and the return type)
 * - Class (erases parameter types and the return type from every
 * method in the class)
 * 
 * @author will_lp
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@GroovyASTTransformationClass(['org.typesbgon.ast.EraseAllAST'])
@interface EraseAll {

}
