package de.pentasys.rexx.builder;

import java.util.List;

import de.pentasys.rexx.entities.costs.Amount;
import de.pentasys.rexx.entities.costs.TrainAmount;
import de.pentasys.rexx.update.Payment;

public class CostBuilder {

    private final List<Amount> costs;

    public CostBuilder(List<Amount> costs) {
        this.costs = costs;
    }

    public static Amount train(double costs, Payment payment) {
        return new TrainAmount(costs, payment);
    }

    public CostBuilder inboundCosts(Amount cost) {
        costs.add(cost);
        return this;
    }

    public CostBuilder outboundCosts(Amount cost) {
        costs.add(cost);
        return this;
    }
}
