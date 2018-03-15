package com.m2dl.mobebmp.mobe_bmp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Activity qui permet de prendre en photo une anomalie et d'y sélectionner la zone en question
 */

public class Anomalies extends MobeBMPActivity {

    private Size previewsize;
    private Size jpegSizes[]=null;
    private TextureView textureView;
    private ImageView imageView;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder previewBuilder;
    private CameraCaptureSession previewSession;
    private Handler mHandler;
    Button getpicture;
    Button saveButton;
    Paint paint;
    DatabaseReference databaseReference;
    private static final SparseIntArray ORIENTATIONS=new SparseIntArray();
    static
    {
        ORIENTATIONS.append(Surface.ROTATION_0,90);
        ORIENTATIONS.append(Surface.ROTATION_90,0);
        ORIENTATIONS.append(Surface.ROTATION_180,270);
        ORIENTATIONS.append(Surface.ROTATION_270,180);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anomalies);
        mHandler = new Handler();
        textureView=(TextureView)findViewById(R.id.textureView_anomalies_camera);
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        paint = new Paint();
        imageView = (ImageView) findViewById(R.id.imageView_anomalie);
        getpicture=(Button)findViewById(R.id.captureButton);
        getpicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPicture();
            }
        });
        saveButton = (Button) findViewById(R.id.savePicture);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
    void getPicture()
    {
        if(cameraDevice==null || textureView.getVisibility() == View.GONE)
        {
            if (textureView.getVisibility() == View.GONE)
            {
                textureView.setVisibility(View.VISIBLE);
                saveButton.setVisibility(View.GONE);
                imageView.setVisibility(View.GONE);
                return;
            }
            return;
        }
        CameraManager manager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try
        {
            CameraCharacteristics characteristics=manager.getCameraCharacteristics(cameraDevice.getId());
            if(characteristics!=null)
            {
                jpegSizes=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP).getOutputSizes(ImageFormat.JPEG);
            }
            int height=480,width=640;
            if(jpegSizes!=null && jpegSizes.length>0)
            {
                width=jpegSizes[0].getWidth();
                height=jpegSizes[0].getHeight();
            }
            ImageReader reader= ImageReader.newInstance(width,height,ImageFormat.JPEG,1);
            List<Surface> outputSurfaces=new ArrayList<Surface>(2);
            outputSurfaces.add(reader.getSurface());
            outputSurfaces.add(new Surface(textureView.getSurfaceTexture()));
            final CaptureRequest.Builder capturebuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            capturebuilder.addTarget(reader.getSurface());
            capturebuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
            int rotation=getWindowManager().getDefaultDisplay().getRotation();
            capturebuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotation));
            ImageReader.OnImageAvailableListener imageAvailableListener=new ImageReader.OnImageAvailableListener() {
                @Override
                public void onImageAvailable(ImageReader reader) {
                    Image image = null;
                    try {
                        image = reader.acquireLatestImage();
                        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                        final byte[] bytes = new byte[buffer.capacity()];
                        buffer.get(bytes);

                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                textureView.setVisibility(View.GONE);

                                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                imageView.setVisibility(View.VISIBLE);
                                imageView.setImageBitmap(bmp);
                                imageView.setRotation(90);

                                saveButton.setVisibility(View.VISIBLE);
                                saveButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v)
                                    {
                                        save(bytes);
                                        saveButton.setVisibility(View.GONE);
                                        imageView.setVisibility(View.GONE);
                                        textureView.setVisibility(View.VISIBLE);
                                    }
                                });
                            }
                        });

                    } catch (Exception ee) {
                        ee.printStackTrace();
                    }
                    finally {
                        if(image!=null)
                            image.close();
                    }
                }
                void save(byte[] bytes)
                {
                    File file12=getOutputMediaFile();

                    OutputStream outputStream=null;
                    try
                    {
                        outputStream=new FileOutputStream(file12);
                        outputStream.write(bytes);
                        List<byte[]> bytesList = new ArrayList<>();
                        bytesList.add(bytes);
                        databaseReference.child(file12.getName().replaceAll(".", "_")).setValue(bytesList);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }finally {
                        try {
                            if (outputStream != null)
                                outputStream.close();
                        }catch (Exception e){}
                    }
                }
            };

            HandlerThread handlerThread=new HandlerThread("takepicture");
            handlerThread.start();
            final Handler handler=new Handler(handlerThread.getLooper());
            reader.setOnImageAvailableListener(imageAvailableListener,handler);
            final CameraCaptureSession.CaptureCallback  previewSSession=new CameraCaptureSession.CaptureCallback() {
                @Override
                public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
                    super.onCaptureStarted(session, request, timestamp, frameNumber);
                }
                @Override
                public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result) {
                    super.onCaptureCompleted(session, request, result);
                    startCamera();
                }
            };
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    try
                    {
                        session.capture(capturebuilder.build(),previewSSession,handler);
                    }catch (Exception e)
                    {
                    }
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            },handler);
        }
        catch (Exception e)
        {
        }
    }
    public  void openCamera()
    {
        CameraManager manager=(CameraManager)getSystemService(Context.CAMERA_SERVICE);
        try
        {
            String camerId=manager.getCameraIdList()[0];
            CameraCharacteristics characteristics=manager.getCameraCharacteristics(camerId);
            StreamConfigurationMap map=characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewsize=map.getOutputSizes(SurfaceTexture.class)[0];
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            manager.openCamera(camerId,stateCallback,null);
        }catch (Exception e)
        {
        }
    }

    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            cameraDevice=camera;
            startCamera();
        }
        @Override
        public void onDisconnected(CameraDevice camera) {
        }
        @Override
        public void onError(CameraDevice camera, int error) {
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(cameraDevice!=null)
        {
            cameraDevice.close();
        }
    }

    void  startCamera()
    {
        if(cameraDevice==null||!textureView.isAvailable()|| previewsize==null)
        {
            return;
        }
        SurfaceTexture texture=textureView.getSurfaceTexture();
        if(texture==null)
        {
            return;
        }
        texture.setDefaultBufferSize(previewsize.getWidth(),previewsize.getHeight());
        Surface surface=new Surface(texture);
        try
        {
            previewBuilder=cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        }catch (Exception e)
        {
        }
        previewBuilder.addTarget(surface);
        try
        {
            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(CameraCaptureSession session) {
                    previewSession=session;
                    getChangedPreview();
                }
                @Override
                public void onConfigureFailed(CameraCaptureSession session) {
                }
            },null);
        }catch (Exception e)
        {
        }
    }
    void getChangedPreview()
    {
        if(cameraDevice==null)
        {
            return;
        }
        previewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        HandlerThread thread=new HandlerThread("changed Preview");
        thread.start();
        Handler handler=new Handler(thread.getLooper());
        try
        {
            previewSession.setRepeatingRequest(previewBuilder.build(), null, handler);
        }catch (Exception e){}
    }

    private static File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStorageDirectory(),
                "Mobe_BMP");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Mobe_BMP", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

}
