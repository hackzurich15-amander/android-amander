package ch.christofbuechi.android_amander.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by christof on 03.10.15.
 */
public class Wrapper {

    @SerializedName("input")
    @Expose
    private Input input;

    @SerializedName("output")
    @Expose
    private Output output;

    public static Wrapper fromVehicle(Vehicle vehilce){
        Wrapper wrapper = new Wrapper();

        Input minput = new Input();
        minput.setVehicle(vehilce);
        wrapper.setInput(minput);

        Output output = new Output();
        output.setMatch(vehilce.match);
        wrapper.setOutput(output);
        return wrapper;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
