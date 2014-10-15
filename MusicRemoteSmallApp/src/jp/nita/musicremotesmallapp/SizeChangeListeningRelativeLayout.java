package jp.nita.musicremotesmallapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SizeChangeListeningRelativeLayout extends RelativeLayout {

	public SizeChangeListeningRelativeLayout(Context ctx) {
		super(ctx);
	}
	
	public SizeChangeListeningRelativeLayout(Context ctx, AttributeSet attrs) {
		super(ctx, attrs);
	}
	
	@Override
	public void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
		super.onSizeChanged(xNew,yNew,xOld,yOld);
		if(xNew>=544&&yNew>=544){
			findViewById(R.id.tiny_layout).setVisibility(View.GONE);
			findViewById(R.id.small_layout).setVisibility(View.GONE);
			findViewById(R.id.large_layout).setVisibility(View.GONE);
			
			findViewById(R.id.large_layout).setVisibility(View.VISIBLE);
		}else if(xNew>=320&&yNew>=320){
			findViewById(R.id.tiny_layout).setVisibility(View.GONE);
			findViewById(R.id.small_layout).setVisibility(View.GONE);
			findViewById(R.id.large_layout).setVisibility(View.GONE);
			
			findViewById(R.id.small_layout).setVisibility(View.VISIBLE);
		}else{
			findViewById(R.id.tiny_layout).setVisibility(View.GONE);
			findViewById(R.id.small_layout).setVisibility(View.GONE);
			findViewById(R.id.large_layout).setVisibility(View.GONE);
			
			findViewById(R.id.tiny_layout).setVisibility(View.VISIBLE);
		}
	}

}
