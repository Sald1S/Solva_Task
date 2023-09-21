package kz.saldis.solvatask.model;

public enum TransactionCategory {
    SERVICE(0),
    GOODS(1);

    private final int intValue;

    TransactionCategory(int intValue){
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }
}
