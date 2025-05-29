package dbdmod.action;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MinionPower;

public class DeadSwitchAction extends AbstractGameAction {
    private final DamageInfo info;

    public DeadSwitchAction(AbstractMonster target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);

        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FASTER && target != null) {
            target.damage(this.info);

            if ((target.isDying || this.target.currentHealth <= 0) && !target.halfDead && !target.hasPower(MinionPower.POWER_ID)) {
                for (AbstractMonster m3: AbstractDungeon.getMonsters().monsters) {
                    addToBot(new StunMonsterAction(m3, AbstractDungeon.player, 1));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}
