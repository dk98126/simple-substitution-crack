public class SpecialCount {
    private Pair pair;
    private double logProbability;

    public SpecialCount(Pair pair, Double logProbability) {
        this.pair = pair;
        this.logProbability = logProbability;
    }

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }

    public Double getLogProbability() {
        return logProbability;
    }

    public void setLogProbability(Double logProbability) {
        this.logProbability = logProbability;
    }

    @Override
    public String toString() {
        return "{" + pair + " : " + logProbability + "}";
    }
}
