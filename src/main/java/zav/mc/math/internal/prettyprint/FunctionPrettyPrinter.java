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

package zav.mc.math.internal.prettyprint;


import de.monticore.prettyprint.IndentPrinter;
import zav.mc.math._ast.ASTMethodExpression;
import zav.mc.math._ast.ASTPowExpression;
import zav.mc.math._visitor.MathInheritanceHandler;
import zav.mc.math._visitor.MathTraverser;
import zav.mc.math._visitor.MathVisitor2;

/**
 * Pretty printer for mathematical functions (e.g. sin, cos, sqrt).
 */
public class FunctionPrettyPrinter extends MathInheritanceHandler implements MathVisitor2 {
  private MathTraverser traverser;
  private IndentPrinter printer;
  
  public FunctionPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }
  
  @Override
  public void setTraverser(MathTraverser traverser) {
    this.traverser = traverser;
  }
  
  @Override
  public MathTraverser getTraverser() {
    return traverser;
  }
  
  @Override
  public void handle(ASTPowExpression node) {
    node.getLeft().accept(getTraverser());
    printer.print(" ^ ");
    node.getRight().accept(getTraverser());
  }
  
  @Override
  public void visit(ASTMethodExpression node) {
    printer.print(node.getName());
  }
}
