package dbdmod;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import dbdmod.powers.Brutal;

public class MyFunction {

    // Return [brutalToReduce, strengthToReduce] (both of them are negative)
    public static int[] annihilate(AbstractCreature p, int needed) {
        int[] result = new int[2];
        // Detect Brutal and Strength stacks
        AbstractPower brutal = p.getPower(Brutal.POWER_ID);
        AbstractPower strength = p.getPower(StrengthPower.POWER_ID);
        int brutalAmount = (brutal != null) ? brutal.amount : 0;
        int strengthAmount = (strength != null) ? strength.amount : 0;
        int totalPower = brutalAmount + strengthAmount;

        // If Brutal stacks are enough
        if (brutalAmount >= needed) {
            result[0] = -needed;
            return result;
        }

        // If the total of Brutal + Strength stacks is enough
        if (totalPower >= needed) {
            result[0] = -brutalAmount;
            result[1] = brutalAmount-needed;
            return result;
        }

        // If Strength stacks are enough
        if (strengthAmount >= needed) {
            result[1] = -needed;
            return result;
        }

        return result;
    }
}
