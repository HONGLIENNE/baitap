package com.example.multimediaapis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 100;
    private final String[] permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private CameraSurfaceView cameraView;
    private Button btnAddView, btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm view
        btnAddView = findViewById(R.id.btnaddview);
        btnCapture = findViewById(R.id.btncapture);

        // Kiểm tra quyền khi khởi tạo
        if (!hasPermissions()) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS);
        } else {
            // Nếu có quyền rồi thì tự động mở camera
            setupCameraView();
        }

        btnAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupCameraView();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cameraView != null) {
                    capturePhoto();
                } else {
                    Toast.makeText(MainActivity.this, "Vui lòng mở camera trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupCameraView() {
        if (cameraView == null) {
            cameraView = new CameraSurfaceView(MainActivity.this);
            LinearLayout layout = findViewById(R.id.LinearLayout1);
            layout.addView(cameraView);
        }
    }

    private boolean hasPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        }
        return true;
    }

    private void capturePhoto() {
        cameraView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    Calendar cal = Calendar.getInstance();
                    String filename = "IMG_" + cal.getTimeInMillis() + ".jpg";

                    File picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    if (!picturesDir.exists()) {
                        picturesDir.mkdirs();
                    }

                    File file = new File(picturesDir, filename);

                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(data);
                    fos.close();

                    // Cập nhật MediaStore để ảnh hiện trong gallery
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(file);
                    mediaScanIntent.setData(contentUri);
                    sendBroadcast(mediaScanIntent);

                    // Mở ảnh vừa chụp
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        uri = FileProvider.getUriForFile(MainActivity.this, "com.example.multimediaapis.fileprovider", file);
                    } else {
                        uri = Uri.fromFile(file);
                    }
                    intent.setDataAndType(uri, "image/*");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);

                    Toast.makeText(MainActivity.this, "Ảnh đã lưu: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();

                    // Khởi động lại preview camera
                    camera.startPreview();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Lỗi lưu ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Xử lý kết quả cấp quyền
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            boolean grantedAll = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    grantedAll = false;
                    break;
                }
            }

            if (grantedAll) {
                Toast.makeText(this, "Đã cấp quyền camera và lưu trữ", Toast.LENGTH_SHORT).show();
                setupCameraView();
            } else {
                Toast.makeText(this, "Bạn cần cấp quyền camera và lưu trữ để sử dụng ứng dụng", Toast.LENGTH_LONG).show();
                // Bạn có thể thoát app hoặc disable chức năng ở đây
            }
        }
    }
}
