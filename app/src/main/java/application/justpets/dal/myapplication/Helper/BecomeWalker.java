package application.justpets.dal.myapplication.Helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import application.justpets.dal.myapplication.R;

public class BecomeWalker extends AppCompatActivity {
    private Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_walker);
        start = (Button) findViewById(R.id.button3);

        start.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, WalkerForm.class);
                startActivity(intent);
            }

        });
    }
}
