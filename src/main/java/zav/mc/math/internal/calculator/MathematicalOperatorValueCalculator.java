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

package zav.mc.math.internal.calculator;

import static com.google.common.base.Preconditions.checkArgument;

import de.monticore.ast.ASTNode;
import de.monticore.expressions.commonexpressions._ast.ASTBracketExpression;
import de.monticore.expressions.commonexpressions._ast.ASTDivideExpression;
import de.monticore.expressions.commonexpressions._ast.ASTMinusExpression;
import de.monticore.expressions.commonexpressions._ast.ASTModuloExpression;
import de.monticore.expressions.commonexpressions._ast.ASTMultExpression;
import de.monticore.expressions.commonexpressions._ast.ASTPlusExpression;
import de.monticore.expressions.commonexpressions._visitor.CommonExpressionsVisitor2;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * Evaluates the result after applying a mathematical operator such as division or multiplication.
 */
public class MathematicalOperatorValueCalculator implements CommonExpressionsVisitor2 {
  private final Map<ASTNode, BigDecimal> values;

  public MathematicalOperatorValueCalculator(Map<ASTNode, BigDecimal> values) {
    this.values = values;
  }

  @Override
  public void endVisit(ASTBracketExpression node) {
    checkArgument(values.containsKey(node.getExpression()));

    values.put(node, values.get(node.getExpression()));
  }

  @Override
  public void endVisit(ASTMultExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.multiply(right));
  }

  @Override
  public void endVisit(ASTDivideExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.divide(right, 15, RoundingMode.HALF_EVEN));
  }

  @Override
  public void endVisit(ASTModuloExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.remainder(right));
  }

  @Override
  public void endVisit(ASTPlusExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.add(right));
  }

  @Override
  public void endVisit(ASTMinusExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.subtract(right));
  }
}