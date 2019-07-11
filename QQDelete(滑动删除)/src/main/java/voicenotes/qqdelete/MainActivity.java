package voicenotes.qqdelete;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SlideListView slv = findViewById(R.id.slv);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("招商银行 信用卡 (256" + i + ")");
        }
        slv.setAdapter(new SlideAdapter(this, list));
    }
}
