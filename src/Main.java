import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    private static Automation au = new Automation();
    private static HashSet<String> result = new HashSet<>();
    private static HashSet<ArrayList<Integer>> cycles = new HashSet<>();// strings containt from chars
    private static ArrayList<Integer> rotateCycle(ArrayList<Integer> initial_cycle, int shift){
        var ans = new ArrayList<Integer>(initial_cycle.size());
        for(int i = shift; i < initial_cycle.size(); ++i){
            ans.add(initial_cycle.get(i));
        }
        for(int i = 0; i < shift; ++i){
            ans.add(initial_cycle.get(i));
        }
        return ans;
    }
    private static void ScanAutomation(String current_word, ArrayList<Integer> states, Integer state){
        // add new possible word
        if(au.states.get(state).isFinal){
            result.add(current_word);
        }
        if(!au.states.get(state).transitions.isEmpty()){
            for(var state_ : au.states.get(state).transitions) {

                var cycle_start = states.lastIndexOf(state_.getValue());

                if (cycle_start == -1) {
                    var new_word = current_word.concat(String.valueOf(state_.getKey()));
                    var new_states = new ArrayList<Integer>(states.size() + 1);
                    for(var trace_state: states){
                        new_states.add(trace_state);
                    }
                    new_states.add(state_.getValue());
                    ScanAutomation(new_word, new_states, state_.getValue());
                    continue;
                }
                var possible_cycle = new ArrayList<Integer>(states.size() - cycle_start);
                for(var i = cycle_start; i < states.size(); ++i){
                    possible_cycle.add(states.get(i));
                }
                if (!cycles.contains(possible_cycle)) { // found the cycle
                    for(int i = 0; i < possible_cycle.size(); ++i){
                        cycles.add(rotateCycle(possible_cycle, i));
                    }
                }
            }
        }
    }
    private static String Processing() throws IOException {
        System.out.print("To close the application enter \"EXIT\". Enter the path to your file > ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        return reader.readLine();
    }

    public static void main(String[] args) {
        while(true) {
            try {
                String file_path = Processing();
                if(file_path.equals("EXIT")){
                    System.out.println("Bye");
                    return;
                }
                au = new Automation(file_path);
                var states = new ArrayList<Integer>();
                states.add(au.startState);
                ScanAutomation("", states, au.startState);
                System.out.println(result);
            } catch (Exception ex) {
                System.out.println("The error was occurred. Description > " + ex.getMessage());
            } finally {
                result.clear();
                cycles.clear();
                au.Clear();
            }
        }
    }
}