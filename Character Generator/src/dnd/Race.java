package dnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Race {
    /*
    ■name■
    ■statBonus■
    ■languageBonus■
    ■languages■
    ■speed■
    ■size■
    ■raceFeatures■
    ■racialCastings■
    ■acBonus■
    ■skills■
    ■otherProficiencies■
    ■alignmentTrend■
    */
    private String name;
    private IntList statBonus;
    private int languageBonus;
    private ArrayList<String> languages;
    private int speed;
    private char size;
    ArrayList<String> raceFeatures;
    ArrayList<Spells> racialCastings;

    Race() {
        name = "Human";
        statBonus = new IntList(new int[]{1, 1, 1, 1, 1, 1});
        languageBonus = 1;
        speed = 30;
        size = 'M';
        raceFeatures = new ArrayList<>(0);
    }

    Race(String n, int[] sb, int l, int sp, char sz) {
        name = n;
        statBonus = new IntList(sb);
        languageBonus = l;

        speed = sp;
        size = sz;
    }

    Race(File text) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(text));
        String st;
        while ((st = input.readLine()) != null) {


        }
    }
}
