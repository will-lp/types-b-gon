package org.typesbgon

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;


/**
 * Erases return and parameters types from methods.
 * 
 * Can be applied to:
 * - Method (erases both parameter and return types)
 * - Class (erases parameter and return types from every method 
 * in the class)
 * 
 * @author will_lp
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@GroovyASTTransformationClass(['org.typesbgon.ast.EraseAllAST'])
@interface EraseAll {

}
