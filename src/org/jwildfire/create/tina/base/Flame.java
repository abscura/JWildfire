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
package org.jwildfire.create.tina.base;

import java.util.ArrayList;
import java.util.List;

import org.jwildfire.base.QualityProfile;
import org.jwildfire.base.ResolutionProfile;
import org.jwildfire.create.tina.edit.Assignable;
import org.jwildfire.create.tina.edit.PropertyChangeListener;
import org.jwildfire.create.tina.palette.RGBPalette;
import org.jwildfire.create.tina.variation.FlameTransformationContext;
import org.jwildfire.create.tina.variation.Variation;

public class Flame implements Assignable<Flame> {
  private double centreX;
  private double centreY;
  private int width;
  private int height;
  private double camPitch;
  private double camYaw;
  private double camPerspective;
  private double camRoll;
  private double camZoom;
  private double camZ;
  private double camDOF;
  private int spatialOversample;
  private int colorOversample;
  private double spatialFilterRadius;
  private double sampleDensity;
  private int bgColorRed;
  private int bgColorGreen;
  private int bgColorBlue;
  private double gamma;
  private double gammaThreshold;
  private double pixelsPerUnit;
  private int whiteLevel;
  private double brightness;
  private double contrast;
  private double vibrancy;
  private boolean preserveZ;
  private String resolutionProfile;
  private String qualityProfile;

  private RGBPalette palette = new RGBPalette();
  private final List<XForm> xForms = new ArrayList<XForm>();
  private XForm finalXForm = null;
  private ShadingInfo shadingInfo = new ShadingInfo(this);

  private final List<PropertyChangeListener<Flame>> changeListeners = new ArrayList<PropertyChangeListener<Flame>>();

  public Flame() {
    spatialFilterRadius = 0.0;
    sampleDensity = 100.0;
    bgColorRed = bgColorGreen = bgColorBlue = 0;
    brightness = 4;
    contrast = 1;
    vibrancy = 1;
    gamma = 4;
    centreX = 0.0;
    centreY = 0.0;
    camRoll = 0.0;
    camPitch = 0.0;
    camYaw = 0.0;
    camPerspective = 0.0;
    camZoom = 1.0;
    camZ = 0.0;
    camDOF = 0.0;
    spatialOversample = 1;
    colorOversample = 1;
    gammaThreshold = 0.04;
    pixelsPerUnit = 50;
    whiteLevel = 200;
    shadingInfo.init();
  }

  public double getCentreX() {
    return centreX;
  }

