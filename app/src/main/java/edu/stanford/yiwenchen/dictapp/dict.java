package edu.stanford.yiwenchen.dictapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class dict extends AppCompatActivity {

    private ArrayList<String> words;
    private String wordToShow;
    private ArrayList<String> fiveDef;
    private HashMap<String, String> dict;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        initDict();
        generateWordToGuess();
    }

    private void initDict() {
        words = new ArrayList<>();
        fiveDef = new ArrayList<>();
        dict = new HashMap<String, String>();
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords));
        while (scan.hasNextLine()) {
            String text = scan.nextLine();
            String[] parts = text.split("\t");
            if (parts.length < 2) continue;
            String word = parts[0];
            String defn = parts[1];
            dict.put(word, defn);
            words.add(word);
        }
    }


    private void generateWordToGuess() {
        Collections.shuffle(words);
        fiveDef.clear();
        wordToShow = words.get(0);
        for (int i = 0; i < 5; i++) {
            fiveDef.add(dict.get(words.get(i)));
        }
        Collections.shuffle(fiveDef);

        final TextView word = (TextView) findViewById(R.id.word);
        word.setText(wordToShow);
        ListView wordList = (ListView) findViewById(R.id.list);
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fiveDef);
            wordList.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        wordList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defClicked = fiveDef.get(position);
                String rightAns = dict.get(wordToShow);
                if (defClicked.equals(rightAns)) {
                    Toast.makeText(dict.this, "You are awesome!", Toast.LENGTH_SHORT).show();
                    generateWordToGuess();
                } else {
                    Toast.makeText(dict.this, "You suck.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void changeCliked(View view) {
        generateWordToGuess();
    }
}
