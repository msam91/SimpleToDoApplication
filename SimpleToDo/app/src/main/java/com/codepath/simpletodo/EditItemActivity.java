package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditItemActivity extends ActionBarActivity {

    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        EditText itemName = (EditText)findViewById(R.id.etEditItem);
        position = getIntent().getIntExtra("id", 0);
        itemName.setText(getIntent().getStringExtra("itemName"));
        itemName.setSelection(itemName.getText().length());
        itemName.setFocusable(true);
        if(itemName.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void onSubmit(View v) {
        EditText etName = (EditText) findViewById(R.id.etEditItem);
        Intent i = new Intent();
        i.putExtra("itemName", etName.getText().toString());
        i.putExtra("position",position);
        i.putExtra("code", 200);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
