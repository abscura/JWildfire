/*
 JWildfireC - an external C-based fractal-flame-renderer for JWildfire
 Copyright (C) 2012 Andreas Maschke

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
#ifndef JWFVAR_COLLIDEOSCOPE_H_
#define JWFVAR_COLLIDEOSCOPE_H_

#include "jwf_Constants.h"
#include "jwf_Variation.h"

class CollideoscopeFunc: public Variation {
public:
	CollideoscopeFunc() {
		a = 0.20f;
		num = 1;
		initParameterNames(2, "a", "num");
	}

	const char* getName() const {
		return "pie";
	}

	void setParameter(char *pName, float pValue) {
		if (strcmp(pName, "a") == 0) {
			a = pValue;
		}
		else if (strcmp(pName, "num") == 0) {
			num = FTOI(pValue);
		}
	}

	void transform(FlameTransformationContext *pContext, XYZPoint *pAffineTP, XYZPoint *pVarTP, float pAmount) {
    float a = atan2f(pAffineTP->y, pAffineTP->x);
    float r = pAmount * sqrtf(pAffineTP->x*pAffineTP->x + pAffineTP->y*pAffineTP->y);
    int alt;

    if (a >= 0.0) {
      alt = (int) (a * _kn_pi);
      if (alt % 2 == 0)
      {
        a = alt * _pi_kn + fmod(_ka_kn + a, _pi_kn);
      }
      else {
        a = alt * _pi_kn + fmod(-_ka_kn + a, _pi_kn);
      }
    }
    else
    {
      alt = (int) (-a * _kn_pi);
      if (alt % 2 == 1)
      {
        a = -(alt * _pi_kn + fmod(-_ka_kn - a, _pi_kn));
      }
      else
      {
        a = -(alt * _pi_kn + fmod(_ka_kn - a, _pi_kn));
      }
    }


    float s = sinf(a);
    float c = cosf(a);

    pVarTP->x += r * c;
    pVarTP->y += r * s;

		if (pContext->isPreserveZCoordinate) {
			pVarTP->z += pAmount * pAffineTP->z;
		}
	}

	CollideoscopeFunc* makeCopy() {
		return new CollideoscopeFunc(*this);
	}

	void init(FlameTransformationContext *pContext, float pAmount) {
    _kn_pi = (float) num * M_1_PI;
    _pi_kn = M_PI / (float) num;
    _ka = M_PI * a;
    _ka_kn = _ka / (float) num;
  }

private:
	float a;
	int num;

  float _kn_pi, _pi_kn, _ka, _ka_kn;
};

#endif // JWFVAR_COLLIDEOSCOPE_H_