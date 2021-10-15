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
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import zav.mc.math._ast.ASTACosExpression;
import zav.mc.math._ast.ASTASinExpression;
import zav.mc.math._ast.ASTATanExpression;
import zav.mc.math._ast.ASTAbsExpression;
import zav.mc.math._ast.ASTCeilExpression;
import zav.mc.math._ast.ASTCosExpression;
import zav.mc.math._ast.ASTFloorExpression;
import zav.mc.math._ast.ASTLiteralExpression;
import zav.mc.math._ast.ASTLnExpression;
import zav.mc.math._ast.ASTLogExpression;
import zav.mc.math._ast.ASTMaxExpression;
import zav.mc.math._ast.ASTMinExpression;
import zav.mc.math._ast.ASTPowExpression;
import zav.mc.math._ast.ASTRandomNumberExpression;
import zav.mc.math._ast.ASTSinExpression;
import zav.mc.math._ast.ASTSqrtExpression;
import zav.mc.math._ast.ASTTanExpression;
import zav.mc.math._visitor.MathHandler;
import zav.mc.math._visitor.MathTraverser;
import zav.mc.math._visitor.MathTraverserImplementation;
import zav.mc.math._visitor.MathVisitor2;

/**
 * Evaluates the result after applying a mathematical function such as the sinus or square root.
 */
public class FunctionValueCalculator implements MathVisitor2, MathHandler {
  /**
   * The maximum number of rolls that are allowed at once.
   */
  private static final long MAX_EYE = 1024;
  /**
   * The maximum number of eyes a die can have.
   */
  private static final long MAX_DICE = 256;
  /**
   * Contains the value of each subexpression.
   */
  private final Map<ASTNode, BigDecimal> values;
  
  private MathTraverser traverser;
  
  public FunctionValueCalculator(Map<ASTNode, BigDecimal> values) {
    this.values = values;
  }

  @Override
  public MathTraverser getTraverser() {
    return traverser;
  }

  @Override
  public void setTraverser(MathTraverser traverser) {
    this.traverser = traverser;
  }

  @Override
  public void endVisit(ASTLiteralExpression node) {
    checkArgument(values.containsKey(node.getSignedLiteral()));

    values.put(node, values.get(node.getSignedLiteral()));
  }

  @Override
  public void endVisit(ASTPowExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, BigDecimal.valueOf(Math.pow(left.doubleValue(), right.doubleValue())));
  }

  @Override
  public void endVisit(ASTRandomNumberExpression node) {
    checkArgument(values.containsKey(node.getDice()));
    checkArgument(values.containsKey(node.getEyes()));
    checkArgument(values.get(node.getDice()).intValueExact() <= MAX_DICE);
    checkArgument(values.get(node.getEyes()).intValueExact() <= MAX_EYE);
    checkArgument(values.get(node.getDice()).intValueExact() > 0);
    checkArgument(values.get(node.getEyes()).intValueExact() > 0);

    int dice = values.get(node.getDice()).intValueExact();
    int eyes = values.get(node.getEyes()).intValueExact();
    BigDecimal result = BigDecimal.ZERO;

    for (int i = 0; i < dice; ++i) {
      //+1 since rng returns values from [0,eyes)
      result = result.add(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(eyes) + 1));
    }

    values.put(node, result);
  }

  @Override
  public void endVisit(ASTAbsExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, value.abs());
  }

  @Override
  public void endVisit(ASTACosExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.acos(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTASinExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.asin(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTATanExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.atan(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTCeilExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.ceil(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTCosExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.cos(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTFloorExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.floor(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTLogExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.log10(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTLnExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.log(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTMaxExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.max(right));
  }

  @Override
  public void endVisit(ASTMinExpression node) {
    checkArgument(values.containsKey(node.getLeft()));
    checkArgument(values.containsKey(node.getRight()));

    BigDecimal left = values.get(node.getLeft());
    BigDecimal right = values.get(node.getRight());

    values.put(node, left.min(right));
  }

  @Override
  public void endVisit(ASTSinExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.sin(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTSqrtExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.sqrt(value.doubleValue())));
  }

  @Override
  public void endVisit(ASTTanExpression node) {
    checkArgument(values.containsKey(node.getArgument()));

    BigDecimal value = values.get(node.getArgument());

    values.put(node, BigDecimal.valueOf(Math.tan(value.doubleValue())));
  }
}
