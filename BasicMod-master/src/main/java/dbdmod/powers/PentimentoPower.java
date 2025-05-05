package dbdmod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import dbdmod.DiscardHookPatch;

import static dbdmod.BasicMod.makeID;

public class PentimentoPower extends BasePower implements DiscardHookPatch.OnDiscardThing {
    public static final String POWER_ID = makeID("PentimentoPower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private int discardAmount = 0;

    public PentimentoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount == 0)
            addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if (this.amount >= 999)
            this.amount = 999;
        if (this.amount <= -999)
            this.amount = -999;
    }

    @Override
    public void onManualDiscardThing() {
        discardAmount++;
        if (discardAmount >= 4) {
            discardAmount = 0;
            AbstractCard c = AbstractDungeon.player.hand.getRandomCard(AbstractDungeon.cardRandomRng);
            c.cost -= amount;
            c.costForTurn -= amount;
            c.isCostModified = true;
            c.flash();
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
