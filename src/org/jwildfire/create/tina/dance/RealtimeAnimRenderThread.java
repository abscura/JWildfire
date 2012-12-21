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
package org.jwildfire.create.tina.dance;

import org.jwildfire.create.tina.animate.AnimationService;
import org.jwildfire.create.tina.animate.GlobalScript;
import org.jwildfire.create.tina.animate.MotionSpeed;
import org.jwildfire.create.tina.animate.XFormScript;
import org.jwildfire.create.tina.audio.JLayerInterface;
import org.jwildfire.create.tina.audio.RecordedFFT;
import org.jwildfire.create.tina.base.Flame;
import org.jwildfire.create.tina.dance.transform.DanceFlameTransformer;
import org.jwildfire.create.tina.swing.FlameHolder;
import org.jwildfire.image.SimpleImage;
import org.jwildfire.swing.ImagePanel;

public class RealtimeAnimRenderThread implements Runnable, FlameHolder {
  private final DancingFractalsController controller;
  private boolean forceAbort;
  private boolean running;
  private RecordedFFT fftData;
  private JLayerInterface musicPlayer;
  private ImagePanel fftPanel;
  private int framesPerSecond = 12;
  private long timeRenderStarted = 0;
  private final DanceFlameTransformer transformer;
  private GlobalScript globalScript[] = { GlobalScript.NONE, GlobalScript.NONE, GlobalScript.NONE };
  private MotionSpeed globalSpeed[] = { MotionSpeed.S1_1, MotionSpeed.S1_1, MotionSpeed.S1_1 };
  private XFormScript xFormScript[] = { XFormScript.NONE, XFormScript.NONE, XFormScript.NONE };
  private MotionSpeed xFormSpeed[] = { MotionSpeed.S1_1, MotionSpeed.S1_1, MotionSpeed.S1_1 };
  private static final int SPEED_REF_FRAMES = 180;
  private Flame flame;
  private boolean drawTriangles = true;
  private boolean drawFFT = true;
  private boolean drawFPS = true;

  public RealtimeAnimRenderThread(DancingFractalsController pController) {
    controller = pController;
    transformer = new DanceFlameTransformer();
  }

  @Override
  public void run() {
    setRunning(forceAbort = false);
    try {
      long fpsMeasureMentFrameCount = 0;
      long startFPSMeasurement = System.currentTimeMillis();
      long nextFrame = timeRenderStarted = startFPSMeasurement;
      double fps = 0.0;
      setRunning(true);
      final int scriptMaxCount = globalScript.length;
      double globalTime[] = new double[scriptMaxCount];
      double xFormTime[] = new double[scriptMaxCount];
      int refFrames = (int) (SPEED_REF_FRAMES / (double) framesPerSecond * 25.0 + 0.5);

      while (!forceAbort) {
        long time = System.currentTimeMillis();
        while (time < nextFrame) {
          try {
            Thread.sleep(1);
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
          time = System.currentTimeMillis();
        }
        nextFrame = (long) (time + 1000.0 / (double) getFramesPerSecond() + 0.5);
        // TODO
        Flame flame = this.flame != null ? this.flame.makeCopy() : null;
        if (fftData != null) {
          short currFFT[] = fftData.getData(musicPlayer.getPosition());
          if (drawFFT && fftPanel != null) {
            SimpleImage img = fftPanel.getImage();
            drawFFT(img, currFFT);
            fftPanel.repaint();
          }
          if (flame != null) {
            for (int s = 0; s < scriptMaxCount; s++) {
              flame = AnimationService.createFlame(flame, globalScript[s], globalTime[s], xFormScript[s], xFormTime[s], controller.getParentCtrl().getPrefs());
              globalTime[s] += globalSpeed[s].calcTime(2, refFrames, false);
              if (globalTime[s] > 1.0)
                globalTime[s] = 0.0;

              xFormTime[s] += xFormSpeed[s].calcTime(2, refFrames, false);
              if (xFormTime[s] > 1.0)
                xFormTime[s] = 0.0;
            }
            transformer.transformFlame(flame, currFFT);
          }
        }
        fpsMeasureMentFrameCount++;
        long dt = (System.currentTimeMillis() - startFPSMeasurement);
        if (dt > 500) {
          fps = (double) (fpsMeasureMentFrameCount * 1000.0) / (double) dt;
          fpsMeasureMentFrameCount = 0;
          startFPSMeasurement = System.currentTimeMillis();
        }
        controller.refreshFlameImage(flame, drawTriangles, fps, drawFPS);
      }
    }
    finally {
      setRunning(false);
    }
  }

  public boolean isDone() {
    return !isRunning();
  }

  public void setForceAbort(boolean forceAbort) {
    this.forceAbort = forceAbort;
  }

  public void setFFTData(RecordedFFT pFFTData) {
    fftData = pFFTData;
  }

  public void setMusicPlayer(JLayerInterface pMusicPlayer) {
    musicPlayer = pMusicPlayer;
  }

  public void setFFTPanel(ImagePanel pFFTPanel) {
    fftPanel = pFFTPanel;
  }

  private void drawFFT(SimpleImage img, short[] buffer) {
    final double hScale = 1.75;
    final int imgWidth = img.getImageWidth();
    final int imgHeight = img.getImageHeight();
    int blockSize = imgWidth / (buffer.length + 1);
    img.fillBackground(0, 0, 0);
    for (int i = 0; i < buffer.length; i++) {
      short val = buffer[i];
      int iVal = (int) ((double) val / (double) Short.MAX_VALUE * (double) imgHeight * hScale + 0.5);
      if (iVal < 0)
        iVal = 0;
      else if (iVal >= imgHeight)
        iVal = imgHeight - 1;

      for (int y = 0; y < iVal; y++) {
        img.setARGB(i * blockSize, imgHeight - 1 - y, 255, 255, 0, 0);
        img.setARGB((i + 1) * blockSize, imgHeight - 1 - y, 255, 255, 0, 0);
      }
      for (int x = i * blockSize; x < (i + 1) * blockSize; x++) {
        img.setARGB(x, imgHeight - 1 - iVal, 255, 255, 0, 0);
      }
    }
  }

  public void setFramesPerSecond(int pFPS) {
    framesPerSecond = pFPS;
  }

  public boolean isRunning() {
    return running;
  }

  public void setRunning(boolean running) {
    this.running = running;
  }

  public long getTimeRenderStarted() {
    return timeRenderStarted;
  }

  public int getFramesPerSecond() {
    return framesPerSecond;
  }

  public void setGlobalScript(int pIdx, GlobalScript pGlobalScript) {
    globalScript[pIdx] = pGlobalScript;
  }

  public void setXFormScript(int pIdx, XFormScript pXFormScript) {
    xFormScript[pIdx] = pXFormScript;
  }

  public void setGlobalSpeed(int pIdx, MotionSpeed pGlobalSpeed) {
    globalSpeed[pIdx] = pGlobalSpeed;
  }

  public void setXFormSpeed(int pIdx, MotionSpeed pXFormSpeed) {
    xFormSpeed[pIdx] = pXFormSpeed;
  }

  public void setFlame(Flame pFlame) {
    flame = pFlame;
  }

  @Override
  public Flame getFlame() {
    // TODO
    return flame;
  }

  public void setDrawFFT(boolean pDrawFFT) {
    drawFFT = pDrawFFT;
  }

  public void setDrawFPS(boolean pDrawFPS) {
    drawFPS = pDrawFPS;
  }

  public void setDrawTriangles(boolean pDrawTriangles) {
    drawTriangles = pDrawTriangles;
  }
}
