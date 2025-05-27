package dbdmod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static dbdmod.BasicMod.makeID;

public class VilePurgePower extends BasePower {
    public static final String POWER_ID = makeID("VilePurgePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    public VilePurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {}

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        if (owner.hasPower(PoisonPower.POWER_ID)) {
            int poisonAmount = owner.getPower(PoisonPower.POWER_ID).amount;
            for (AbstractMonster m3 : AbstractDungeon.getMonsters().monsters) {
                if (!m3.isDeadOrEscaped() && !m3.halfDead && m3 != owner) {
                    addToBot(new ApplyPowerAction(m3, p, new PoisonPower(m3, p, poisonAmount)));
                }
            }
        }
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}