  public void setCentreX(double centreX) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "centreX", this.centreX, centreX);
    }
    this.centreX = centreX;
  }

  public double getCentreY() {
    return centreY;
  }

  public void setCentreY(double centreY) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "centreY", this.centreY, centreY);
    }
    this.centreY = centreY;
  }

  public double getCamRoll() {
    return camRoll;
  }

  public void setCamRoll(double pCamRoll) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camRoll", this.camRoll, camRoll);
    }
    this.camRoll = pCamRoll;
  }

  public double getBrightness() {
    return brightness;
  }

  public void setBrightness(double brightness) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "brightness", this.brightness, brightness);
    }
    this.brightness = brightness;
  }

  public double getGamma() {
    return gamma;
  }

  public void setGamma(double gamma) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "gamma", this.gamma, gamma);
    }
    this.gamma = gamma;
  }

  public double getGammaThreshold() {
    return gammaThreshold;
  }

  public void setGammaThreshold(double gammaThreshold) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "gammaThreshold", this.gammaThreshold, gammaThreshold);
    }
    this.gammaThreshold = gammaThreshold;
  }

  public int getSpatialOversample() {
    return spatialOversample;
  }

  public void setSpatialOversample(int spatialOversample) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "spatialOversample", this.spatialOversample, spatialOversample);
    }
    this.spatialOversample = spatialOversample;
  }

  public double getSpatialFilterRadius() {
    return spatialFilterRadius;
  }

  public void setSpatialFilterRadius(double spatialFilterRadius) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "spatialFilterRadius", this.spatialFilterRadius, spatialFilterRadius);
    }
    this.spatialFilterRadius = spatialFilterRadius;
  }

  public double getSampleDensity() {
    return sampleDensity;
  }

  public void setSampleDensity(double sampleDensity) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "sampleDensity", this.sampleDensity, sampleDensity);
    }
    this.sampleDensity = sampleDensity;
  }

  public List<XForm> getXForms() {
    return xForms;
  }

  public double getPixelsPerUnit() {
    return pixelsPerUnit;
  }

  public void setPixelsPerUnit(double pixelsPerUnit) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "pixelsPerUnit", this.pixelsPerUnit, pixelsPerUnit);
    }
    this.pixelsPerUnit = pixelsPerUnit;
  }

  public int getWhiteLevel() {
    return whiteLevel;
  }

  public void setWhiteLevel(int whiteLevel) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "whiteLevel", this.whiteLevel, whiteLevel);
    }
    this.whiteLevel = whiteLevel;
  }

  public double getContrast() {
    return contrast;
  }

  public void setContrast(double contrast) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "contrast", this.contrast, contrast);
    }
    this.contrast = contrast;
  }

  public double getVibrancy() {
    return vibrancy;
  }

  public void setVibrancy(double vibrancy) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "vibrancy", this.vibrancy, vibrancy);
    }
    this.vibrancy = vibrancy;
  }

  public RGBPalette getPalette() {
    return palette;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "width", this.width, width);
    }
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "height", this.height, height);
    }
    this.height = height;
  }

  public XForm getFinalXForm() {
    return finalXForm;
  }

  public void setFinalXForm(XForm pFinalXForm) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "finalXForm", this.finalXForm, finalXForm);
    }
    finalXForm = pFinalXForm;
  }

  public double getCamPitch() {
    return camPitch;
  }

  public void setCamPitch(double camPitch) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camPitch", this.camPitch, camPitch);
    }
    this.camPitch = camPitch;
  }

  public double getCamYaw() {
    return camYaw;
  }

  public void setCamYaw(double camYaw) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camYaw", this.camYaw, camYaw);
    }
    this.camYaw = camYaw;
  }

  public double getCamPerspective() {
    return camPerspective;
  }

  public void setCamPerspective(double camPerspective) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camPerspective", this.camPerspective, camPerspective);
    }
    this.camPerspective = camPerspective;
  }

  public double getCamZoom() {
    return camZoom;
  }

  public void setCamZoom(double camZoom) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camZoom", this.camZoom, camZoom);
    }
    this.camZoom = camZoom;
  }

  public int getBGColorRed() {
    return bgColorRed;
  }

  public void setBGColorRed(int bgColorRed) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "bgColorRed", this.bgColorRed, bgColorRed);
    }
    this.bgColorRed = bgColorRed;
  }

  public int getBGColorGreen() {
    return bgColorGreen;
  }

  public void setBGColorGreen(int bgColorGreen) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "bgColorGreen", this.bgColorGreen, bgColorGreen);
    }
    this.bgColorGreen = bgColorGreen;
  }

  public int getBGColorBlue() {
    return bgColorBlue;
  }

  public void setBGColorBlue(int bgColorBlue) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "bgColorBlue", this.bgColorBlue, bgColorBlue);
    }
    this.bgColorBlue = bgColorBlue;
  }

  public void setPalette(RGBPalette pPalette) {
    if (pPalette == null || pPalette.getSize() != RGBPalette.PALETTE_SIZE)
      throw new IllegalArgumentException(pPalette.toString());
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "palette", this.palette, pPalette);
    }
    palette = pPalette;
    palette.setOwner(this);
  }

  @Override
  public Flame makeCopy() {
    Flame res = new Flame();
    res.assign(this);
    return res;
  }

  @Override
  public void assign(Flame pFlame) {
    centreX = pFlame.centreX;
    centreY = pFlame.centreY;
    width = pFlame.width;
    height = pFlame.height;
    camPitch = pFlame.camPitch;
    camYaw = pFlame.camYaw;
    camPerspective = pFlame.camPerspective;
    camRoll = pFlame.camRoll;
    camZoom = pFlame.camZoom;
    camZ = pFlame.camZ;
    camDOF = pFlame.camDOF;
    spatialOversample = pFlame.spatialOversample;
    colorOversample = pFlame.colorOversample;
    spatialFilterRadius = pFlame.spatialFilterRadius;
    sampleDensity = pFlame.sampleDensity;
    bgColorRed = pFlame.bgColorRed;
    bgColorGreen = pFlame.bgColorGreen;
    bgColorBlue = pFlame.bgColorBlue;
    gamma = pFlame.gamma;
    gammaThreshold = pFlame.gammaThreshold;
    pixelsPerUnit = pFlame.pixelsPerUnit;
    whiteLevel = pFlame.whiteLevel;
    brightness = pFlame.brightness;
    contrast = pFlame.contrast;
    vibrancy = pFlame.vibrancy;
    preserveZ = pFlame.preserveZ;
    resolutionProfile = pFlame.resolutionProfile;
    qualityProfile = pFlame.qualityProfile;
    shadingInfo.assign(pFlame.shadingInfo);
    palette = pFlame.palette.makeCopy();
    xForms.clear();
    for (XForm xForm : pFlame.getXForms()) {
      xForms.add(xForm.makeCopy());
    }
    if (pFlame.finalXForm != null) {
      finalXForm = pFlame.finalXForm.makeCopy();
    }
    changeListeners.clear();
    for (PropertyChangeListener<Flame> listener : pFlame.getChangeListeners()) {
      changeListeners.add(listener);
    }
  }

  public int getColorOversample() {
    return colorOversample;
  }

  public void setColorOversample(int colorOversample) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "colorOversample", this.colorOversample, colorOversample);
    }
    this.colorOversample = colorOversample;
  }

  public double getCamZ() {
    return camZ;
  }

  public void setCamZ(double camZ) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camZ", this.camZ, camZ);
    }
    this.camZ = camZ;
  }

  public double getCamDOF() {
    return camDOF;
  }

  public void setCamDOF(double camDOF) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "camDOF", this.camDOF, camDOF);
    }
    this.camDOF = camDOF;
  }

  public ShadingInfo getShadingInfo() {
    return shadingInfo;
  }

  public void setShadingInfo(ShadingInfo shadingInfo) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "shadingInfo", this.shadingInfo, shadingInfo);
    }
    this.shadingInfo = shadingInfo;
  }

  public void distributeColors() {
    RGBPalette oldPalette = changeListeners.size() > 0 ? getPalette().makeCopy() : null;
    Boolean listenerState[] = disableChangeListeners();
    try {
      int cnt = getXForms().size();
      if (cnt > 1) {
        for (int i = 0; i < getXForms().size(); i++) {
          XForm xForm = getXForms().get(i);
          xForm.setColor((double) i / (double) (cnt - 1));
        }
      }
    }
    finally {
      enableChangeListeners(listenerState);
    }
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "palette", oldPalette, this.getPalette());
    }
  }

  public void randomizeColors() {
    RGBPalette oldPalette = changeListeners.size() > 0 ? getPalette().makeCopy() : null;
    Boolean listenerState[] = disableChangeListeners();
    try {
      for (int i = 0; i < getXForms().size(); i++) {
        XForm xForm = getXForms().get(i);
        xForm.setColor(Math.random());
      }
    }
    finally {
      enableChangeListeners(listenerState);
    }
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "palette", oldPalette, this.getPalette());
    }
  }

  public void refreshModWeightTables(FlameTransformationContext pFlameTransformationContext) {
    double tp[] = new double[Constants.MAX_MOD_WEIGHT_COUNT];
    int n = getXForms().size();

    for (XForm xForm : this.getXForms()) {
      xForm.initTransform();
      for (Variation var : xForm.getSortedVariations()) {
        var.getFunc().init(pFlameTransformationContext, xForm, var.getAmount());
      }
    }
    if (getFinalXForm() != null) {
      XForm xForm = getFinalXForm();
      xForm.initTransform();
      for (Variation var : xForm.getSortedVariations()) {
        var.getFunc().init(pFlameTransformationContext, xForm, var.getAmount());
      }
    }
    //
    for (int k = 0; k < n; k++) {
      double totValue = 0;
      XForm xform = getXForms().get(k);
      for (int l = 0; l < xform.getNextAppliedXFormTable().length; l++) {
        xform.getNextAppliedXFormTable()[l] = new XForm();
      }
      for (int i = 0; i < n; i++) {
        tp[i] = getXForms().get(i).getWeight() * getXForms().get(k).getModifiedWeights()[i];
        totValue = totValue + tp[i];
      }

      if (totValue > 0) {
        double loopValue = 0;
        for (int i = 0; i < xform.getNextAppliedXFormTable().length; i++) {
          double totalProb = 0;
          int j = -1;
          do {
            j++;
            totalProb = totalProb + tp[j];
          }
          while (!((totalProb > loopValue) || (j == n - 1)));
          xform.getNextAppliedXFormTable()[i] = getXForms().get(j);
          loopValue = loopValue + totValue / (double) xform.getNextAppliedXFormTable().length;
        }
      }
      else {
        for (int i = 0; i < xform.getNextAppliedXFormTable().length - 1; i++) {
          xform.getNextAppliedXFormTable()[i] = null;
        }
      }
    }
  }

  public boolean isPreserveZ() {
    return preserveZ;
  }

  public void setPreserveZ(boolean preserveZ) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "preserveZ", this.preserveZ, preserveZ);
    }
    this.preserveZ = preserveZ;
  }

  public String getResolutionProfile() {
    return resolutionProfile;
  }

  public void setResolutionProfile(ResolutionProfile pResolutionProfile) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "resolutionProfile", this.resolutionProfile, pResolutionProfile.toString());
    }
    resolutionProfile = pResolutionProfile.toString();
  }

  public void setResolutionProfile(String pResolutionProfile) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "resolutionProfile", this.resolutionProfile, pResolutionProfile);
    }
    resolutionProfile = pResolutionProfile;
  }

  public String getQualityProfile() {
    return qualityProfile;
  }

  public void setQualityProfile(QualityProfile pQualityProfile) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "qualityProfile", this.qualityProfile, pQualityProfile.toString());
    }
    qualityProfile = pQualityProfile.toString();
  }

  public void setQualityProfile(String pQualityProfile) {
    for (PropertyChangeListener<Flame> listener : changeListeners) {
      listener.propertyChanged(this, "qualityProfile", this.qualityProfile, pQualityProfile);
    }
    qualityProfile = pQualityProfile;
  }

  public void addPropertyChangeListener(PropertyChangeListener<Flame> pListener) {
    if (pListener != null && changeListeners.indexOf(pListener) < 0) {
      changeListeners.add(pListener);
    }
  }

  public void clearPropertyChangeListeners() {
    changeListeners.clear();
  }

  public List<PropertyChangeListener<Flame>> getChangeListeners() {
    return changeListeners;
  }

  public Boolean[] disableChangeListeners() {
    Boolean state[] = new Boolean[changeListeners.size()];
    for (int i = 0; i < changeListeners.size(); i++) {
      PropertyChangeListener<Flame> listener = changeListeners.get(i);
      state[i] = listener.isEnabled();
      listener.setEnabled(false);
    }
    return state;
  }

  public void enableChangeListeners(Boolean state[]) {
    if (state == null || state.length != changeListeners.size()) {
      throw new IllegalStateException();
    }
    for (int i = 0; i < changeListeners.size(); i++) {
      PropertyChangeListener<Flame> listener = changeListeners.get(i);
      listener.setEnabled(state[i]);
    }
  }

}
