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

import static org.assertj.core.api.Assertions.assertThat;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import zav.mc.math._parser.MathParser;

/**
 * Base test class for all test.<br>
 * Provides static methods for transforming strings into expressions.
 */
public abstract class AbstractTest {
  /**
   * Creates the abstract syntax tree of an arithmetic expression.
   *
   * @param expression A string representation of an arithmetic expression.
   * @return The syntax tree corresponding to the arithmetic expression.
   */
  public static ASTExpression parse(String expression) {
    try {
      MathParser parser = new MathParser();

      Optional<ASTExpression> optional = parser.parse_String(expression);
      if (parser.hasErrors()) {
        throw new IllegalArgumentException();
      }
      //fail("The parser encountered errors while parsing "+expression);
      if (optional.isEmpty()) {
        throw new IllegalArgumentException();
      }
      //fail("The expression couldn't be parsed");

      return optional.get();
    } catch (IOException e) {
      //fail(e.getMessage());
      //return null;
      throw new IllegalArgumentException();
    }
  }

  public static Optional<BigDecimal> valueOfOpt(String expression) {
    return ArithmeticExpressionsValueCalculator.valueOf(expression);
  }
  
  /**
   * Calculates the numerical value of an expression.
   *
   * @param expression A string representation of an arithmetic expression.
   * @return The numerical value of the provided expressions.
   */
  public static BigDecimal valueOf(String expression) {
    Optional<BigDecimal> valueOpt = valueOfOpt(expression);
    assertThat(valueOpt).isPresent();
    return valueOpt.get();
  }
}
