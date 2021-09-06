package info.efficacious.centralmodelschool.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import info.efficacious.centralmodelschool.R;

public class Aboutus extends AppCompatActivity     {
private YouTubePlayerView               youTubePlayerView;
private final String                    YOUTUBE_VIDEO="AIzaSyBodFO6XCty0BNc3Bw49s5-4DUBrG0FFjo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        YouTubePlayerFragment youTubePlayerFragment = YouTubePlayerFragment.newInstance();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.add(R.id.youtube_layout,youTubePlayerFragment).commit();
       transaction.replace(R.id.youtube_layout,youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YOUTUBE_VIDEO, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b)
                {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    youTubePlayer.loadVideo("b5Fh6wIJt-I");
                    youTubePlayer.play();

                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                String errorMessage = youTubeInitializationResult.toString();
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
                Log.d("errorMessage:", errorMessage);
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        getSupportActionBar().getTitle();
        this.finish();
    }


}
