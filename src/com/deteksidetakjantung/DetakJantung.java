package com.deteksidetakjantung;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;
import com.deteksidetakjantung.R;
import com.deteksidetakjantung.process.ImageProcessing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


public class DetakJantung extends Activity  {
	
	 private static final String TAG = "DetakJantung";
	 private static final AtomicBoolean processing = new AtomicBoolean(false);

	    private static SurfaceView preview = null;
	    private static SurfaceHolder previewHolder = null;
	    private static Camera camera = null;
	    private static View image = null;
	    private static TextView text = null;
	    private static WakeLock wakeLock = null;
	    private static int averageIndex = 0;
	    private static final int averageArraySize = 4;
	    private static final int[] averageArray = new int[averageArraySize];

	    public static enum TYPE {
	        GREY, RED
	    };
	    //awal dirun warna icon abu abu
	    private static TYPE currentType = TYPE.GREY;

	    public static TYPE getCurrent() {
	        return currentType;
	    }

	    private static int beatsIndex = 0;
	    private static final int beatsArraySize = 3;
	    private static final int[] beatsArray = new int[beatsArraySize];
	    private static double beats = 0;
	    private static long startTime = 0;
		protected int stop;
		protected static int beatsAvg;

	   
	    @SuppressWarnings("deprecation")
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.deteksijantung);

	        preview = (SurfaceView) findViewById(R.id.preview);
	        preview.setVisibility(View.VISIBLE);
	        previewHolder = preview.getHolder();
	        previewHolder.addCallback(surfaceCallback);
	        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

	        image = findViewById(R.id.image);
	        text = (TextView) findViewById(R.id.text);
	        
	        
	        //calling wakelock indicate that aplication need to have the device stay on
	        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
	        //calling wakelock type full wakelock
	        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen");
	        

	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	    }

	    @Override
	    public void onResume() {
	        super.onResume();
	        onCreate(null);
	        camera = Camera.open();
	        wakeLock.acquire();
	        camera.startPreview();
	        startTime = System.currentTimeMillis();
	        final Timer timer = new Timer(true);
	        timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					if (beatsAvg > 50 && beatsAvg < 100) {
						stop++;
					}
					if (stop > 10) {
						stop = 0;
						 if (camera != null) camera.stopPreview();
						 runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								preview.setVisibility(View.GONE);							
								}
						});
						 timer.cancel();
						 
						 
					// kerjaan lain2
					}
				}
			}, 0, 1000);
	      
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        camera.setPreviewCallback(null);
	        camera.stopPreview();
	        camera.release();
	        camera = null;
	    }
	    @Override
	    protected void onDestroy() {
	    	 
	    	super.onDestroy();
	    }
	    
	    private static PreviewCallback previewCallback = new PreviewCallback() {

	        @Override
	        public void onPreviewFrame(byte[] data, Camera cam) {
	        	Log.e("Detak", "1");
	            if (data == null) throw new NullPointerException();
	            Camera.Size size = cam.getParameters().getPreviewSize();
	            if (size == null) throw new NullPointerException();

	            if (!processing.compareAndSet(false, true)) return;
	            Log.e("Detak", "2");
	            int width = size.width;
	            int height = size.height;

	            int imgAvg = ImageProcessing.decode4619PtoRedAvg(data.clone(), height, width);
	            // Log.i(TAG, "imgAvg="+imgAvg);
	            if (imgAvg == 0 || imgAvg == 255) {
	                processing.set(false);
	                return;
	            }
	            Log.e("Detak", "3");
	            int averageArrayAvg = 0;
	            int averageArrayCnt = 0;
	            for (int i = 0; i < averageArray.length; i++) {
	                if (averageArray[i] > 0) {
	                    averageArrayAvg += averageArray[i];
	                    averageArrayCnt++;
	                }
	            }

	            int rollingAverage = (averageArrayCnt > 0) ? (averageArrayAvg / averageArrayCnt) : 0;
	            TYPE newType = currentType;
	            if (imgAvg < rollingAverage) {
	                newType = TYPE.RED;
	                if (newType != currentType) {
	                    beats++;
	                    // Log.d(TAG, "BEAT!! beats="+beats);
	                }
	            } else if (imgAvg > rollingAverage) {
	                newType = TYPE.GREY;
	            }

	            if (averageIndex == averageArraySize) averageIndex = 0;
	            averageArray[averageIndex] = imgAvg;
	            averageIndex++;

	            // Transitioned from one state to another to the same
	            if (newType != currentType) {
	                currentType = newType;
	                image.postInvalidate();
	            }
	            
	            // reset data int 
	            long endTime = System.currentTimeMillis();
	            double totalTimeInSecs = (endTime - startTime) / 1000d;
	            // set perubahan detak jantung detik
	            if (totalTimeInSecs >= 1) {
	                double bps = (beats / totalTimeInSecs);
	                int dpm = (int) (bps * 60d);
	                if (dpm < 30 || dpm > 180) {
	                    startTime = System.currentTimeMillis();
	                    beats = 0;
	                    processing.set(false);
	                    return;
	                }

	                // Log.d(TAG,
	                // "totalTimeInSecs="+totalTimeInSecs+" beats="+beats);

	                if (beatsIndex == beatsArraySize) beatsIndex = 0;
	                beatsArray[beatsIndex] = dpm;
	                beatsIndex++;

	                int beatsArrayAvg = 0;
	                int beatsArrayCnt = 0;
	                for (int i = 0; i < beatsArray.length; i++) {
	                    if (beatsArray[i] > 0) {
	                        beatsArrayAvg += beatsArray[i];
	                        beatsArrayCnt++;
	                    }
	                }
	                beatsAvg = (beatsArrayAvg / beatsArrayCnt);
	                text.setText(String.valueOf(beatsAvg));
	                Log.e("Detak", String.valueOf(beatsAvg));
	                startTime = System.currentTimeMillis();
	                beats = 0; //memulai
	            }
	            processing.set(false);
	        }
	    };

	    private static SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

	        
	        @Override
	        public void surfaceCreated(SurfaceHolder holder) {
	        	
	            try {
	                camera.setPreviewDisplay(previewHolder);
	                camera.setPreviewCallback(previewCallback);
	            } catch (Throwable t) {
	                Log.e("PreviewDemo-surfaceCallback", "Exception in setPreviewDisplay()", t);
	            }
	        }

	        // memanggil kamera flash ketika membuka menu deteksi detak jantung 
	        @Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
				Camera.Parameters parameters = camera.getParameters();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF); 
				Camera.Size size = getSmallestPreviewSize(width, height, parameters);
				if (size != null){
					parameters.setPreviewSize(size.width, size.height);
					Log.d(TAG, "Using width=" + size.width + " height=" + size.height);
				}
				camera.setParameters(parameters);
				camera.startPreview();
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				camera.setParameters(parameters);
				
			}
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}
         };

	    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
	        Camera.Size result = null;

	        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
	            if (size.width <= width && size.height <= height) {
	                if (result == null) {
	                    result = size;
	                } else {
	                    int resultArea = result.width * result.height;
	                    int newArea = size.width * size.height;

	                    if (newArea < resultArea) result = size;
	                }
	            }
	        }

	        return result;
	    }
	    @Override
	    public void finish() {
	    	
	    	super.finish();
	    }
	}
