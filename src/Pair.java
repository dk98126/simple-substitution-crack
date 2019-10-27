public class Pair {
    private String gram;
    private long frequency;

    public Pair(String gram, Long frequency) {
        this.gram = gram;
        this.frequency = frequency;
    }

    public String getGram() {
        return gram;
    }

    public void setGram(String gram) {
        this.gram = gram;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "{" + gram + " : " + frequency + "}";
    }
}
