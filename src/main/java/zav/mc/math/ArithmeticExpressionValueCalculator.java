/*
 * Copyright (c) 2021 Zavarov.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package zav.mc.math;

import de.monticore.ast.ASTNode;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import zav.mc.math._visitor.MathTraverserImplementation;
import zav.mc.math.internal.calculator.FunctionValueCalculator;
import zav.mc.math.internal.calculator.MathematicalConstantValueCalculator;
import zav.mc.math.internal.calculator.MathematicalOperatorValueCalculator;
import zav.mc.math.internal.calculator.NumberValueCalculator;

/**
 * Evaluates the numerical value of an arithmetic expression.
 */
public class ArithmeticExpressionValueCalculator extends MathTraverserImplementation {
  private final Map<ASTNode, BigDecimal> values = new HashMap<>();
  
  private ArithmeticExpressionValueCalculator() {
    FunctionValueCalculator arithmeticExpressions =
          new FunctionValueCalculator(values);
    add4Math(arithmeticExpressions);
    setMathHandler(arithmeticExpressions);
    
    MathematicalOperatorValueCalculator commonExpressions =
          new MathematicalOperatorValueCalculator(values);
    add4CommonExpressions(commonExpressions);
    
    MathematicalConstantValueCalculator expressionsBasis =
          new MathematicalConstantValueCalculator(values);
    add4ExpressionsBasis(expressionsBasis);
    
    NumberValueCalculator commonLiterals =
          new NumberValueCalculator(values);
    add4MCCommonLiterals(commonLiterals);
  }
  
  public Optional<BigDecimal> getValue(ASTExpression expression) {
    return values.containsKey(expression) ? Optional.of(values.get(expression)) : Optional.empty();
  }
  
  /**
   * Evaluates the numerical value of the expression.<br>
   * Returns {@link Optional#empty()} if the expression couldn't be evaluated.
   *
   * @param expression the arithmetic expression that is going to be evaluated.
   * @return the value of the expression.
   */
  public static Optional<BigDecimal> valueOf(ASTExpression expression) {
    try {
      ArithmeticExpressionValueCalculator calculator = new ArithmeticExpressionValueCalculator();
      expression.accept(calculator);
      return calculator.getValue(expression);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}
