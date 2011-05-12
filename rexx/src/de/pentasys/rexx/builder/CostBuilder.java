package de.pentasys.rexx.builder;

import java.util.List;

import de.pentasys.rexx.entities.costs.Amount;
import de.pentasys.rexx.entities.costs.Payment;
import de.pentasys.rexx.entities.costs.TrainAmount;

public class CostBuilder {
    public static Amount train(double costs, Payment payment) {
        return new TrainAmount(costs, payment);
    }
    public static Amount taxi(double costs, Payment payment) {
        return new TaxiAmount(costs, payment);
    }

    private final List<Amount> costs;

    public CostBuilder(List<Amount> costs) {
        this.costs = costs;
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
