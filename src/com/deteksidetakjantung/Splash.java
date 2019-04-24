package com.deteksidetakjantung;

import com.deteksidetakjantung.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity {
 protected boolean active = true;
 protected int splashtime = 4000;//  delay time 4000 milisecond = 4 second 
 
 @Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//menghilangkan title bar
		setContentView(R.layout.splash); // memanggil layout splash.xml

	Thread splashThread = new Thread(){
		//waktu splash
			public void run() {
				try{
				int waited = 0;
				while (active && (waited < splashtime)) {
					sleep(100);
					if(active){
						waited += 100;
					}
				}
				}catch (InterruptedException e){
				}finally{
					finish();
					Intent newIntent = new Intent(Splash.this, MenuUtama.class); // pindah kemenu utama
					startActivityForResult(newIntent,0);
				}
			}
		};
		splashThread.start();
		}
 
	}
