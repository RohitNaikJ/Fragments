package ListItems;

/**
 * Created by Rohit on 3/1/16.
 */
public class GradeEntry {
    private String code;
    private String name;
    private int score;
    private int out_of;
    private int weightage;
    private float abs;

    public GradeEntry(String code, String name, int score, int out_of, int weightage) {
        this.code = code;
        this.name = name;
        this.score = score;
        this.out_of = out_of;
        this.weightage = weightage;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getOut_of() {
        return out_of;
    }

    public int getWeightage() {
        return weightage;
    }

    public float getAbs() {
        float a = (((float)score)/out_of)*weightage;
        abs  = ((int)(a*100))/100.0f;
        return abs;
    }
}
