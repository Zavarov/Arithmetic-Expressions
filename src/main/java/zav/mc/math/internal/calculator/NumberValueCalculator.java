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

import de.monticore.ast.ASTNode;
import de.monticore.literals.mccommonliterals._ast.ASTSignedBasicDoubleLiteral;
import de.monticore.literals.mccommonliterals._ast.ASTSignedBasicFloatLiteral;
import de.monticore.literals.mccommonliterals._ast.ASTSignedBasicLongLiteral;
import de.monticore.literals.mccommonliterals._ast.ASTSignedNatLiteral;
import de.monticore.literals.mccommonliterals._visitor.MCCommonLiteralsVisitor2;
import java.math.BigDecimal;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the value of a number.
 */
public class NumberValueCalculator implements MCCommonLiteralsVisitor2 {
  private final Map<ASTNode, BigDecimal> values;

  public NumberValueCalculator(Map<ASTNode, BigDecimal> values) {
    this.values = values;
  }

  @Override
  public void visit(ASTSignedNatLiteral node) {
    values.put(node, new BigDecimal(node.getSource()));
  }

  @Override
  public void visit(ASTSignedBasicLongLiteral node) {
    values.put(node, new BigDecimal(StringUtils.chop(node.getSource())));
  }

  @Override
  public void visit(ASTSignedBasicFloatLiteral node) {
    values.put(node, new BigDecimal(StringUtils.chop(node.getSource())));
  }

  @Override
  public void visit(ASTSignedBasicDoubleLiteral node) {
    values.put(node, new BigDecimal(node.getSource()));
  }
}
