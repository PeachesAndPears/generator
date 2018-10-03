package dnd;

import java.util.ArrayList;

public class IntList extends ArrayList {

    IntList(){
        super(6);
    }
    IntList(int [] array){
        super(array.length);
        for(int i=0; i<array.length; i++) {
            this.add(array[i]);
        }
    }

}
