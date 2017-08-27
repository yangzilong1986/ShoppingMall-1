package com.example.addsubview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private AddSubView mAddSubView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAddSubView = (AddSubView) findViewById(R.id.add_sub_view);
		mAddSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
			@Override
			public void OnNumberChange(int value) {
				Toast.makeText(MainActivity.this,"当前商品数=="+value,Toast.LENGTH_SHORT).show();
			}
		});
	}
}
