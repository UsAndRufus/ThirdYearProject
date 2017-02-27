package output;

import java.util.Map;

public class ProbabilityDistribution {

    private Map<Integer, Double> distributionMap;
    private String name;
    private String integerColumnName;
    private String doubleColumnName;

    public ProbabilityDistribution(Map<Integer, Double> distributionMap, String name,
                                   String integerColumnName, String doubleColumnName) {
        this.distributionMap = distributionMap;
        this.name = name;
        this.integerColumnName = integerColumnName;
        this.doubleColumnName = doubleColumnName;
    }

    public Map<Integer, Double> getDistributionMap() {
        return distributionMap;
    }

    public String getName() {
        return name;
    }

    public String getIntegerColumnName() {
        return integerColumnName;
    }

    public String getDoubleColumnName() {
        return doubleColumnName;
    }
}
