package dbdmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static dbdmod.BasicMod.makeID;

public class LacerationPower extends BasePower {
    public static final String POWER_ID = makeID("LacerationPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public LacerationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        amount /= 2;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (amount % 6 == 0 && amount != 0) {
            addToTop(new SFXAction(makeID("LacerationFull")));
            flash();
            addToBot(new DamageAction(owner, new DamageInfo(null, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        } else if (amount % 6 == 5) {
            addToTop(new SFXAction(makeID("LacerationAlmostFull")));
        } else {
            addToTop(new SFXAction(makeID("LacerationCount")));
        }
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
