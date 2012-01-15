/*
  JWildfire - an image and animation processor written in Java 
  Copyright (C) 1995-2011 Andreas Maschke

  This is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser 
  General Public License as published by the Free Software Foundation; either version 2.1 of the 
  License, or (at your option) any later version.
 
  This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
  even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
  Lesser General Public License for more details.

  You should have received a copy of the GNU Lesser General Public License along with this software; 
  if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jwildfire.create.tina.variation;

import org.jwildfire.base.Tools;
import org.jwildfire.create.tina.base.XForm;
import org.jwildfire.create.tina.base.XYZPoint;

public class CannabisCurveWFFunc extends VariationFunc {
  private static final String PARAM_FILLED = "filled";
  private static final String[] paramNames = { PARAM_FILLED };

  private int filled = 1;

  @Override
  public void transform(FlameTransformationContext pContext, XForm pXForm, XYZPoint pAffineTP, XYZPoint pVarTP, double pAmount) {
    double a = pAffineTP.getPrecalcAtan(pContext);
    double r = pAffineTP.getPrecalcSqrt(pContext);

    // cannabis curve (http://mathworld.wolfram.com/CannabisCurve.html)
    r = (1 + 9.0 / 10.0 * pContext.cos(8.0 * a)) * (1 + 1.0 / 10.0 * pContext.cos(24.0 * a)) * (9.0 / 10.0 + 1.0 / 10.0 * pContext.cos(200.0 * a)) * (1.0 + pContext.sin(a));
    a += Math.PI / 2.0;

    if (filled == 1) {
      r *= pContext.random();
    }

    double nx = pContext.sin(a) * r;
    double ny = pContext.cos(a) * r;

    pVarTP.x += pAmount * nx;
    pVarTP.y += pAmount * ny;
  }

  @Override
  public String[] getParameterNames() {
    return paramNames;
  }

  @Override
  public Object[] getParameterValues() {
    return new Object[] { filled };
  }

  @Override
  public void setParameter(String pName, double pValue) {
    if (PARAM_FILLED.equalsIgnoreCase(pName))
      filled = Tools.FTOI(pValue);
    else
      throw new IllegalArgumentException(pName);
  }

  @Override
  public String getName() {
    return "cannabiscurve_wf";
  }
}