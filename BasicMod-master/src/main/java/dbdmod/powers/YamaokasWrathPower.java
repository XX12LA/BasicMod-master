package dbdmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.stances.WrathStance;

import static dbdmod.BasicMod.makeID;

public class YamaokasWrathPower extends BasePower {
    public static final String POWER_ID = makeID("YamaokasWrathPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public YamaokasWrathPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.owner == this.owner && this.amount > 0) {
            this.amount -= damageAmount;
        }
        if (this.amount <= 0 && !AbstractDungeon.player.stance.ID.equals(WrathStance.STANCE_ID)) {
            this.amount = 0;
            addToBot(new ChangeStanceAction(WrathStance.STANCE_ID));
        }
    }

    public void stackPower(int stackAmount) {}

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        addToBot(new ChangeStanceAction(NeutralStance.STANCE_ID));
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}