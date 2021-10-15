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

import de.monticore.expressions.expressionsbasis._ast.ASTExpressionsBasisNode;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.literals.prettyprint.MCCommonLiteralsPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import zav.mc.math._visitor.MathTraverser;
import zav.mc.math.internal.prettyprint.FunctionPrettyPrinter;

/**
 * Transforms an arithmetic expression into a human-readable string.
 */
public class ArithmeticExpressionsPrettyPrinter {
  private final IndentPrinter printer;
  private MathTraverser traverser;
  
  /**
   * The pretty printer requires a printer to write the string into. May either be
   * {@code new IndentPrinter()} or an instance already used by another pretty-printer.
   *
   * @param printer Printer instance used for pretty-printing the expression.
   */
  public ArithmeticExpressionsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
    this.traverser = MathMill.traverser();

    FunctionPrettyPrinter arithmeticExpressions =
          new FunctionPrettyPrinter(printer);
    this.traverser.add4Math(arithmeticExpressions);
    this.traverser.setMathHandler(arithmeticExpressions);
    
    CommonExpressionsPrettyPrinter commonExpressions =
          new CommonExpressionsPrettyPrinter(printer);
    this.traverser.add4CommonExpressions(commonExpressions);
    this.traverser.setCommonExpressionsHandler(commonExpressions);
    
    ExpressionsBasisPrettyPrinter expressionsBasis =
          new ExpressionsBasisPrettyPrinter(printer);
    this.traverser.add4ExpressionsBasis(expressionsBasis);
    this.traverser.setExpressionsBasisHandler(expressionsBasis);
    
    MCCommonLiteralsPrettyPrinter commonLiterals =
          new MCCommonLiteralsPrettyPrinter(printer);
    this.traverser.add4MCCommonLiterals(commonLiterals);
    this.traverser.setMCCommonLiteralsHandler(commonLiterals);
  }
  
  public MathTraverser getTraverser() {
    return traverser;
  }
  
  public void setTraverser(MathTraverser traverser) {
    this.traverser = traverser;
  }
  
  /**
   * Transforms the expression into a human-readable string.
   *
   * @param node An arithmetic expression.
   * @return A string representation of the expression.
   */
  public String prettyprint(ASTExpressionsBasisNode node) {
    node.accept(getTraverser());
    String content = printer.getContent();
    printer.clearBuffer();
    return content;
  }
}
