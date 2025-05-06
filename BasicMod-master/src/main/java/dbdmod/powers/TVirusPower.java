package dbdmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static dbdmod.BasicMod.makeID;

public class TVirusPower extends BasePower {
    public static final String POWER_ID = makeID("TVirusPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public TVirusPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onDeath() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.owner.currentHealth <= 0) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, amount)));
        }
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void reducePower(int reduceAmount) {
        this.amount -= reduceAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
