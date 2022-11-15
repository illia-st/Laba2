import java.io.FileReader;
import java.util.*;
public class Automation {

    public class State {
        public boolean isFinal = false;
        public ArrayList<Map.Entry<Integer, Integer>> transitions = new ArrayList<>();

        public void AddTransition(int alphabetSymbol, int toState) {
            transitions.add(new AbstractMap.SimpleEntry<>(alphabetSymbol, toState));
        }

    }

    public int alphabetCount, statesCount;
    public int startState;
    public ArrayList<State> states;
    public Automation() {}
    public Automation(String filepath) {
        try(Scanner in = new Scanner(new FileReader(filepath))) {
            alphabetCount = in.nextInt();
            statesCount = in.nextInt();
            startState = in.nextInt();

            states = new ArrayList<>();
            for(int i = 0; i < statesCount; i++) {
                states.add(new State());
            }

            int finalStatesCount = in.nextInt();
            int tmpState = 0;
            while(finalStatesCount > 0) {
                tmpState = in.nextInt();
                if(tmpState < 0 || tmpState >= statesCount) {
                    System.out.println("Wrong input automaton");
                    System.exit(0);
                }
                states.get(tmpState).isFinal = true;
                finalStatesCount--;
            }

            int letter = 0, tmpState2 = 0;
            while(in.hasNext()) {
                tmpState = in.nextInt();
                letter = in.nextInt();
                tmpState2 = in.nextInt();
                if(tmpState < 0 || tmpState >= statesCount || letter < 0 || letter >= alphabetCount ||
                        tmpState2 < 0 || tmpState2 >= statesCount) {
                    System.out.println("Wrong input automaton");
                    System.exit(0);
                }
                states.get(tmpState).AddTransition(letter, tmpState2);
            }
        } catch (Exception e) {
            System.out.println("Can`t read file");
            System.exit(0);
        }
    }
    public void Clear(){
        states.clear();
        alphabetCount = 0;
        statesCount = 0;
        startState = 0;
    }


}