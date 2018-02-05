package com.viscocits.utils.zoom.settings;

import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.alexvasilkov.gestures.Settings;
import com.alexvasilkov.gestures.views.interfaces.GestureView;

public class SettingsMenu implements SettingsController {

    private static final float OVERSCROLL = 32f;
    private static final long SLOW_ANIMATIONS = 1500L;

    
    private boolean isPanEnabled = true;

    private boolean isZoomEnabled = true;
    private boolean isRotationEnabled = false;
    private boolean isRestrictRotation = false;
    private boolean isOverscrollXEnabled = false;
    private boolean isOverscrollYEnabled = false;
    private boolean isOverzoomEnabled = true;
    private boolean isExitEnabled = true;
    private boolean isFillViewport = true;
    private Settings.Fit fitMethod = Settings.Fit.INSIDE;
    private int gravity = Gravity.CENTER;
    private boolean isSlow = false;

    public void setValuesFrom(Settings settings) {
        isPanEnabled = settings.isPanEnabled();
        isZoomEnabled = settings.isZoomEnabled();
        isRotationEnabled = settings.isRotationEnabled();
        isRestrictRotation = settings.isRestrictRotation();
        isExitEnabled = settings.isExitEnabled();
        isFillViewport = settings.isFillViewport();
        fitMethod = settings.getFitMethod();
        gravity = settings.getGravity();
    }









    @Override
    public void apply(GestureView view) {
        Context context = ((View) view).getContext();
        float overscrollX = isOverscrollXEnabled ? OVERSCROLL : 0f;
        float overscrollY = isOverscrollYEnabled ? OVERSCROLL : 0f;
        float overzoom = isOverzoomEnabled ? Settings.OVERZOOM_FACTOR : 1f;

        view.getController().getSettings()
                .setPanEnabled(isPanEnabled)
                .setZoomEnabled(isZoomEnabled)
                .setDoubleTapEnabled(isZoomEnabled)
                .setRotationEnabled(isRotationEnabled)
                .setRestrictRotation(isRestrictRotation)
                .setOverscrollDistance(context, overscrollX, overscrollY)
                .setOverzoomFactor(overzoom)
                .setExitEnabled(isExitEnabled)
                .setFillViewport(isFillViewport)
                .setFitMethod(fitMethod)
                .setGravity(gravity)
                .setAnimationsDuration(isSlow ? SLOW_ANIMATIONS : Settings.ANIMATIONS_DURATION);
    }

    private enum GravityType {
        CENTER(Gravity.CENTER),
        TOP(Gravity.TOP),
        BOTTOM(Gravity.BOTTOM),
        START(Gravity.START),
        END(Gravity.END),
        TOP_START(Gravity.TOP | Gravity.START),
        BOTTOM_END(Gravity.BOTTOM | Gravity.END);

        public final int gravity;

        GravityType(int gravity) {
            this.gravity = gravity;
        }

        public static GravityType find(int gravity) {
            for (GravityType type : values()) {
                if (type.gravity == gravity) {
                    return type;
                }
            }
            return null;
        }
    }

}
