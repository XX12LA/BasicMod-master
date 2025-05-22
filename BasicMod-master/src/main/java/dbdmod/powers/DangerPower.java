package dbdmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static dbdmod.BasicMod.makeID;

public class DangerPower extends BasePower {
    public static final String POWER_ID = makeID("DangerPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public DangerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        addToTop(new SFXAction(makeID("Exposed")));
    }

    public void stackPower(int stackAmount) {
        addToTop(new SFXAction(makeID("Exposed")));
        this.amount += stackAmount;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        amount--;
        if (amount == 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return damageAmount*2;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
