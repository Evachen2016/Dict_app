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

    private static final String[] WORDS = {
            "abate", "to lessen to subside",
            "abeyance", "suspended action",
            "abjure", "promise or swear to give up",
            "abrogate", "repeal or annul by authority",
            "abstruse", "difficult to comprehend obscure",
            "acarpous", "effete no longer fertile worn out",
            "accretion", "the growing of separate things into one",
            "agog", "eager/excited",
            "alloy", "to debase by mixing with something inferior",
            "amortize", "end (a debt) by setting aside money"
    };
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
        for (int i = 0; i < WORDS.length; i+=2) {
            dict.put(WORDS[i], WORDS[i + 1]);
            words.add(WORDS[i]);
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

}
