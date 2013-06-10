package uk.co.jakeclarke.gravitywell;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.surfaceview.FixedResolutionStrategy;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
		cfg.resolutionStrategy = new FixedResolutionStrategy(
				GravityWellGame.TARGET_WIDTH, GravityWellGame.TARGET_HEIGHT);
        
        initialize(new GravityWellGame(), cfg);
    }
}