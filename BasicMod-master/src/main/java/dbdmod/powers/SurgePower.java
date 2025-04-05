package dbdmod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import dbdmod.DiscardHookPatch;
import static dbdmod.BasicMod.makeID;

public class SurgePower extends BasePower implements DiscardHookPatch.OnDiscardThing {
    public static final String POWER_ID = makeID("SurgePower");
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SurgePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount >= 999)
            this.amount = 999;
    }

    @Override
    public void onManualDiscardThing() {
        addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster m3 : AbstractDungeon.getMonsters().monsters) {
            if (!m3.isDeadOrEscaped() && !m3.halfDead) {
                addToTop(new VFXAction(new LightningEffect(m3.drawX, m3.drawY), 0));
                addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}