package thehogmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;

public class BlockForEachDebuffAction extends AbstractGameAction {
    public BlockForEachDebuffAction(AbstractMonster target, AbstractPlayer source, int block) {
        this.setValues(target, source, block);

        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update() {
        HashSet<String> debuffSet = new HashSet<>();

        for (AbstractPower power : this.target.powers) {
            if (power.type == AbstractPower.PowerType.DEBUFF && !power.ID.equals("Shackled")) {
                debuffSet.add(power.ID);
            }
        }

        for (int i = 0; i < debuffSet.size(); i++) {
            addToBot(new GainBlockAction(this.source, this.amount));
        }

        this.isDone = true;
    }
}


