package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String>itemsAdapter;
    private ListView lvItems;
    private EditText etEditText;
    private final int REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.codepath.simpletodo.R.layout.activity_main);
        populateArrayItems();
        lvItems=(ListView)findViewById(com.codepath.simpletodo.R.id.lvItems);
        lvItems.setAdapter(itemsAdapter);
        etEditText = (EditText) findViewById(com.codepath.simpletodo.R.id.etNewItem);
        setupListViewListener();
        setupOnClickItemListener();
    }

    public void populateArrayItems(){
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
    }

    public void onAddItem(View v){
        if(!etEditText.getText().toString().isEmpty()) {
            itemsAdapter.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetInvalidated();
                writeItems();
                return true;
            }
        });
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File toDoFile = new  File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupOnClickItemListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchEditItemActivity(position,items.get(position));

            }
        });
    }

    public void launchEditItemActivity(int position, String itemName){
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("id",position);
        i.putExtra("itemName",itemName);
        startActivityForResult(i, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = i.getExtras().getString("itemName");
            int position = i.getIntExtra("position",0);

            // Toast the name to display temporarily on screen
            items.set(position,name);
            itemsAdapter.notifyDataSetInvalidated();
            writeItems();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.codepath.simpletodo.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.codepath.simpletodo.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
