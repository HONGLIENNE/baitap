package com.example.multimediaapis;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.List;

public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera camera;

    public CameraSurfaceView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();

            // Cấu hình preview size tối ưu (ví dụ lấy cái đầu tiên)
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            Camera.Size selected = sizes.get(0);
            parameters.setPreviewSize(selected.width, selected.height);

            camera.setParameters(parameters);
            camera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    public boolean capture(Camera.PictureCallback jpegHandler) {
        if (camera != null) {
            camera.takePicture(null, null, jpegHandler);
            return true;
        }
        return false;
    }
}
